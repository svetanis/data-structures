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
// The one idea: put all the words in one trie, a tree with one edge per letter in which a path
// from the root spells a prefix of some word and a node that ends a word holds that word. Then
// walk the board once instead of once per word: from each cell, step to a neighbour only if the
// trie has an edge for that neighbour's letter. The trie node reached says which prefix the path
// has spelled so far, so a path that matches no word's prefix is cut at once, and a node that
// holds a word reports it. The cell is marked used while its neighbours are tried and restored
// on the way back.
//
// This is the baseline, and the one to write first. It never unlinks a trie node whose words
// have all been found, so later grid paths re-descend dead branches. WordSearchTriePruned adds
// that, and returns a List since blanking the word already stops duplicates -- it is the answer
// to "can you make it faster?".
//
// Time: O(w * L + m * n * 3^L) -- building the trie reads every letter of every word once; the
// walk starts at each cell and has at most 3 neighbours to try at each step (the cell just left
// is marked used), for at most L steps, however many words there are.
// Space: O(w * L) -- trie nodes, each with 26 slots; the recursion adds O(L) and the result O(w).
//
// Siblings: WordSearchBacktracking runs a separate search per word, with no trie.
// WordSearchTriePruned is this walk plus the unlinking. WordSearchTrieStringCarried carries the
// spelled string and asks the shared Trie class about it at every step.

public final class WordSearchTrieSimple {

  private static final int[] dx = { 0, 1, 0, -1 };
  private static final int[] dy = { 1, 0, -1, 0 };

  public static List<String> search(char[][] board, String[] words) {
    int rows = board.length;
    int cols = board[0].length;
    Trie trie = init(words); // BUILD: one trie of all the words
    Set<String> set = new HashSet<>();
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) { // START: every cell
        dfs(board, row, col, trie, set);
      }
    }
    return new ArrayList<>(set);
  }

  private static Trie init(String[] words) {
    Trie trie = new Trie();
    for (String word : words) {
      trie.insert(word);
    }
    return trie;
  }

  private static void dfs(char[][] board, int row, int col,
      Trie node, Set<String> set) {
    int index = board[row][col] - 'a';
    if (node.children[index] == null) { // PRUNE: no word continues with this letter
      return;
    }
    node = node.children[index]; // STEP: down the trie by this cell's letter
    if (node.word != null) {
      set.add(node.word); // RECORD: a word ends here
      node.word = null;
    }
    char c = board[row][col];
    board[row][col] = '#'; // MARK: used for this path
    for (int k = 0; k < dx.length; k++) { // TRY: the four neighbours
      int x = row + dx[k];
      int y = col + dy[k];
      if (valid(board, x, y)) {
        dfs(board, x, y, node, set);
      }
    }
    board[row][col] = c; // UNMARK: another path may use the cell
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

  private static class Trie {
    private String word; // non-null only where a word ends
    private Trie[] children = new Trie[26];

    public void insert(String word) {
      Trie node = this;
      for (int i = 0; i < word.length(); i++) {
        int index = word.charAt(i) - 'a';
        if (node.children[index] == null) {
          node.children[index] = new Trie(); // MAKE: an edge for a new letter
        }
        node = node.children[index];
      }
      node.word = word; // MARK: the word ends here
    }
  }
}
