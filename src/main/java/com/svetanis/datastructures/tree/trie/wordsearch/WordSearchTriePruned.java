package com.svetanis.datastructures.tree.trie.wordsearch;

import java.util.ArrayList;
import java.util.List;

// 212. Word Search II
//
// Input: board, an m x n grid of lowercase letters, and words, an array of distinct lowercase
// words. A word is on the board if its letters can be traced through horizontally or vertically
// adjacent cells, using each cell at most once for that word. Returns every word of words that
// is on the board, in the order the walk finds them. On LeetCode: 1 <= m, n <= 12,
// 1 <= words.length <= 3 * 10^4, 1 <= words[i].length <= 10.
//
// The one idea: put all the words in one trie (one edge per letter; a node that ends a word
// holds that word) and walk the board once, stepping to a neighbour only when the trie has an
// edge for its letter. WordSearchTrieSimple plus one line on the way back up: a trie node that
// holds no word and has no children can never produce an answer again, so it is unlinked from
// its parent. Unlinking a child can leave the parent empty too, and because the check runs at
// every level of the recursion, a finished word's whole branch disappears one level at a time.
//
// Blanking a word after collecting it already stops a second collection, so the result is a
// plain List rather than a Set.
//
// The unlink pays off on boards with few distinct letters, where many paths reach finished
// branches; with many distinct letters few paths get that far and the 26-slot emptiness check
// costs slightly more than it saves.
//
// Time: O(w * L + m * n * 3^L) -- building the trie reads every letter of every word once; the
// walk starts at each cell and has at most 3 neighbours to try at each step (the cell just left
// is marked used), for at most L steps, however many words there are. Unlinking only removes
// work, and each emptiness check is 26 slots, a constant.
// Space: O(w * L) -- trie nodes, each with 26 slots; the recursion adds O(L) and the result O(w).
//
// Siblings: WordSearchTrieSimple is the same walk without the unlinking, and returns its words
// through a Set. WordSearchBacktracking runs a separate search per word, with no trie.
// WordSearchTrieStringCarried carries the spelled string and asks the shared Trie class about
// it at every step.

public final class WordSearchTriePruned {

  private static final int[] dx = { 0, 1, 0, -1 };
  private static final int[] dy = { 1, 0, -1, 0 };
  private static final char VISITED = '.';

  public static List<String> search(char[][] board, String[] words) {
    Node root = buildTrie(words); // BUILD: one trie of all the words
    List<String> result = new ArrayList<>();
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[0].length; col++) { // START: every cell
        dfs(board, row, col, root, result);
      }
    }
    return result;
  }

  // node is the trie node reached before this cell; this cell's letter must be
  // one of its children
  private static void dfs(char[][] board, int row, int col, Node node, List<String> result) {
    if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
      return; // PRUNE: off the grid
    }
    char c = board[row][col];
    if (c == VISITED) {
      return; // already on this path
    }
    Node child = node.children[c - 'a'];
    if (child == null) {
      return; // no word continues with this letter
    }
    if (child.word != null) {
      result.add(child.word); // RECORD: a word ends here
      child.word = null; // collected once, however many paths spell it
    }
    board[row][col] = VISITED; // MARK: used for this path
    for (int k = 0; k < dx.length; k++) { // TRY: the four neighbours
      dfs(board, row + dx[k], col + dy[k], child, result);
    }
    board[row][col] = c; // UNMARK: another path may use the cell
    if (child.word == null && child.hasNoChildren()) {
      node.children[c - 'a'] = null; // nothing left below: unlink it
    }
  }

  private static Node buildTrie(String[] words) {
    Node root = new Node();
    for (String word : words) {
      root.insert(word);
    }
    return root;
  }

  public static void main(String[] args) {
    char[][] matrix = { //
        { 'o', 'a', 'a', 'n' }, //
        { 'e', 't', 'a', 'e' }, //
        { 'i', 'h', 'k', 'r' }, //
        { 'i', 'f', 'l', 'v' } //
    };
    String[] a = { "oath", "pea", "eat", "rain" };
    System.out.println(search(matrix, a)); // [oath, eat]

    char[][] matrix2 = { //
        { 'a', 'b' }, //
        { 'c', 'd' } //
    };
    String[] a2 = { "abcb" };
    System.out.println(search(matrix2, a2)); // []

    char[][] matrix3 = { //
        { 'a', 'b' }, //
        { 'b', 'a' } //
    };
    String[] a3 = { "ab" };
    System.out.println(search(matrix3, a3)); // [ab] -- four paths, collected once
  }

  private static class Node {
    private String word; // non-null only where a word ends
    private final Node[] children = new Node[26];

    private void insert(String word) {
      Node node = this;
      for (int i = 0; i < word.length(); i++) {
        int index = word.charAt(i) - 'a';
        if (node.children[index] == null) {
          node.children[index] = new Node(); // MAKE: an edge for a new letter
        }
        node = node.children[index];
      }
      node.word = word; // MARK: the word ends here
    }

    private boolean hasNoChildren() {
      for (Node child : children) { // SCAN: all 26 slots
        if (child != null) {
          return false;
        }
      }
      return true;
    }
  }
}
