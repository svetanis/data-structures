package com.svetanis.datastructures.graph.islands;

// 695. Max Area of Island

public final class MaxAreaOfIsland {
	// Time complexity: O(r * c)
	// Space Complexity: O(r * c)

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public static int maxArea(int[][] g) {
		int n = g.length;
		int m = g[0].length;
		// 1 is land, 0 is water
		int max = 0;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				max = Math.max(max, dfs(g, r, c));
			}
		}
		return max;
	}

	private static int dfs(int[][] g, int row, int col) {
		if (g[row][col] == 0) {
			return 0;
		}
		int area = 1;
		// mark this cell as water
		g[row][col] = 0;
		for (int k = 0; k < dx.length; ++k) {
			int x = row + dx[k];
			int y = col + dy[k];
			if (safe(g, x, y)) {
				area += dfs(g, x, y);
			}
		}
		return area;
	}

	private static boolean safe(int[][] g, int row, int col) {
		boolean one = row >= 0 && row < g.length; // row number is in range
		boolean two = col >= 0 && col < g[0].length; // col number is in range
		return one && two;
	}

	public static void main(String[] args) {
		int[][] g1 = { //
				{ 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 }, //
				{ 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, //
				{ 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0 }, //
				{ 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0 }, //
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 }, //
				{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 }, //
				{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 } };//
		System.out.println(maxArea(g1)); // 6

		int[][] g2 = { { 0, 0, 0, 0, 0, 0, 0, 0 } };//
		System.out.println(maxArea(g2)); // 0
	}
}
