package com.svetanis.datastructures.graph.islands;

// 695. Max Area of Island
//
// Input: g -- an m x n grid of 0 (water) and 1 (land). An island is a group of land cells joined by
// sides (up, down, left or right). Returns the area of the largest island, which is its number of
// cells, or 0 if there is no land. The grid is changed by the call: every land cell ends up as 0.
// On LeetCode: 1 <= m, n <= 50, every cell is 0 or 1.
//
// The one idea: sweep the grid cell by cell and walk each cell once. Walking a land cell turns it
// into water, so the walk never counts it twice, and it returns 1 plus what the walks of its four
// neighbours return: the number of land cells joined to it. Walking a water cell returns 0, so the
// sweep can start a walk on every cell and the walks that start on water add nothing. The
// biggest value any walk returns is the largest island, and writing 0 into the grid is what stops
// an island being found a second time from another of its cells.
//
// Time: O(r * c) -- the sweep visits each cell once and each land cell is walked once.
// Space: O(r * c) -- the recursion depth: one island can snake through every cell. At LC's 50 x 50
// that is 2,500 frames, which a default stack holds.

public final class MaxAreaOfIsland {

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
				max = Math.max(max, dfs(g, r, c)); // TAKE: the larger of the area so far and this island
			}
		}
		return max;
	}

	private static int dfs(int[][] g, int row, int col) {
		if (g[row][col] == 0) {
			return 0; // BASE: water, or land already counted
		}
		int area = 1;
		// mark this cell as water
		g[row][col] = 0;
		for (int k = 0; k < dx.length; ++k) {
			int x = row + dx[k];
			int y = col + dy[k];
			if (safe(g, x, y)) {
				area += dfs(g, x, y); // WALK: add the land joined through this side
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
