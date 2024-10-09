package com.svetanis.datastructures.graph.floodfill;

import static com.svetanis.java.base.utils.Print.print;

// In computer graphics, an uncompressed 
// raster image is presented as a matrix of numbers. 
// Each entry of the matrix represents the color of a pixel. 
// A flood fill algorithm takes a coordinate r, c and 
// a replacement color, and replaces all pixels connected to r, c 
// that have the same color (i.e., the pixels connected to 
// the given coordinate with same color and all the other pixels 
// connected to the those pixels of the same color) with the replacement color. 
// (e.g. MS-Paint's paint bucket tool).

public final class FloodFillMatrix {
	// Time Complexity: O(r * c)

	public static void floodFill(int[][] image, int x, int y, int c) {
		int prev = image[x][y];
		dfs(image, x, y, prev, c);
	}

	public static void dfs(int[][] image, int x, int y, int prev, int c) {
		int n = image.length;
		int m = image[0].length;
		if (x < 0 || x >= n || y >= m || y < 0) {
			return;
		}
		if (image[x][y] != prev) {
			return;
		}
		image[x][y] = c;
		dfs(image, x + 1, y, prev, c);
		dfs(image, x - 1, y, prev, c);
		dfs(image, x, y + 1, prev, c);
		dfs(image, x, y - 1, prev, c);
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

		int[][] image1 = { //
				{ 0, 1, 3, 4, 1 }, //
				{ 3, 8, 8, 3, 3 }, //
				{ 6, 7, 8, 8, 3 }, //
				{ 12, 2, 8, 9, 1 }, //
				{ 12, 3, 1, 3, 2 } //
		};//
		floodFill(image1, 2, 2, 9);
		print(image1);

		int[][] image2 = { //
				{ 0, 1, 6, 4 }, //
				{ 2, 3, 3, 5 }, //
				{ 3, 3, 3, 3 }, //
				{ 6, 4, 3, 4 } //
		};//
		floodFill(image2, 1, 1, 9);
		print(image2);
	}
}
