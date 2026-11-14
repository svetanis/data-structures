package com.svetanis.datastructures.graph.islands;

import java.util.ArrayDeque;
import java.util.Deque;

// 200. Number of Islands
//
// Input: g -- an m x n map of characters, '1' for land and '0' for water, exactly as LeetCode
// gives it. An island is a group of land cells joined by sides (up, down, left or right), and
// everything off the edge of the grid is water. Returns the number of islands. The grid is
// changed by the call: it comes back as solid water. On LeetCode: 1 <= m, n <= 300, every cell is
// '0' or '1'.
//
// The one idea: the same count as NumberOfIslandsInPlaceGuarded, with a stack of cells in place
// of the recursion. No visited array: a land cell met by the sweep is the first cell of an island
// nobody has counted, because every cell of an island already walked has been overwritten with
// water. One stack entry is one land cell, already overwritten with water, whose neighbours have
// not been looked at yet. A neighbour is tested before it is pushed and overwritten the moment it
// is pushed, so every entry is land when it goes on and no cell is pushed twice.
//
// Time: O(r * c) -- every land cell is pushed once and looks at four sides.
// Space: O(r * c) -- the stack, up to one entry per land cell; there is no visited array.
//
// Siblings: NumberOfIslandsInPlaceGuarded is the recursive walk, which overflows a default Java
// stack on a very large island. NumberOfIslandsInPlaceRestoreIterative also uses no visited array
// but hands the grid back unchanged.

public final class NumberOfIslandsInPlaceGuardedIterative {

	private static final char LAND = '1';
	private static final char WATER = '0';

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
					sink(g, r, c); // WALK: overwrite the whole island with water
					count++; // TAKE: one more island
				}
			}
		}
		return count;
	}

	private static void sink(char[][] g, int row, int col) {
		Deque<int[]> stack = new ArrayDeque<>();
		g[row][col] = WATER; // MARK: when pushed, so no cell is pushed twice
		stack.push(new int[] { row, col });
		while (!stack.isEmpty()) {
			int[] cell = stack.pop();
			for (int k = 0; k < dx.length; ++k) { // WALK: the four sides of this cell
				int x = cell[0] + dx[k];
				int y = cell[1] + dy[k];
				if (isLand(g, x, y)) {
					g[x][y] = WATER;
					stack.push(new int[] { x, y });
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
