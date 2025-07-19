package com.svetanis.datastructures.graph.dfs;

import com.svetanis.java.base.utils.Print;

// 1034. Coloring A Border

public final class ColoringBorder {
	// Time Complexity: O(n * m)
	// Space Complexity: O(n * m)

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	private int rows;
	private int cols;
	private int color;
	private int[][] grid;
	private boolean[][] visited;

	public int[][] colorBorder(int[][] grid, int row, int col, int color) {
		this.color = color;
		this.rows = grid.length;
		this.cols = grid[0].length;
		this.grid = grid;
		this.visited = new boolean[rows][cols];
		dfs(row, col, grid[row][col]);
		return grid;
	}

	private void dfs(int row, int col, int original) {
		visited[row][col] = true;
		boolean boarder = !isSafe(row, col);
		for (int k = 0; k < 4; k++) {
			int x = row + dx[k];
			int y = col + dy[k];
			if (isSafe(x, y)) {
				if (!visited[x][y]) {
					if (grid[x][y] == original) {
						dfs(x, y, original);
					} else {
						boarder = true;
					}
				}
			} else {
				boarder = true;
			}
		}
		if (boarder) {
			grid[row][col] = color;
		}
	}

	private boolean isSafe(int row, int col) {
		boolean one = row >= 0 && row < rows; // row number is in range
		boolean two = col >= 0 && col < cols; // col number is in range
		return one && two;
	}

	public static void main(String[] args) {
		ColoringBorder ups = new ColoringBorder();
		int[][] g = { { 1, 1 }, { 1, 2 } };
		Print.print(ups.colorBorder(g, 0, 0, 3)); // [3,3],[3,2]

		ColoringBorder ups2 = new ColoringBorder();
		int[][] g1 = { //
				{ 1, 2, 2 }, //
				{ 2, 3, 2 } };//
		Print.print(ups2.colorBorder(g1, 0, 1, 3)); // [1,3,3], [2,3,3]

		int[][] g2 = { //
				{ 1, 1, 1 }, //
				{ 1, 1, 1 }, //
				{ 1, 1, 1 } };//
		Print.print(ups.colorBorder(g2, 1, 1, 2)); // [2,2,2],[2,1,2],[2,2,2]
	}
}
