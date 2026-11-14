package com.svetanis.datastructures.graph.islands;

import java.util.ArrayDeque;
import java.util.Deque;

// 1254. Number of Closed Islands
//
// Input: g -- an m x n grid of 0 (land) and 1 (water). An island is a group of land cells joined
// by sides (up, down, left or right); it is closed if water borders it on all four sides all the
// way round, which means none of its cells lies on the edge of the grid. Returns the number of
// closed islands. The grid is changed by the call: every land cell ends up as 1. On LeetCode:
// 1 <= m, n <= 100, every cell is 0 or 1.
//
// The one idea: the same count as NumberOfClosedIslands, with a stack of cells in place of the
// recursion. The sweep walks each island from the first land cell it meets. One stack entry is one
// land cell of the island being walked whose neighbours have not been looked at yet. A cell is
// turned to water when it is pushed, so no cell is pushed twice and the island is never met
// again. The walk starts with closed = 1 and drops it to 0 as soon as any cell it takes off the
// stack is on the edge of the grid; it still empties the stack, because the rest of an open island
// must be turned to water too, or the sweep would meet it again and count it.
//
// Time: O(r * c) -- each land cell is pushed once and looks at four sides.
// Space: O(r * c) -- the stack, up to one entry per land cell.
//
// Siblings: NumberOfClosedIslands is the recursive walk, which overflows a default Java stack on
// a 100 x 100 grid of land.

public final class NumberOfClosedIslandsIterative {

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
					count += walk(g, r, c); // TAKE: 1 if the island just walked was closed
				}
			}
		}
		return count;
	}

	private static int walk(int[][] g, int row, int col) {
		int closed = 1;
		Deque<int[]> stack = new ArrayDeque<>();
		g[row][col] = 1; // MARK: turned to water when pushed, so no cell is pushed twice
		stack.push(new int[] { row, col });
		while (!stack.isEmpty()) {
			int[] cell = stack.pop();
			if (!closed(g, cell[0], cell[1])) {
				closed = 0; // one cell on the edge makes the island open
			}
			for (int k = 0; k < dx.length; ++k) { // WALK: the four sides of this cell
				int x = cell[0] + dx[k];
				int y = cell[1] + dy[k];
				if (safe(g, x, y)) {
					g[x][y] = 1;
					stack.push(new int[] { x, y });
				}
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
