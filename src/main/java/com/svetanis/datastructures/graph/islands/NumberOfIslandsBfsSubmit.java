package com.svetanis.datastructures.graph.islands;

import java.util.ArrayDeque;
import java.util.Queue;

// 200. Number of Islands
//
// Input: g -- an m x n map of characters, '1' for land and '0' for water, exactly as LeetCode
// gives it. An island is a group of land cells joined by sides (up, down, left or right), and
// everything off the edge of the grid is water. Returns the number of islands. On LeetCode:
// 1 <= m, n <= 300, every cell is '0' or '1'.
//
// The one idea: the same sweep as NumberOfIslandsMatrixSubmit with the pending cells in a queue
// instead of on the call stack. A land cell that no earlier walk has marked is the first cell of
// an island nobody has counted, so the count goes up by one and a breadth-first walk marks every
// land cell joined to it. visited[r][c] says whether a cell is already spoken for; it is set the
// moment a cell goes into the queue, never when the walk takes it out, so no cell is queued twice.
// Marking after the take-out would leave a window in which a cell is queued and unmarked, and
// every neighbour taken out during that window would queue it again: the same answer with several
// times the queue traffic. The recursive version asks for up to 90,000 nested calls on a 300 x 300
// grid of land and throws StackOverflowError; this one has no recursion, so it has no such limit.
//
// Time: O(r * c) -- every cell is queued at most once and looks at four sides.
// Space: O(r * c) -- the boolean[r][c] visited array. The queue itself is only O(r + c): it holds
// one band of the island at a time, which on a solid n x n grid never exceeds n cells.
//
// Siblings: NumberOfIslandsMatrixSubmit is the recursive walk on the same input. It takes LC's
// char[][] and allocates nothing but the queue and the visited array, so it can be pasted as is.

public final class NumberOfIslandsBfsSubmit {

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
					visited[r][c] = true; // MARK: the first cell, before the walk starts
					bfs(g, r, c, visited); // WALK: mark the rest of the island
					count++; // TAKE: the sweep counts, the walk never does
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
					// MARK and queue on the same two lines, always
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
		// a 300 x 300 block of land, LC's largest grid: the recursive
		// versions of this problem throw StackOverflowError on it
		System.out.println(count(g4)); // 1
	}
}
