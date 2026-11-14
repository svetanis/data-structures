package com.svetanis.datastructures.graph.islands;

// 1020. Number of Enclaves
//
// Input: g -- an m x n grid of 0 (sea) and 1 (land). A move goes from a land cell to a land cell
// beside it (up, down, left or right), or off the edge of the grid. Returns the number of land
// cells from which you cannot walk off the edge of the grid, however many moves you make. The grid
// is changed by the call: the land cells that can reach the edge are turned to 0. On LeetCode:
// 1 <= m, n <= 500, every cell is 0 or 1.
//
// The one idea: count the land that can escape and take it away. A land cell can walk off the edge
// exactly when it is joined, through land, to a land cell on the edge of the grid. So the sweep
// starts a walk from every land cell on the edge, and each walk turns all the land joined to it
// into sea. What is still land afterwards cannot reach the edge, and adding up the 1s left in the
// grid is the answer.
//
// Time: O(r * c) -- each cell is walked at most once, and the final count looks at each cell once.
// Space: O(r * c) -- the recursion depth: the land joined to the edge can snake through every
// cell.
//
// Known limit: the recursion overflows the default Java stack on a very large region of land.
// LC allows 500 x 500, and a solid block of land that size throws StackOverflowError. It is left
// as it is; NumberOfEnclavesIterative is the same algorithm with an explicit stack.
//
// Siblings: NumberOfEnclavesIterative.

public final class NumberOfEnclaves {

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public static int countEnclaves(int[][] g) {
		int n = g.length;
		int m = g[0].length;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (landOnBoundary(g, r, c)) {
					dfs(g, r, c); // WALK: sink all the land joined to this edge cell
				}
			}
		}
		return count(g);
	}

	private static int count(int[][] g) {
		int count = 0;
		for (int[] row : g) {
			for (int val : row) {
				count += val; // TAKE: land is 1, sea is 0, so the sum is the land left
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
