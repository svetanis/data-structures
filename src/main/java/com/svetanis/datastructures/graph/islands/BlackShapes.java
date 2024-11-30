package com.svetanis.datastructures.graph.islands;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public final class BlackShapes {

	private static final char WHITE = 'O';
	private static int[] dx = { -1, 0, 0, 1 };
	private static int[] dy = { 0, -1, 1, 0 };

	public static int count(char[][] g) {
		// Time complexity: O(ROW x COL)

		int count = 0;
		int row = g.length;
		int col = g[0].length;

		boolean[][] visited = new boolean[row][col];
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				if (g[i][j] != WHITE && !visited[i][j]) {
					dfs(g, i, j, visited);
					++count;
				}
			}
		}
		return count;
	}

	private static void dfs(char[][] g, int row, int col, boolean[][] visited) {
		// mark this cell as visited
		visited[row][col] = true;
		// considers only the neighbors as adjacent vertices
		// recur for all connected neighbors
		for (int k = 0; k < dx.length; ++k) {
			int x = row + dx[k];
			int y = col + dy[k];
			if (isSafe(g, x, y, visited)) {
				dfs(g, x, y, visited);
			}
		}
	}

	private static boolean isSafe(char[][] g, int r, int c, boolean[][] visited) {
		int n = g.length;
		int m = g[0].length;
		boolean one = r >= 0 && r < n; // row number is in range
		boolean two = c >= 0 && c < m; // col number is in range
		return one && two && g[r][c] != WHITE && !visited[r][c];
	}

	private static char[][] toMatrix(List<String> list) {
		int n = list.size();
		int m = list.get(0).length();
		char[][] chars = new char[n][m];
		for (int i = 0; i < n; i++) {
			String str = list.get(i);
			for (int j = 0; j < m; j++) {
				chars[i][j] = str.charAt(j);
			}
		}
		return chars;
	}

	public static void main(String[] args) {
		char[][] g = { //
				{ '0', '0', '0', 'X', '0', '0', '0' }, //
				{ '0', '0', 'X', 'X', '0', 'X', '0' }, //
				{ '0', 'X', '0', '0', '0', 'X', '0' } };//
		System.out.println(count(g));

		List<String> list = newArrayList("OOOXOOO", "OOXXOXO", "OXOOOXO");
		char[][] m = toMatrix(list);
		System.out.println(count(m));
	}
}
