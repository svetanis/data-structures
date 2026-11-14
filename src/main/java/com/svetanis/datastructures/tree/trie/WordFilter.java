package com.svetanis.datastructures.tree.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 745. Prefix and Suffix Search
//
// Input: the constructor gets the array words; f(prefix, suffix) returns the largest index i such
// that words[i] starts with prefix and ends with suffix, or -1 when no word does. On LeetCode:
// 1 <= words.length <= 10^4, 1 <= words[i].length <= 7, 1 <= prefix.length, suffix.length <= 7,
// all lowercase letters, at most 10^4 calls to f.
//
// The one idea: two tries, one of the words and one of the words reversed, so that a prefix is one
// walk in the first and a suffix, reversed, is one walk in the second. One node holds the indexes,
// in increasing order, of every word that passes through it, so the node reached by prefix lists
// the words that start with it and the node reached by reversed suffix lists the words that end
// with it. The answer is the largest index in both lists, found by walking the two sorted lists
// from their ends: step back in the list whose current index is larger, stop when they are equal.
//
// Time: O(N * L) to build, O(L + N) per f -- N words of length at most L = 7; the lists can hold
// every word, so the merge of the two lists reads at most N indexes
// Space: O(N * L) -- every word adds its index to each node on its path in both tries
//
// Siblings: none.

public final class WordFilter {

  private Node prefixTrie = new Node();
  private Node suffixTrie = new Node();

  public WordFilter(String[] words) {
    for (int i = 0; i < words.length; i++) {
      String word = words[i];
      prefixTrie.insert(word, i); // BUILD: the word forwards
      String reversed = new StringBuilder(word).reverse().toString();
      suffixTrie.insert(reversed, i); // BUILD: the word backwards
    }
  }

  public int f(String prefix, String suffix) {
    suffix = new StringBuilder(suffix).reverse().toString(); // REVERSE: to walk the second trie
    List<Integer> pindexes = prefixTrie.search(prefix);
    List<Integer> sindexes = suffixTrie.search(suffix);
    if (pindexes.isEmpty() || sindexes.isEmpty()) {
      return -1;
    }
    int i = pindexes.size() - 1; // START at the largest index of each list
    int j = sindexes.size() - 1;
    while (i >= 0 && j >= 0) {
      int pindex = pindexes.get(i);
      int sindex = sindexes.get(j);
      if (pindex == sindex) {
        return pindex; // MATCH: the largest index in both lists
      }
      if (pindex > sindex) {
        i--; // SKIP: this word does not end with the suffix
      } else {
        j--; // SKIP: this word does not start with the prefix
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    String[] w1 = { "apple" };
    WordFilter wf = new WordFilter(w1);
    System.out.println(wf.f("a", "e")); // 0
    System.out.println(wf.f("b", "e")); // -1

    String[] w2 = { "apple", "ape", "apple" };
    WordFilter wf2 = new WordFilter(w2);
    System.out.println(wf2.f("ap", "e")); // 2
    System.out.println(wf2.f("ap", "pe")); // 1
  }

  private static class Node {
    private Node[] children;
    private List<Integer> indexes;

    public Node() {
      this.children = new Node[26];
      this.indexes = new ArrayList<>();
    }

    public void insert(String word, int index) {
      Node node = this;
      for (char c : word.toCharArray()) {
        int i = c - 'a';
        if (node.children[i] == null) {
          node.children[i] = new Node(); // CREATE the missing letter
        }
        node = node.children[i];
        node.indexes.add(index); // RECORD: this word passes through the node
      }
    }

    public List<Integer> search(String prefix) {
      Node node = this;
      for (char c : prefix.toCharArray()) {
        int index = c - 'a';
        if (node.children[index] == null) {
          return Collections.emptyList(); // MISS: no word has this start
        }
        node = node.children[index]; // WALK one letter down
      }
      return node.indexes;
    }
  }
}
