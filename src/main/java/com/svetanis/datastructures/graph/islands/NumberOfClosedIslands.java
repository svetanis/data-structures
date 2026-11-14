package com.svetanis.datastructures.graph.islands;

// 1254. Number of Closed Islands
//
// Input: g -- an m x n grid of 0 (land) and 1 (water). An island is a group of land cells joined
// by sides (up, down, left or right); it is closed if water borders it on all four sides all the
// way round, which means none of its cells lies on the edge of the grid. Returns the number of
// closed islands. The grid is changed by the call: every land cell ends up as 1. On LeetCode:
// 1 <= m, n <= 100, every cell is 0 or 1.
//
// The one idea: sweep the grid, and walk each island from the first land cell the sweep meets,
// turning every cell it walks into water so it is never met again. A walk returns 1 if the island
// is closed and 0 if not. A single cell on the edge of the grid makes the whole island open, so
// each cell contributes "am I off the edge?" and the walk combines the answers of all its cells
// with &=. Every neighbour is still walked after the answer is already 0, because the rest of an
// open island must be turned to water too, or the sweep would meet it again and count it.
//
// Time: O(r * c) -- each cell is walked at most once.
// Space: O(r * c) -- the recursion depth: one island can snake through every cell.
//
// Known limit: the recursion overflows the default Java stack on a very large island. LC allows
// 100 x 100, i.e. 10,000 frames, and a grid of all land that size throws StackOverflowError on a
// fresh JVM (frames get smaller once the method has been compiled, so a warmed-up run can
// survive). It is left as it is; NumberOfClosedIslandsIterative is the same algorithm with an
// explicit stack.
//
// Siblings: NumberOfClosedIslandsIterative.

public final class NumberOfClosedIslands {

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
					count += dfs(g, r, c); // TAKE: 1 if the island just walked was closed
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
				closed &= dfs(g, x, y); // WALK: one cell on the edge makes the island open
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
