package com.svetanis.datastructures.tree.trie;

import java.util.ArrayList;
import java.util.List;

// 2452. Words Within Two Edits of Dictionary

public final class WordsWithTwoEditsTrie {

  private TrieNode root = new TrieNode();

  public List<String> twoEditWords(String[] queries, String[] dictionary) {
    for (String word : dictionary) {
      root.insert(word);
    }
    List<String> list = new ArrayList<>();
    for (String query : queries) {
      if (dfs(query, 0, root, 0)) {
        list.add(query);
      }
    }
    return list;
  }

  private boolean dfs(String word, int index, TrieNode node, int count) {
    if (count > 2 || node == null) {
      return false;
    }
    if (index == word.length()) {
      return node.isEnd;
    }
    int i = word.charAt(index) - 'a';
    // no changes
    if (node.children[i] != null) {
      if (dfs(word, index + 1, node.children[i], count)) {
        return true;
      }
    }
    // made changes
    if (count < 2) {
      for (int c = 0; c < 26; c++) {
        if (c == i) {
          continue;
        }
        if (node.children[c] != null) {
          if (dfs(word, index + 1, node.children[c], count + 1)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public static void main(String[] args) {
    WordsWithTwoEditsTrie wwe = new WordsWithTwoEditsTrie();
    String[] q1 = { "word", "note", "ants", "wood" };
    String[] d1 = { "wood", "joke", "moat" };
    System.out.println(wwe.twoEditWords(q1, d1)); // word, note, wood

    String[] q2 = { "yes" };
    String[] d2 = { "not" };
    System.out.println(wwe.twoEditWords(q2, d2)); // []
  }

  private static class TrieNode {
    private boolean isEnd = false;
    private TrieNode[] children = new TrieNode[26];

    public void insert(String word) {
      TrieNode node = this;
      for (char c : word.toCharArray()) {
        int index = c - 'a';
        if (node.children[index] == null) {
          node.children[index] = new TrieNode();
        }
        node = node.children[index];
      }
      node.isEnd = true;
    }
  }
}
