package com.svetanis.datastructures.graph.islands;

import java.util.ArrayDeque;
import java.util.Deque;

// 130. Surrounded Regions
//
// Input: g -- an m x n board of 'X' and 'O'. A region is a group of 'O' cells joined by sides (up,
// down, left or right); it is surrounded if none of its cells lies on the edge of the board.
// Changes the board in place: every 'O' of every surrounded region becomes 'X', and the other 'O'
// cells stay. Returns nothing. The method is called closedIslands, after the sibling problem
// NumberOfClosedIslands, but it solves this one. On LeetCode: 1 <= m, n <= 200, every cell is
// 'X' or 'O'.
//
// The one idea: find the 'O' cells that are NOT surrounded and keep them. An 'O' is safe exactly
// when it is joined, through 'O' cells, to an 'O' on the edge of the board. So the queue starts
// with every 'O' on the edge, and each cell taken off it is overwritten with '.', meaning "safe",
// and offers its four neighbours. One queue entry is one position that may or may not still be an
// 'O'; a position that is off the board or not an 'O' (including one already turned to '.') is
// dropped when it is taken off. Every 'O' still on the board afterwards is joined to no edge 'O',
// so it is turned to 'X', and the '.' cells go back to 'O'.
//
// Time: O(r * c) -- each cell is queued a constant number of times (as an edge seed, and once from
// each of its four neighbours), and the final pass looks at each cell once.
// Space: O(r * c) -- the queue, which can hold the same position more than once because a cell is
// marked when it is taken off, not when it is queued.
//
// Siblings: SurroundedRegionsDfs is the recursive walk, which overflows a default Java stack on a
// very large region; this version has no recursion.

public final class SurroundedRegionsBfs {

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public static void closedIslands(char[][] g) {
		Deque<int[]> dq = initQueue(g);
		while (!dq.isEmpty()) {
			int[] cell = dq.poll();
			int x = cell[0];
			int y = cell[1];
			if (invalid(g, x, y)) {
				continue; // SKIP: off the board, not an 'O', or already marked safe
			}
			g[x][y] = '.'; // MARK: safe
			for (int k = 0; k < 4; k++) {
				int row = x + dx[k];
				int col = y + dy[k];
				dq.offer(new int[] { row, col }); // WALK: offer the four sides
			}
		}
		flip(g);
	}

	private static boolean invalid(char[][] g, int x, int y) {
		int n = g.length;
		int m = g[0].length;
		return x < 0 || y < 0 || x >= n || y >= m || g[x][y] != 'O';
	}

	private static Deque<int[]> initQueue(char[][] g) {
		int rows = g.length;
		int cols = g[0].length;
		Deque<int[]> dq = new ArrayDeque<>();
		for (int i = 0; i < rows; i++) {
			if (g[i][0] == 'O') {
				dq.offer(new int[] { i, 0 }); // SEED: the left edge
			}
			if (g[i][cols - 1] == 'O') {
				dq.offer(new int[] { i, cols - 1 }); // SEED: the right edge
			}
		}
		for (int j = 0; j < cols; j++) {
			if (g[0][j] == 'O') {
				dq.offer(new int[] { 0, j }); // SEED: the top edge
			}
			if (g[rows - 1][j] == 'O') {
				dq.offer(new int[] { rows - 1, j }); // SEED: the bottom edge
			}
		}
		return dq;
	}

	private static void flip(char[][] g) {
		int n = g.length;
		int m = g[0].length;
		// flip all remaining 'O' to x and back all '.' to 'O'
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (g[i][j] == '.') {
					g[i][j] = 'O'; // RESTORE: a safe cell
				} else if (g[i][j] == 'O') {
					g[i][j] = 'X'; // CAPTURE: nothing joined it to the edge
				}
			}
		}
	}

	private static void print(char[][] g) {
		for (char[] row : g) {
			StringBuilder sb = new StringBuilder();
			for (char ch : row) {
				sb.append(ch).append(' ');
			}
			System.out.println(sb);
		}
		System.out.println();
	}

	public static void main(String[] args) {
		char[][] g1 = { //
				{ 'X', 'X', 'X', 'X' }, //
				{ 'X', 'O', 'O', 'X' }, //
				{ 'X', 'X', 'O', 'X' }, //
				{ 'X', 'O', 'X', 'X' } };//
		closedIslands(g1);
		print(g1); // rows: X X X X / X X X X / X X X X / X O X X

		char[][] g2 = { { 'X' } };//
		closedIslands(g2);
		print(g2); // rows: X
	}
}
