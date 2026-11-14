package com.svetanis.datastructures.tree.trie.prefix;

// 720. Longest Word in Dictionary
//
// Input: an array of words. On LeetCode: 1 <= words.length <= 1000,
// 1 <= words[i].length <= 30, lowercase letters only.
// Returns the longest word that can be built one letter at a time by other words in the
// array -- every prefix of it is also a word in the array. Among equally long answers,
// the one that is smallest in dictionary order; "" when no word qualifies.
//
// The one idea: put every word in a trie. A node holds one letter and a flag that says
// "a word ends here". A word can be built one letter at a time exactly when every node on
// its path is flagged, so the search walks down from the root and enters a child only when
// that child is flagged: the first unflagged node cuts off everything below it. Of the
// flagged paths, keep the longest; the children are visited a to z, and a tie keeps the
// smaller word because only a strictly smaller word of the same length replaces it.
//
// Time: O(N * L * (26 + L)) -- N words of at most L letters: the trie has at most N * L nodes,
// each visited once, scanning 26 slots and extending the path string by one letter
// Space: O(N * L) -- the trie
//
// Siblings: none in this package.

public final class LongestWord {

  private Node root;

  public LongestWord() {
    this.root = new Node();
  }

  public String longestWord(String[] words) {
    this.root = new Node(); // RESET: a second call on this object must not see the first call's words
    for (String word : words) {
      insert(word); // BUILD: every word into the trie
    }
    return dfs(root, "");
  }

  private String dfs(Node node, String path) {
    String s = path; // BEST: the longest buildable word found under this node
    for (Node child : node.children) {
      if (child != null && child.endOfWord) { // ENTER only a flagged child: its word is buildable
        String word = dfs(child, path + child.c);
        boolean one = word.length() > s.length(); // LONGER wins
        boolean two = word.length() == s.length();
        boolean cmp = word.compareTo(s) < 0; // TIE: the smaller word wins
        if (one || (two && cmp)) {
          s = word;
        }
      }
    }
    return s; // RETURN: path itself when no flagged child goes deeper
  }

  private void insert(String word) {
    Node node = root;
    for (char c : word.toCharArray()) {
      int index = c - 'a';
      if (node.children[index] == null) {
        node.children[index] = new Node();
        node.children[index].c = c;
      }
      node = node.children[index]; // STEP down
    }
    node.endOfWord = true; // FLAG: a word ends at the last node only
  }

  public static void main(String[] args) {
    String[] w1 = { "w", "wo", "wor", "worl", "world" };
    LongestWord lw = new LongestWord();
    System.out.println(lw.longestWord(w1)); // world

    String[] w2 = { "a", "banana", "app", "appl", "ap", "apply", "apple" };
    LongestWord lw2 = new LongestWord();
    System.out.println(lw2.longestWord(w2)); // apple
  }

  private static class Node {
    private char c;
    private boolean endOfWord;
    private Node[] children;

    public Node() {
      this.children = new Node[26];
    }
  }
}
