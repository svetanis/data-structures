package com.svetanis.datastructures.tree.trie.wordsearch;

import java.util.ArrayList;
import java.util.List;

// 212. Word Search II

public final class WordSearchTrie {

  private static final int[] dx = { 0, 1, 0, -1 };
  private static final int[] dy = { 1, 0, -1, 0 };

  public static List<String> search(char[][] board, String[] words) {
    int rows = board.length;
    int cols = board[0].length;
    Trie trie = init(words);
    List<String> list = new ArrayList<>();
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        dfs(board, words, row, col, trie, list);
      }
    }
    return list;
  }

  private static Trie init(String[] words) {
    Trie trie = new Trie();
    for (int i = 0; i < words.length; i++) {
      trie.insert(words[i], i);
    }
    return trie;
  }

  private static void dfs(char[][] board, String[] words, int row, int col, 
      Trie node, List<String> list) {
    int index = board[row][col] - 'a';
    if (node.children[index] == null) {
      return;
    }
    node = node.children[index];
    if (node.reference != -1) {
      list.add(words[node.reference]);
      node.reference = -1;
    }
    char c = board[row][col];
    board[row][col] = '#';
    for (int k = 0; k < dx.length; k++) {
      int x = row + dx[k];
      int y = col + dy[k];
      if (valid(board, x, y)) {
        dfs(board, words, x, y, node, list);
      }
    }
    board[row][col] = c;
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

  private static class Trie {

    private int reference = -1;
    private Trie[] children = new Trie[26];

    public void insert(String word, int reference) {
      Trie node = this;
      for (int i = 0; i < word.length(); i++) {
        int index = word.charAt(i) - 'a';
        if (node.children[index] == null) {
          node.children[index] = new Trie();
        }
        node = node.children[index];
      }
      node.reference = reference;
    }
  }
}
