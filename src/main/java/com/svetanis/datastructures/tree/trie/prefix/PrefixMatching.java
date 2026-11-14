package com.svetanis.datastructures.tree.trie.prefix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Prefix matching.
// Given a list of words and a prefix, list every word that begins with the prefix.
//
// Input: an array of words and one prefix. Returns the distinct words that begin with the
// prefix, in trie order (the order a HashMap lists the children, not alphabetical); the
// empty prefix lists every word; no match gives an empty list.
//
// The one idea: put every word in a trie; a node holds one letter, and a node flagged
// end-of-word is a word spelled by the path above it. Every word that begins with the prefix
// sits below the node where the prefix ends, so walk the prefix down from the root (a missing
// child means no word begins with it), then collect every flagged node in that subtree,
// carrying the letters of the path along so each hit already knows its word.
//
// Time: O(S + p + k * L) -- S = total letters of the words (build), p = prefix length,
// k = nodes below the prefix node, L = longest word (each step copies the path string)
// Space: O(S) -- the trie, plus the recursion stack, one frame per letter
//
// Known limit: the collection step recurses one frame per letter, so a word of roughly 5,000
// letters or more overflows the default stack. It only happens on very large input.
// PrefixMatchingIterative is the same algorithm with an explicit stack.
//
// Siblings: PrefixMatchingIterative (same algorithm, no recursion). PrefixCount counts the
// words under a prefix instead of listing them.

public final class PrefixMatching {

  private TrieNode root;

  public void createTrie(String[] words) {
    this.root = new TrieNode(); // RESET: each call builds from an empty trie
    for (String word : words) {
      insert(word);
    }
  }

  public void insert(String word) {
    TrieNode node = root;
    for (char c : word.toCharArray()) {
      if (!node.children.containsKey(c)) {
        node.children.put(c, new TrieNode()); // GROW: first word to use this letter here
      }
      node = node.children.get(c); // STEP down
    }
    node.isEndOfWord = true; // FLAG: a word ends at the last node only
  }

  public List<String> prefix(String word) {
    TrieNode node = root;
    for (char c : word.toCharArray()) { // WALK the prefix down from the root
      if (!node.children.containsKey(c)) {
        return new ArrayList<>(); // MISS: no word begins with this prefix
      }
      node = node.children.get(c);
    }
    List<String> list = new ArrayList<>();
    dfs(node, word, list); // COLLECT every word in the subtree below the prefix
    return list;
  }

  private void dfs(TrieNode node, String word, List<String> list) {
    if (node.isEndOfWord) {
      list.add(word); // RECORD: the path to here is a whole word
    }
    for (char c : node.children.keySet()) {
      TrieNode child = node.children.get(c);
      dfs(child, word + c, list); // DESCEND, adding the child's letter to the path
    }
  }

  public List<String> trie(String[] words, String prefix) {
    createTrie(words);
    return this.prefix(prefix);
  }

  public static void main(String[] args) {
    PrefixMatching pm = new PrefixMatching();
    String[] words1 = { "apple", "app", "apartment", "ap", "apricot" };
    System.out.println(pm.trie(words1, "app")); // [app, apple]
    System.out.println(pm.trie(words1, "ap")); // [ap, app, apple, apartment, apricot]
    System.out.println(pm.trie(words1, "z")); // []
  }

  private static class TrieNode {
    private boolean isEndOfWord;
    private Map<Character, TrieNode> children;

    public TrieNode() {
      this.children = new HashMap<>();
    }
  }
}
