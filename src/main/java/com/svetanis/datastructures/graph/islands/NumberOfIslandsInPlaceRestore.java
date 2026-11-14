package com.svetanis.datastructures.graph.islands;

// 200. Number of Islands
//
// Input: g -- an m x n map of characters, '1' for land and '0' for water, exactly as LeetCode
// gives it. An island is a group of land cells joined by sides (up, down, left or right), and
// everything off the edge of the grid is water. Returns the number of islands. The grid is
// changed during the call and put back before it returns. On LeetCode: 1 <= m, n <= 300, every
// cell is '0' or '1'.
//
// The one idea: no visited array and no damage to the caller's grid. A walked cell is written with
// a THIRD value, SEEN, which is neither land nor water, so the sweep passes over it and an island
// is counted once. One sweep at the end puts every SEEN cell back to land. The third value is what
// makes this work: overwriting land with water, as NumberOfIslandsInPlaceGuarded does, loses the
// difference between "water all along" and "land I have already counted", so there is nothing
// left to restore from.
//
// The restoring sweep runs AFTER the count, never inside the walk. A mark put back on the way out
// of a call is a mark the sweep walks into again, and the same island would then be counted once
// per cell.
//
// Time: O(r * c) -- two sweeps of the grid, not one.
// Space: O(r * c) -- the recursion depth alone, one frame per land cell in the worst case.
// Restoring the grid costs a second pass, not extra memory.
//
// Known limit: the recursion overflows the default Java stack on a very large island. LC allows
// 300 x 300, i.e. 90,000 frames, and a grid of all land that size throws StackOverflowError. It is
// left as it is; NumberOfIslandsInPlaceRestoreIterative is the same algorithm with an explicit
// stack.
//
// Siblings: NumberOfIslandsInPlaceGuarded also uses no visited array but leaves the grid as solid
// water; NumberOfIslandsInPlaceRestoreIterative.

public final class NumberOfIslandsInPlaceRestore {

	private static final char LAND = '1';
	private static final char SEEN = '2';

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public static int count(char[][] g) {
		int count = 0;
		int n = g.length;
		int m = g[0].length;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (g[r][c] == LAND) {
					mark(g, r, c); // WALK: write SEEN over the whole island
					count++; // TAKE: one more island
				}
			}
		}
		// RESTORE: after the count, never inside the walk
		restore(g);
		return count;
	}

	private static void mark(char[][] g, int row, int col) {
		if (!isLand(g, row, col)) {
			return;
		}
		g[row][col] = SEEN;
		for (int k = 0; k < dx.length; ++k) {
			mark(g, row + dx[k], col + dy[k]);
		}
	}

	private static void restore(char[][] g) {
		for (int r = 0; r < g.length; r++) {
			for (int c = 0; c < g[0].length; c++) {
				if (g[r][c] == SEEN) {
					g[r][c] = LAND; // RESTORE: SEEN was land before the walk
				}
			}
		}
	}

	private static boolean isLand(char[][] g, int row, int col) {
		boolean one = row >= 0 && row < g.length; // row number is in range
		boolean two = col >= 0 && col < g[0].length; // col number is in range
		return one && two && g[row][col] == LAND;
	}

	public static void main(String[] args) {
		char[][] g1 = { //
				{ '1', '1', '0', '0', '0' }, //
				{ '0', '1', '0', '0', '1' }, //
				{ '1', '0', '0', '1', '1' }, //
				{ '0', '0', '0', '0', '0' }, //
				{ '1', '0', '1', '0', '1' } };//
		System.out.println(count(g1)); // 6
		System.out.println(count(g1)); // 6 -- the grid survived

		char[][] g2 = { //
				{ '1', '1', '1', '1', '0' }, //
				{ '1', '1', '0', '1', '0' }, //
				{ '1', '1', '0', '0', '0' }, //
				{ '0', '0', '0', '0', '0' } };//
		System.out.println(count(g2)); // 1

		char[][] g3 = { //
				{ '1', '1', '0', '0', '0' }, //
				{ '1', '1', '0', '0', '0' }, //
				{ '0', '0', '1', '0', '0' }, //
				{ '0', '0', '0', '1', '1' } };//
		System.out.println(count(g3)); // 3

		char[][] g4 = { //
				{ '1', '1', '1' }, //
				{ '1', '1', '1' }, //
				{ '1', '1', '1' } };//
		System.out.println(count(g4)); // 1
		System.out.println(g4[0][0]); // 1 -- the grid came back unchanged
	}
}
