package com.svetanis.datastructures.tree.trie.wordsearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 212. Word Search II

public final class WordSearchBacktracking {

	public static List<String> search(char[][] board, String[] words) {
		Set<String> set = new HashSet<>();
		for (String word : words) {
			if (exist(board, word)) {
				set.add(word);
			}
		}
		return new ArrayList<>(set);
	}

	private static boolean exist(char[][] board, String word) {
		int rows = board.length;
		int cols = board[0].length;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (dfs(board, word, row, col, 0)) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean dfs(char[][] board, String word, int row, int col, int index) {
		if (index == word.length()) {
			return true;
		}
		if (!valid(board, row, col) || board[row][col] != word.charAt(index)) {
			return false;
		}
		char c = board[row][col];
		board[row][col] = '#';
		boolean found = dfs(board, word, row + 1, col, index + 1) 
				|| dfs(board, word, row - 1, col, index + 1)
				|| dfs(board, word, row, col + 1, index + 1) 
				|| dfs(board, word, row, col - 1, index + 1);
		board[row][col] = c; // backtrack
		return found;
	}

	private static boolean valid(char[][] board, int row, int col) {
		int n = board.length;
		int m = board[0].length;
		boolean rows = row >= 0 && row < n;
		boolean cols = col >= 0 && col < m;
		if (rows && cols && board[row][col] != '#') {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		char[][] matrix = { //
				{ 'o', 'a', 'a', 'n' }, //
				{ 'e', 't', 'a', 'e' }, //
				{ 'i', 'h', 'k', 'r' }, //
				{ 'i', 'f', 'l', 'v' } //
		};
		String[] a = { "oath", "pea", "eat", "rain" };
		System.out.println(search(matrix, a)); // [eat, oath]

		char[][] matrix2 = { //
				{ 'a', 'b' }, //
				{ 'c', 'd' } //
		};
		String[] a2 = { "abcb" };
		System.out.println(search(matrix2, a2));
	}
}
