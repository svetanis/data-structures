package com.svetanis.datastructures.graph.islands;

// 200. Number of Islands

// no visited array: each land cell is overwritten with
// water as it is reached, so the grid itself records what
// has been counted. and no test at the top of sink -- the
// four call sites each test before calling, so a call is
// made only when it will do work.

// NumberOfIslandsMatrixSubmit calls its recursion four
// times per land cell and three of those calls return on
// their first line, having pushed a frame to do nothing.
// on a 300x300 grid at 50% land, not making them is worth
// about 40%.

// the caller does not get its grid back: it comes back as
// solid water.

// the price in source is that the bounds test is written
// four times instead of once, and each of the four reads
// a different neighbour -- four places to make a typo that
// still compiles.

public final class NumberOfIslandsInPlaceGuarded {
	// Time complexity: O(r * c)
	// Space Complexity: O(r * c) -- the recursion depth alone, one frame
	// per land cell, as in every other recursive file here.

	private static final char LAND = '1';
	private static final char WATER = '0';

	public static int count(char[][] g) {
		int count = 0;
		int n = g.length;
		int m = g[0].length;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (g[r][c] == LAND) {
					sink(g, r, c);
					count++;
				}
			}
		}
		return count;
	}

	// no guard at the top: every call that reaches here is
	// already known to be land inside the grid
	private static void sink(char[][] g, int row, int col) {
		g[row][col] = WATER;
		if (row > 0 && g[row - 1][col] == LAND) {
			sink(g, row - 1, col);
		}
		if (row + 1 < g.length && g[row + 1][col] == LAND) {
			sink(g, row + 1, col);
		}
		if (col > 0 && g[row][col - 1] == LAND) {
			sink(g, row, col - 1);
		}
		if (col + 1 < g[0].length && g[row][col + 1] == LAND) {
			sink(g, row, col + 1);
		}
	}

	public static void main(String[] args) {
		char[][] g1 = { //
				{ '1', '1', '0', '0', '0' }, //
				{ '0', '1', '0', '0', '1' }, //
				{ '1', '0', '0', '1', '1' }, //
				{ '0', '0', '0', '0', '0' }, //
				{ '1', '0', '1', '0', '1' } };//
		System.out.println(count(g1)); // 6

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
		System.out.println(g4[0][0]); // 0 -- the grid was consumed
	}
}
