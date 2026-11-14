package com.svetanis.datastructures.graph.islands;

import java.util.ArrayDeque;
import java.util.Deque;

// 1020. Number of Enclaves
//
// Input: g -- an m x n grid of 0 (sea) and 1 (land). A move goes from a land cell to a land cell
// beside it (up, down, left or right), or off the edge of the grid. Returns the number of land
// cells from which you cannot walk off the edge of the grid, however many moves you make. The grid
// is changed by the call: the land cells that can reach the edge are turned to 0. On LeetCode:
// 1 <= m, n <= 500, every cell is 0 or 1.
//
// The one idea: the same count-the-land-that-cannot-escape as NumberOfEnclaves, with a stack of
// cells in place of the recursion. A land cell can walk off the edge exactly when it is joined,
// through land, to a land cell on the edge. One stack entry is one such cell whose neighbours
// have not been looked at yet. A cell is turned to 0 when it is pushed, so no cell is pushed
// twice, and whatever is still 1 when every edge walk is done cannot reach the edge.
//
// Time: O(r * c) -- each cell is pushed at most once, and the final count looks at each cell once.
// Space: O(r * c) -- the stack, up to one entry per land cell.
//
// Siblings: NumberOfEnclaves is the recursive walk, which overflows a default Java stack on a very
// large region of land.

public final class NumberOfEnclavesIterative {

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public static int countEnclaves(int[][] g) {
		int n = g.length;
		int m = g[0].length;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (landOnBoundary(g, r, c)) {
					sink(g, r, c); // WALK: sink all the land joined to this edge cell
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

	private static void sink(int[][] g, int row, int col) {
		Deque<int[]> stack = new ArrayDeque<>();
		g[row][col] = 0; // MARK: turned to sea when pushed, so no cell is pushed twice
		stack.push(new int[] { row, col });
		while (!stack.isEmpty()) {
			int[] cell = stack.pop();
			for (int k = 0; k < dx.length; ++k) { // WALK: the four sides of this cell
				int x = cell[0] + dx[k];
				int y = cell[1] + dy[k];
				if (isSafe(g, x, y)) {
					g[x][y] = 0;
					stack.push(new int[] { x, y });
				}
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
