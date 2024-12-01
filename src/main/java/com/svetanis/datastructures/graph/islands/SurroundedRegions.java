package com.svetanis.datastructures.graph.islands;

import com.svetanis.java.base.utils.Print;

// 130. Surrounded Regions

public final class SurroundedRegions {
	// Time complexity: O(r * c)
	// Space Complexity: O(r * c)

	// horizontal + vertical moves
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public static void closedIslands(char[][] g) {
		int n = g.length;
		int m = g[0].length;
		// 0 is land, 1 is water
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (boarder(g, r, c) && g[r][c] == 'O') {
					dfs(g, r, c);
				}
			}
		}
		flip(g);
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

	private static void dfs(char[][] g, int row, int col) {
		g[row][col] = '.';
		// considers only the neighbors as adjacent vertices
		// and recur for all connected neighbors
		for (int k = 0; k < dx.length; ++k) {
			int x = row + dx[k];
			int y = col + dy[k];
			if (safe(g, x, y)) {
				dfs(g, x, y);
			}
		}
	}

	private static boolean boarder(char[][] g, int row, int col) {
		boolean one = row == 0 || row == g.length - 1;
		boolean two = col == 0 || col == g[0].length - 1;
		return one || two;
	}

	private static boolean safe(char[][] g, int row, int col) {
		boolean one = row >= 0 && row < g.length; // row number is in range
		boolean two = col >= 0 && col < g[0].length; // col number is in range
		return one && two && g[row][col] == 'O';
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
