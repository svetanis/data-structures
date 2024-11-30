package com.svetanis.datastructures.graph.islands;

// 1254. Number of Closed Islands

public final class NumberOfClosedIslands {
	// Time complexity: O(r * c)
	// Space Complexity: O(r * c)

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public static int closedIslands(int[][] g) {
		int n = g.length;
		int m = g[0].length;
		// 0 is land, 1 is water
		int count = 0;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (g[r][c] == 0) {
					count += dfs(g, r, c);
				}
			}
		}
		return count;
	}

	private static int dfs(int[][] g, int row, int col) {
		int closed = closed(g, row, col) ? 1 : 0;
		// mark this cell as water
		g[row][col] = 1;
		// considers only the neighbors as adjacent vertices
		// and recur for all connected neighbors
		for (int k = 0; k < dx.length; ++k) {
			int x = row + dx[k];
			int y = col + dy[k];
			if (safe(g, x, y)) {
				closed &= dfs(g, x, y);
			}
		}
		return closed;
	}

	private static boolean closed(int[][] g, int row, int col) {
		boolean one = row > 0 && row < g.length - 1;
		boolean two = col > 0 && col < g[0].length - 1;
		return one && two;
	}

	private static boolean safe(int[][] g, int row, int col) {
		boolean one = row >= 0 && row < g.length; // row number is in range
		boolean two = col >= 0 && col < g[0].length; // col number is in range
		return one && two && g[row][col] == 0;
	}

	public static void main(String[] args) {
		int[][] g1 = { //
				{ 1, 1, 1, 1, 1, 1, 1, 0 }, //
				{ 1, 0, 0, 0, 0, 1, 1, 0 }, //
				{ 1, 0, 1, 0, 1, 1, 1, 0 }, //
				{ 1, 0, 0, 0, 0, 1, 0, 1 }, //
				{ 1, 1, 1, 1, 1, 1, 1, 0 } };//
		System.out.println(closedIslands(g1)); // 2

		int[][] g2 = { //
				{ 0, 0, 1, 0, 0 }, //
				{ 0, 1, 0, 1, 0 }, //
				{ 0, 1, 1, 1, 0 } };//
		System.out.println(closedIslands(g2)); // 1

		int[][] g3 = { //
				{ 1, 1, 1, 1, 1, 1, 1 }, //
				{ 1, 0, 0, 0, 0, 0, 1 }, //
				{ 1, 0, 1, 1, 1, 0, 1 }, //
				{ 1, 0, 1, 0, 1, 0, 1 }, //
				{ 1, 0, 1, 1, 1, 0, 1 }, //
				{ 1, 0, 0, 0, 0, 0, 1 }, //
				{ 1, 1, 1, 1, 1, 1, 1 } };//
		System.out.println(closedIslands(g3)); // 2

	}
}
