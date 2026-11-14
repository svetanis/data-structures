package com.svetanis.datastructures.graph.islands;

// 200. Number of Islands
//
// Input: g -- an m x n map of characters, '1' for land and '0' for water, exactly as LeetCode
// gives it. An island is a group of land cells joined by sides (up, down, left or right), and
// everything off the edge of the grid is water. Returns the number of islands. The grid is
// changed by the call: it comes back as solid water. On LeetCode: 1 <= m, n <= 300, every cell is
// '0' or '1'.
//
// The one idea: no visited array. The grid records what has been counted: sweep the grid cell by
// cell, and a land cell is the first cell of an island nobody has counted, because every cell of
// an island already walked has been overwritten with water. Counting it adds one, and sink walks
// the island depth first, overwriting each land cell with water as it is reached. There is no
// test at the top of sink: the four call sites each test before calling, so a call is made only
// when it will do work, and NumberOfIslandsMatrixSubmit, which tests at the top, pushes a frame
// for each of the three calls per cell that return at once.
//
// The price in source is that the bounds test is written four times instead of once, and each of
// the four reads a different neighbour -- four places to make a typo that still compiles.
//
// Time: O(r * c) -- every land cell is sunk once and looks at four sides.
// Space: O(r * c) -- the recursion depth alone, one frame per land cell in the worst case, as in
// every other recursive file here; there is no visited array.
//
// Known limit: the recursion overflows the default Java stack on a very large island. LC allows
// 300 x 300, i.e. 90,000 frames, and a grid of all land that size throws StackOverflowError. It is
// left as it is; NumberOfIslandsInPlaceGuardedIterative is the same algorithm with an explicit
// stack.
//
// Siblings: NumberOfIslandsInPlaceRestore also uses no visited array but hands the grid back
// unchanged; NumberOfIslandsMatrixSubmit is the same walk with a visited array;
// NumberOfIslandsInPlaceGuardedIterative.

public final class NumberOfIslandsInPlaceGuarded {

	private static final char LAND = '1';
	private static final char WATER = '0';

	public static int count(char[][] g) {
		int count = 0;
		int n = g.length;
		int m = g[0].length;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (g[r][c] == LAND) {
					sink(g, r, c); // WALK: overwrite the whole island with water
					count++; // TAKE: one more island
				}
			}
		}
		return count;
	}

	// no guard at the top: every call that reaches here is
	// already known to be land inside the grid
	private static void sink(char[][] g, int row, int col) {
		g[row][col] = WATER; // MARK: this cell is counted
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
