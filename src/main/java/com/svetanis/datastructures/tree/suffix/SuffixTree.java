package com.svetanis.datastructures.tree.suffix;

import java.util.Arrays;
import java.util.List;

// Suffix trie: finds every position where a pattern occurs in a text. Not a LeetCode problem.
//
// Input: the text str, given to the constructor. search(s) takes a pattern and returns the
// positions of str where an occurrence of the pattern starts, in increasing order; no occurrence
// returns an empty list.
//
// The one idea: every occurrence of a pattern is the start of a suffix of the text that begins
// with the pattern. So put every suffix of the text into a trie, one character per edge, and at
// each node remember the start positions of the suffixes that pass through it (see Node). To
// find a pattern, walk its characters down from the root and read the positions kept where the
// walk ends. This is a plain trie, one node per character; a compressed suffix tree would join
// each chain of single-child nodes into one edge and store index ranges instead of characters.
//
// Time: build O(n^3) for a text of length n -- n suffixes, each pushed down one character at a
// time, and each step copies the rest of the suffix with substring. search O(m^2) for a pattern
// of length m; the list it returns is handed back, not copied.
// Space: O(n^2) -- a text of n different characters gives n(n + 1) / 2 + 1 nodes.

public final class SuffixTree {

  private Node root = new Node();

  public SuffixTree(String str) {
    for (int i = 0; i < str.length(); ++i) { // WALK: every start position
      String suffix = str.substring(i); // the suffix that starts at i
      root.insert(suffix, i); // INSERT: it, tagged with i
    }
  }

  public List<Integer> search(String s) {
    return root.search(s);
  }

  public static void main(String[] args) {
    String str = "mississippi";
    List<String> strings = Arrays.asList("is", "sip", "hi", "sis");
    SuffixTree root = new SuffixTree(str);
    for (String s : strings) {
      List<Integer> list = root.search(s);
      System.out.println(s + ": " + list); // is: [1, 4]  sip: [6]  hi: []  sis: [3]
    }
    SuffixTree spaced = new SuffixTree("a b");
    System.out.println(spaced.search(" ")); // [1]
    System.out.println(spaced.search("b ")); // []
  }
}