package com.svetanis.datastructures.graph.floodfill;

import static com.svetanis.java.base.utils.Print.print;

// 733. Flood Fill

// In computer graphics, an uncompressed 
// raster image is presented as a matrix of numbers. 
// Each entry of the matrix represents the color of a pixel. 
// A flood fill algorithm takes a coordinate r, c and 
// a replacement color, and replaces all pixels connected to r, c 
// that have the same color (i.e., the pixels connected to 
// the given coordinate with same color and all the other pixels 
// connected to the those pixels of the same color) with the replacement color. 
// (e.g. MS-Paint's paint bucket tool).

public final class FloodFillDfs {
	// Time Complexity: O(r * c)

	private static int[] dx = { 1, -1, 0, 0 };
	private static int[] dy = { 0, 0, 1, -1 };

	public static int[][] floodFill(int[][] image, int x, int y, int c) {
		int prev = image[x][y];
		dfs(image, x, y, prev, c);
		return image;
	}

	public static void dfs(int[][] image, int x, int y, int prev, int c) {
		int n = image.length;
		int m = image[0].length;
		if (x < 0 || x >= n || y >= m || y < 0) {
			return;
		}
		if (image[x][y] != prev || image[x][y] == c) {
			return;
		}
		image[x][y] = c;
		for (int k = 0; k < dx.length; k++) {
			dfs(image, x + dx[k], y + dy[k], prev, c);
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

		int[][] image4 = { //
				{ 0, 0, 0 }, //
				{ 0, 0, 0 } //
		};//
		floodFill(image4, 0, 0, 0);
		print(image4);

	}
}
