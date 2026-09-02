package com.svetanis.datastructures.graph.islands;

import java.util.ArrayDeque;
import java.util.Queue;

// 200. Number of Islands

// the same sweep as NumberOfIslandsMatrixSubmit with the
// pending cells in a queue instead of on the call stack.
// takes LC's char[][] and allocates nothing but the queue,
// so it can be pasted as is.

// NumberOfIslandsMatrixSubmit recurses once per land cell,
// so a 300x300 grid of solid land -- inside LC's constraints
// -- asks for 90,000 nested calls and throws
// StackOverflowError instead of answering. this version has
// no recursion at all: the queue lives on the heap and holds
// one band of the island at a time, which on a solid n x n
// grid never exceeds n cells.

public final class NumberOfIslandsBfsSubmit {
	// Time complexity: O(r * c) -- every cell is polled at most once
	// Space Complexity: O(r * c) -- the boolean[r][c] visited array. the
	// queue itself is only O(r + c): on a solid n x n grid it peaks at
	// exactly n. there is no stack depth here, so the array is the
	// whole cost.

	private static final char WATER = '0';

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public static int count(char[][] g) {
		int count = 0;
		int n = g.length;
		int m = g[0].length;
		boolean[][] visited = new boolean[n][m];
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (g[r][c] != WATER && !visited[r][c]) {
					visited[r][c] = true; // marked before the walk starts,
					bfs(g, r, c, visited); // never when the walk polls it
					count++; // the sweep counts, the walk never does
				}
			}
		}
		return count;
	}

	private static void bfs(char[][] g, int sr, int sc, boolean[][] visited) {
		Queue<int[]> queue = new ArrayDeque<>();
		queue.add(new int[] { sr, sc });
		while (!queue.isEmpty()) {
			int[] curr = queue.poll();
			for (int k = 0; k < dx.length; ++k) {
				int x = curr[0] + dx[k];
				int y = curr[1] + dy[k];
				// same test as the dfs in NumberOfIslandsMatrixSubmit:
				// in bounds, land, and not already accounted for
				if (isLand(g, x, y) && !visited[x][y]) {
					// mark and queue on the same two lines, always.
					// the mark means "already spoken for, nobody else
					// needs to queue it", and that is true the moment
					// the cell goes in. moving it after the poll
					// leaves a window in which the cell is queued and
					// unmarked, so every neighbour polled during that
					// window queues it again -- twice the queue
					// traffic on a solid grid, same answer, no symptom
					visited[x][y] = true;
					queue.add(new int[] { x, y });
				}
			}
		}
	}

	private static boolean isLand(char[][] g, int row, int col) {
		boolean one = row >= 0 && row < g.length; // row number is in range
		boolean two = col >= 0 && col < g[0].length; // col number is in range
		return one && two && g[row][col] != WATER;
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

		char[][] g4 = new char[300][300];
		for (int r = 0; r < 300; r++) {
			for (int c = 0; c < 300; c++) {
				g4[r][c] = '1';
			}
		}
		// the grid that throws StackOverflowError in every
		// recursive file in this package
		System.out.println(count(g4)); // 1
	}
}
