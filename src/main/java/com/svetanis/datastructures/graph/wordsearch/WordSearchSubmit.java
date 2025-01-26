package com.svetanis.datastructures.graph.wordsearch;

// 79. Word Search

public final class WordSearchSubmit {

	private static final int[] dx = { 0, 1, 0, -1 };
	private static final int[] dy = { 1, 0, -1, 0 };

	public static boolean wordSearch(char[][] board, String s) {
		int rows = board.length;
		int cols = board[0].length;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (dfs(board, s, row, col, 0)) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean dfs(char[][] board, String s, int row, int col, int index) {
		int len = s.length();
		if (index == len - 1) {
			return board[row][col] == s.charAt(index);
		}
		if (board[row][col] != s.charAt(index)) {
			return false;
		}
		char c = board[row][col];
		board[row][col] = '0';
		for (int k = 0; k < dx.length; k++) {
			int x = row + dx[k];
			int y = col + dy[k];
			if (valid(board, x, y) && board[x][y] != '0') {
				if (dfs(board, s, x, y, index + 1)) {
					return true;
				}
			}
		}
		board[row][col] = c;
		return false;
	}

	private static boolean valid(char[][] board, int row, int col) {
		int n = board.length;
		int m = board[0].length;
		boolean one = row >= 0 && row < n; // row number is in range
		boolean two = col >= 0 && col < m; // col number is in range
		return one && two;
	}

	public static void main(String[] args) {
		char[][] board = { { 'A', 'B', 'C', 'E' }, { 'S', 'F', 'C', 'S' }, { 'A', 'D', 'E', 'E' } };
		System.out.println(wordSearch(board, "ABCCED"));// true

		char[][] board2 = { { 'A', 'B', 'C', 'E' }, { 'S', 'F', 'C', 'S' }, { 'A', 'D', 'E', 'E' } };
		System.out.println(wordSearch(board2, "SEE"));// true

		char[][] board3 = { { 'A', 'B', 'C', 'E' }, { 'S', 'F', 'C', 'S' }, { 'A', 'D', 'E', 'E' } };
		System.out.println(wordSearch(board3, "ABCB"));// false

		char[][] board4 = { { 'A', 'B', 'C', 'E' }, { 'S', 'F', 'C', 'S' }, { 'A', 'D', 'E', 'E' } };
		System.out.println(wordSearch(board4, "ABFSAB"));// false

		char[][] board5 = { { 'A', 'B', 'C', 'E' }, { 'S', 'F', 'C', 'S' }, { 'A', 'D', 'E', 'E' } };
		System.out.println(wordSearch(board5, "ABCD"));// false
	}
}
