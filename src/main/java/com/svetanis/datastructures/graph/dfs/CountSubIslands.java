package com.svetanis.datastructures.graph.dfs;

// 1905. Count Sub Islands

public final class CountSubIslands {
	// Time Complexity: O(n * m)
	// Space Complexity: O(n * m)

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	private int rows;
	private int cols;
	private int[][] g1;
	private int[][] g2;

	public int countSubIslands(int[][] grid1, int[][] grid2) {
		this.g1 = grid1;
		this.g2 = grid2;
		this.rows = grid1.length;
		this.cols = grid1[0].length;
		int count = 0;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (g2[row][col] == 1 && isSubIsland(row, col)) {
					count++;
				}
			}
		}
		return count;
	}

	private boolean isSubIsland(int row, int col) {
		boolean subIsland = g1[row][col] == 1;
		g2[row][col] = 0;
		for (int k = 0; k < 4; k++) {
			int x = row + dx[k];
			int y = col + dy[k];
			if (valid(x, y) && g2[x][y] == 1 && !isSubIsland(x, y)) {
				subIsland = false;
			}
		}
		return subIsland;
	}

	private boolean valid(int x, int y) {
		return x >= 0 && x < rows && y >= 0 && y < cols;
	}

	public static void main(String[] args) {
		CountSubIslands csi = new CountSubIslands();
		int[][] g1 = { { 1, 1, 1, 0, 0 }, { 0, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0 }, { 1, 1, 0, 1, 1 } };
		int[][] g2 = { { 1, 1, 1, 0, 0 }, { 0, 0, 1, 1, 1 }, { 0, 1, 0, 0, 0 }, { 1, 0, 1, 1, 0 }, { 0, 1, 0, 1, 0 } };
		System.out.println(csi.countSubIslands(g1, g2)); // 3

		int[][] g3 = { { 1, 0, 1, 0, 1 }, { 1, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1 }, { 1, 0, 1, 0, 1 } };
		int[][] g4 = { { 0, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1 }, { 0, 1, 0, 1, 0 }, { 0, 1, 0, 1, 0 }, { 1, 0, 0, 0, 1 } };
		System.out.println(csi.countSubIslands(g3, g4)); // 2
	}
}
