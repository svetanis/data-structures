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
// four neighbours (up, down, left, right) that still has colour prev. A painted cell no longer has
// colour prev, so painting itself marks the cell as visited and no separate visited array is
// needed. The one catch is c == prev: painting would change nothing, the cell would still look
// unvisited, and the fill would run forever, so a cell that already has colour c stops the fill.
//
// Time: O(m * n) -- each cell is painted at most once and looked at by at most four neighbours.
// Space: O(m * n) -- the recursion, one call deeper for every cell of a snaking region; at
// LeetCode's 50 x 50 that is at most 2,500 calls.
//
// Siblings: FloodFillMatrix is the same fill with the four neighbour calls written out instead of
// looped over dx and dy; FloodFillBfs and FloodFillSubmit do it with a queue over a list of lists.

public final class FloodFillDfs {

	private static int[] dx = { 1, -1, 0, 0 };
	private static int[] dy = { 0, 0, 1, -1 };

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
		for (int k = 0; k < dx.length; k++) {
			dfs(image, x + dx[k], y + dy[k], prev, c); // RECURSE: the four neighbours
		}
	}

	public static void main(String[] args) {
		int[][] image3 = { //
				{ 1, 1, 1 }, //
				{ 1, 1, 0 }, //
				{ 1, 0, 1 } //
		};//
		floodFill(image3, 1, 1, 2);
		print(image3);
		// 2 2 2
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
