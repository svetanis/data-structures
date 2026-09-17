package com.svetanis.datastructures.tree.trie.wordsearch;

import java.util.ArrayList;
import java.util.List;

// 212. Word Search II

// WordSearchTrieSimple plus one line on the way back up: a trie node that
// holds no word and has no children can never produce an answer again, so it
// is unlinked from its parent. Unlinking a child can leave the parent empty
// too, and because the check runs at every level of the recursion, a finished
// word's whole branch disappears one level at a time.

// Blanking a word after collecting it already stops a second collection, so the
// result is a plain List rather than a Set.

// The unlink pays off on boards with few distinct letters, where many paths
// reach finished branches; with many distinct letters few paths get that far
// and the 26-slot emptiness check costs slightly more than it saves.

public final class WordSearchTriePruned {

  private static final int[] dx = { 0, 1, 0, -1 };
  private static final int[] dy = { 1, 0, -1, 0 };
  private static final char VISITED = '.';

  public static List<String> search(char[][] board, String[] words) {
    Node root = buildTrie(words);
    List<String> result = new ArrayList<>();
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[0].length; col++) {
        dfs(board, row, col, root, result);
      }
    }
    return result;
  }

  // node is the trie node reached before this cell; this cell's letter must be
  // one of its children
  private static void dfs(char[][] board, int row, int col, Node node, List<String> result) {
    if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
      return;
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
      result.add(child.word);
      child.word = null; // collected once, however many paths spell it
    }
    board[row][col] = VISITED;
    for (int k = 0; k < dx.length; k++) {
      dfs(board, row + dx[k], col + dy[k], child, result);
    }
    board[row][col] = c;
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
          node.children[index] = new Node();
        }
        node = node.children[index];
      }
      node.word = word;
    }

    private boolean hasNoChildren() {
      for (Node child : children) {
        if (child != null) {
          return false;
        }
      }
      return true;
    }
  }
}
