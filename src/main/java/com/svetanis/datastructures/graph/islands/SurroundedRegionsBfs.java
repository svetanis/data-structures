package com.svetanis.datastructures.graph.islands;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.java.base.utils.Print;

// 130. Surrounded Regions

public final class SurroundedRegionsBfs {
	// Time complexity: O(r * c)
	// Space Complexity: O(r * c)

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public static void closedIslands(char[][] g) {
		// 0 is land, 1 is water
		Deque<int[]> dq = initQueue(g);
		while (!dq.isEmpty()) {
			int[] cell = dq.poll();
			int x = cell[0];
			int y = cell[1];
			if (invalid(g, x, y)) {
				continue;
			}
			g[x][y] = '.';
			for (int k = 0; k < 4; k++) {
				int row = x + dx[k];
				int col = y + dy[k];
				dq.offer(new int[] { row, col });
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
				dq.offer(new int[] { i, 0 });
			}
			if (g[i][cols - 1] == 'O') {
				dq.offer(new int[] { i, cols - 1 });
			}
		}
		for (int j = 0; j < cols; j++) {
			if (g[0][j] == 'O') {
				dq.offer(new int[] { 0, j });
			}
			if (g[rows - 1][j] == 'O') {
				dq.offer(new int[] { rows - 1, j });
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
					g[i][j] = 'O';
				} else if (g[i][j] == 'O') {
					g[i][j] = 'X';
				}
			}
		}
	}

	public static void main(String[] args) {
		char[][] g1 = { //
				{ 'X', 'X', 'X', 'X' }, //
				{ 'X', 'O', 'O', 'X' }, //
				{ 'X', 'X', 'O', 'X' }, //
				{ 'X', 'O', 'X', 'X' } };//
		closedIslands(g1);
		Print.print(g1);

		char[][] g2 = { { 'X' } };//
		closedIslands(g2);
		Print.print(g2);
	}
}
