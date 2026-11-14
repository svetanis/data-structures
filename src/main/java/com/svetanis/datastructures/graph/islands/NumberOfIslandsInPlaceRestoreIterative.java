package com.svetanis.datastructures.graph.islands;

import java.util.ArrayDeque;
import java.util.Deque;

// 200. Number of Islands
//
// Input: g -- an m x n map of characters, '1' for land and '0' for water, exactly as LeetCode
// gives it. An island is a group of land cells joined by sides (up, down, left or right), and
// everything off the edge of the grid is water. Returns the number of islands. The grid is
// changed during the call and put back before it returns. On LeetCode: 1 <= m, n <= 300, every
// cell is '0' or '1'.
//
// The one idea: the same count as NumberOfIslandsInPlaceRestore, with a stack of cells in place of
// the recursion. No visited array and no damage to the caller's grid: a walked cell is written
// with a THIRD value, SEEN, which is neither land nor water, so the sweep passes over it and an
// island is counted once. One stack entry is one land cell, already written SEEN, whose
// neighbours have not been looked at yet; a neighbour is written SEEN the moment it is pushed, so
// no cell is pushed twice. One sweep after the count puts every SEEN cell back to land.
//
// Time: O(r * c) -- two sweeps of the grid, and every land cell is pushed once.
// Space: O(r * c) -- the stack, up to one entry per land cell.
//
// Siblings: NumberOfIslandsInPlaceRestore is the recursive walk, which overflows a default Java
// stack on a very large island. NumberOfIslandsInPlaceGuardedIterative also uses no visited array
// but leaves the grid as solid water.

public final class NumberOfIslandsInPlaceRestoreIterative {

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
		Deque<int[]> stack = new ArrayDeque<>();
		g[row][col] = SEEN; // MARK: when pushed, so no cell is pushed twice
		stack.push(new int[] { row, col });
		while (!stack.isEmpty()) {
			int[] cell = stack.pop();
			for (int k = 0; k < dx.length; ++k) { // WALK: the four sides of this cell
				int x = cell[0] + dx[k];
				int y = cell[1] + dy[k];
				if (isLand(g, x, y)) {
					g[x][y] = SEEN;
					stack.push(new int[] { x, y });
				}
			}
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
