package com.svetanis.datastructures.graph.floodfill;

// 733. Flood Fill
//
// Input: an image (a grid of colour numbers) with m rows and n columns, a start cell (x, y) and a
// new colour c. On LeetCode: 1 <= m, n <= 50, 0 <= image[i][j], c < 2^16, and the start cell is
// inside the image. In computer graphics, a flood fill takes a start pixel and a replacement
// colour and repaints every pixel connected to it that has the same colour as it has (the paint
// bucket of MS-Paint).
// Returns: the same array, repainted in place.
//
// The one idea: the start cell's colour is prev. Paint the cell c, then do the same to each of its
// four neighbours (down, up, right, left) that still has colour prev. A painted cell no longer has
// colour prev, so painting itself marks the cell as visited and no separate visited array is
// needed. The one catch is c == prev: painting would change nothing, the cell would still look
// unvisited, and the fill would run forever, so a cell that already has colour c stops the fill.
//
// Time: O(m * n) -- each cell is painted at most once and looked at by at most four neighbours.
// Space: O(m * n) -- the recursion, one call deeper for every cell of a snaking region; at
// LeetCode's 50 x 50 that is at most 2,500 calls.
//
// Siblings: FloodFillDfs is the same fill with the neighbours looped over dx and dy instead of
// written out; FloodFillBfs and FloodFillSubmit do it with a queue over a list of lists.

public final class FloodFillMatrix {

	public static int[][] floodFill(int[][] image, int x, int y, int c) {
		int prev = image[x][y]; // OLD: the colour being replaced
		dfs(image, x, y, prev, c);
		return image;
	}

	public static void dfs(int[][] image, int x, int y, int prev, int c) {
		int n = image.length;
		int m = image[0].length;
		if (x < 0 || x >= n || y >= m || y < 0) {
			return; // SKIP: off the image
		}
		if (image[x][y] != prev || image[x][y] == c) {
			return; // SKIP: a different colour, or already the new colour (which also stops c == prev)
		}
		image[x][y] = c; // PAINT: the new colour doubles as the visited mark
		dfs(image, x + 1, y, prev, c); // RECURSE: down
		dfs(image, x - 1, y, prev, c); // RECURSE: up
		dfs(image, x, y + 1, prev, c); // RECURSE: right
		dfs(image, x, y - 1, prev, c); // RECURSE: left
	}

	public static void main(String[] args) {
		int[][] image = { //
				{ 1, 1, 1, 1, 1, 1, 1, 1 }, //
				{ 1, 1, 1, 1, 1, 1, 0, 0 }, //
				{ 1, 0, 0, 1, 1, 0, 1, 1 }, //
				{ 1, 2, 2, 2, 2, 0, 1, 0 }, //
				{ 1, 1, 1, 2, 2, 0, 1, 0 }, //
				{ 1, 1, 1, 2, 2, 2, 2, 0 }, //
				{ 1, 1, 1, 1, 1, 2, 1, 1 }, //
				{ 1, 1, 1, 1, 1, 2, 2, 1 }, //
		};//
		floodFill(image, 4, 4, 3);
		print(image);
		// 1 1 1 1 1 1 1 1   the connected block of 2s around (4,4) becomes 3
		// 1 1 1 1 1 1 0 0
		// 1 0 0 1 1 0 1 1
		// 1 3 3 3 3 0 1 0
		// 1 1 1 3 3 0 1 0
		// 1 1 1 3 3 3 3 0
		// 1 1 1 1 1 3 1 1
		// 1 1 1 1 1 3 3 1

		int[][] image1 = { //
				{ 0, 1, 3, 4, 1 }, //
				{ 3, 8, 8, 3, 3 }, //
				{ 6, 7, 8, 8, 3 }, //
				{ 12, 2, 8, 9, 1 }, //
				{ 12, 3, 1, 3, 2 } //
		};//
		floodFill(image1, 2, 2, 9);
		print(image1);
		// 0 1 3 4 1   the connected 8s around (2,2) become 9; the 9 at (3,3) was 9 already
		// 3 9 9 3 3
		// 6 7 9 9 3
		// 12 2 9 9 1
		// 12 3 1 3 2

		int[][] image2 = { //
				{ 0, 1, 6, 4 }, //
				{ 2, 3, 3, 5 }, //
				{ 3, 3, 3, 3 }, //
				{ 6, 4, 3, 4 } //
		};//
		floodFill(image2, 1, 1, 9);
		print(image2);
		// 0 1 6 4
		// 2 9 9 5
		// 9 9 9 9
		// 6 4 9 4

		int[][] image3 = { //
				{ 1, 1, 1 }, //
				{ 1, 1, 0 }, //
				{ 1, 0, 1 } //
		};//
		floodFill(image3, 1, 1, 2);
		print(image3);
		// 2 2 2   the corner 1 at (2,2) is not connected, so it stays
		// 2 2 0
		// 2 0 1

		int[][] image4 = { //
				{ 0, 0, 0 }, //
				{ 0, 0, 0 } //
		};//
		floodFill(image4, 0, 0, 0);
		print(image4);
		// 0 0 0   the new colour equals the old one, so nothing changes
		// 0 0 0

	}

	private static void print(int[][] matrix) {
		for (int[] row : matrix) {
			for (int value : row) {
				System.out.print(value + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
