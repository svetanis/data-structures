package com.svetanis.datastructures.graph.wordsearch;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

// 79. Word Search

public final class WordSearch {

	public static int wordSearch(List<String> list, String s) {
		int n = list.size();
		int m = list.get(0).length();
		char[][] board = init(list);
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < m; col++) {
				if (dfs(board, s, row, col, 0)) {
					return 1;
				}
			}
		}
		return 0;
	}

	private static boolean dfs(char[][] board, String s, int row, int col, int index) {
		int len = s.length();
		if (!valid(board, row, col) || index > len) {
			return false;
		}
		if (board[row][col] == s.charAt(index)) {
			char c = board[row][col];
			board[row][col] = '+';
			if (index == len - 1) {
				return true;
			}
			boolean up = dfs(board, s, row, col + 1, index + 1);
			boolean down = dfs(board, s, row, col - 1, index + 1);
			boolean left = dfs(board, s, row - 1, col, index + 1);
			boolean right = dfs(board, s, row + 1, col, index + 1);
			if (right || left || up || down) {
				return true;
			}
			board[row][col] = c;
		}
		return false;
	}

	private static boolean valid(char[][] board, int row, int col) {
		int n = board.length;
		int m = board[0].length;
		boolean one = row >= 0 && row < n; // row number is in range
		boolean two = col >= 0 && col < m; // col number is in range
		return one && two;
	}

	private static char[][] init(List<String> list) {
		int n = list.size();
		int m = list.get(0).length();
		char[][] grid = new char[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				grid[i][j] = list.get(i).charAt(j);
			}
		}
		return grid;
	}

	public static void main(String[] args) {
		List<String> list = newArrayList("ABCE", "SFCS", "ADEE");//
		System.out.println(wordSearch(list, "ABCCED"));// 1
		System.out.println(wordSearch(list, "SEE"));// 1
		System.out.println(wordSearch(list, "ABCB"));// 0
		System.out.println(wordSearch(list, "ABFSAB"));// 0
		System.out.println(wordSearch(list, "ABCD"));// 0
	}
}
