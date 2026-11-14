package com.svetanis.datastructures.tree.trie.twoedits;

import java.util.ArrayList;
import java.util.List;

// 2452. Words Within Two Edits of Dictionary
//
// Input: two arrays of words, queries and dictionary, all of the same length. One edit changes one
// letter of a word to any other letter. Returns the queries that can be turned into some
// dictionary word with at most two edits, in their original order. On LeetCode:
// 1 <= queries.length, dictionary.length <= 100, 1 <= queries[i].length == dictionary[j].length
// <= 100, lowercase letters.
//
// The one idea: dfs(word, index, node, count) asks whether the dictionary has a word that starts
// with the trie path to node and matches word from position index on, using count edits so far.
// At each letter the query may follow its own letter for free, or any other child of the node
// for one edit; the walk gives up when count passes 2 or the path runs out, and it succeeds on a
// node that ends a word after the last letter. A trie path is one dictionary prefix, so each node
// is reached by one path and with one count per query.
//
// Time: O(26 * q * T) -- q queries, T trie nodes (at most d * l, the letters of the dictionary); one
// query reaches each trie node at most once, and a node tries up to 26 children
// Space: O(26 * T) for the trie, plus a recursion l deep
//
// Siblings: WordsWithTwoEditsBruteForce compares every query with every dictionary word instead.

public final class WordsWithTwoEditsTrie {

  private TrieNode root = new TrieNode();

  public List<String> twoEditWords(String[] queries, String[] dictionary) {
    root = new TrieNode(); // RESET: a second call must not see the first call's dictionary
    for (String word : dictionary) {
      root.insert(word);
    }
    List<String> list = new ArrayList<>();
    for (String query : queries) {
      if (dfs(query, 0, root, 0)) {
        list.add(query); // TAKE: some dictionary word is within two edits
      }
    }
    return list;
  }

  private boolean dfs(String word, int index, TrieNode node, int count) {
    if (count > 2 || node == null) {
      return false; // STOP: too many edits, or the path left the trie
    }
    if (index == word.length()) {
      return node.isEnd; // RETURN: the path must end on a whole dictionary word
    }
    int i = word.charAt(index) - 'a';
    // no changes
    if (node.children[i] != null) {
      if (dfs(word, index + 1, node.children[i], count)) { // KEEP the query's letter, no edit
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
          if (dfs(word, index + 1, node.children[c], count + 1)) { // EDIT this letter, one edit
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
    System.out.println(wwe.twoEditWords(q1, d1)); // [word, note, wood]

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
          node.children[index] = new TrieNode(); // CREATE the missing letter
        }
        node = node.children[index];
      }
      node.isEnd = true; // MARK: a whole word stops here
    }
  }
}
