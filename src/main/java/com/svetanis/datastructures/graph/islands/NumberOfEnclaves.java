package com.svetanis.datastructures.graph.islands;

// 1020. Number of Enclaves

public final class NumberOfEnclaves {
	// Time complexity: O(r * c)
	// Space Complexity: O(r * c)

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public static int countEnclaves(int[][] g) {
		int n = g.length;
		int m = g[0].length;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (landOnBoundary(g, r, c)) {
					dfs(g, r, c);
				}
			}
		}
		return count(g);
	}

	private static int count(int[][] g) {
		int count = 0;
		for (int[] row : g) {
			for (int val : row) {
				count += val;
			}
		}
		return count;
	}

	private static boolean landOnBoundary(int[][] g, int row, int col) {
		boolean one = row == 0 || row == g.length - 1;
		boolean two = col == 0 || col == g[0].length - 1;
		return (one || two) && g[row][col] == 1;
	}

	private static void dfs(int[][] g, int row, int col) {
		// mark this cell as water
		g[row][col] = 0;
		// considers only the neighbors as adjacent vertices
		// and recur for all connected neighbors
		for (int k = 0; k < dx.length; ++k) {
			int x = row + dx[k];
			int y = col + dy[k];
			if (isSafe(g, x, y)) {
				dfs(g, x, y);
			}
		}
	}

	private static boolean isSafe(int[][] g, int row, int col) {
		boolean one = row >= 0 && row < g.length; // row number is in range
		boolean two = col >= 0 && col < g[0].length; // col number is in range
		return one && two && g[row][col] == 1;
	}

	public static void main(String[] args) {
		int[][] g1 = { //
				{ 0, 0, 0, 0 }, //
				{ 1, 0, 1, 0 }, //
				{ 0, 1, 1, 0 }, //
				{ 0, 0, 0, 0 } };//
		System.out.println(countEnclaves(g1)); // 3

		int[][] g2 = { //
				{ 0, 1, 1, 0 }, //
				{ 0, 0, 1, 0 }, //
				{ 0, 0, 1, 0 }, //
				{ 0, 0, 0, 0 } };//
		System.out.println(countEnclaves(g2)); // 0
	}
}
