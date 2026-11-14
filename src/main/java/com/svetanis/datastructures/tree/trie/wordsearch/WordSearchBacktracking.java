package com.svetanis.datastructures.tree.trie.wordsearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 212. Word Search II
//
// Input: board, an m x n grid of lowercase letters, and words, an array of distinct lowercase
// words. A word is on the board if its letters can be traced through horizontally or vertically
// adjacent cells, using each cell at most once for that word. Returns every word of words that
// is on the board, in no particular order. On LeetCode: 1 <= m, n <= 12, 1 <= words.length <=
// 3 * 10^4, 1 <= words[i].length <= 10.
//
// The one idea: this is Word Search (79) run once per word. For one word, try every cell as the
// start; from a cell holding the word's next letter, mark the cell used, try its four
// neighbours for the letter after that, and unmark the cell on the way back so another path may
// use it. The word is on the board when the letters run out. Nothing is shared between words,
// which is what the trie files in this package improve.
//
// Time: O(w * m * n * 3^L) -- for each of the w words, every cell starts a search, and each step
// has at most 3 neighbours to try (the cell just left is marked used), for at most L steps.
// Space: O(L) -- the recursion is one frame per letter of the word; the result adds O(w).
//
// Siblings: WordSearchTrieSimple, WordSearchTriePruned and WordSearchTrieStringCarried answer
// the same problem with one trie of all the words, so one walk over the board serves every
// word at once.

public final class WordSearchBacktracking {

  public static List<String> search(char[][] board, String[] words) {
    Set<String> set = new HashSet<>();
    for (String word : words) { // WALK: one word at a time
      if (exist(board, word)) {
        set.add(word); // RECORD: the word is on the board
      }
    }
    return new ArrayList<>(set);
  }

  private static boolean exist(char[][] board, String word) {
    int rows = board.length;
    int cols = board[0].length;
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) { // START: every cell
        if (dfs(board, word, row, col, 0)) {
          return true;
        }
      }
    }
    return false;
  }

  private static boolean dfs(char[][] board, String word,
      int row, int col, int index) {
    if (index == word.length()) { // BASE: every letter matched
      return true;
    }
    // PRUNE: off the grid, already used, or not the letter wanted
    if (!valid(board, row, col) || board[row][col] != word.charAt(index)) {
      return false;
    }
    char c = board[row][col];
    board[row][col] = '#'; // MARK: used for this path
    boolean found = dfs(board, word, row + 1, col, index + 1)
        || dfs(board, word, row - 1, col, index + 1)
        || dfs(board, word, row, col + 1, index + 1)
        || dfs(board, word, row, col - 1, index + 1); // TRY: the next letter, four ways
    board[row][col] = c; // backtrack
    return found;
  }

  private static boolean valid(char[][] board, int row, int col) {
    int n = board.length;
    int m = board[0].length;
    boolean rows = row >= 0 && row < n;
    boolean cols = col >= 0 && col < m;
    if (rows && cols && board[row][col] != '#') { // inside the grid and not used on this path
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
    System.out.println(search(matrix, a)); // [oath, eat] -- the order of a HashSet

    char[][] matrix2 = { //
        { 'a', 'b' }, //
        { 'c', 'd' } //
    };
    String[] a2 = { "abcb" };
    System.out.println(search(matrix2, a2)); // []
  }
}
