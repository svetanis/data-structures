package com.svetanis.datastructures.tree.trie.prefix;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Prefix matching, with an explicit stack.
// Given a list of words and a prefix, list every word that begins with the prefix.
//
// Input: an array of words and one prefix. Returns the distinct words that begin with the
// prefix, in trie order (the order a HashMap lists the children, not alphabetical); the
// empty prefix lists every word; no match gives an empty list.
//
// The one idea: the same as PrefixMatching -- every word that begins with the prefix sits
// below the node where the prefix ends, so walk the prefix down and collect every flagged
// node of that subtree, each with its word. Here the subtree is visited with a stack of
// (node, word so far) pairs instead of recursion, so the depth of a word no longer matters.
// The children of a node are pushed in reverse, so they come off the stack in the same order
// the recursive version visits them and both files return identical lists.
//
// Time: O(S + p + k * L) -- S = total letters of the words (build), p = prefix length,
// k = nodes below the prefix node, L = longest word (each pair copies the path string)
// Space: O(S) -- the trie, plus the stack
//
// Siblings: PrefixMatching is the recursive version; a word of roughly 5,000 letters overflows
// its stack, which this file does not.

public final class PrefixMatchingIterative {

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
    return collect(node, word);
  }

  private List<String> collect(TrieNode start, String word) {
    List<String> list = new ArrayList<>();
    Deque<TrieNode> nodes = new ArrayDeque<>(); // the nodes still to visit
    Deque<String> words = new ArrayDeque<>(); // the path to each, kept in step with nodes
    nodes.push(start);
    words.push(word);
    while (!nodes.isEmpty()) {
      TrieNode node = nodes.pop(); // VISIT the next node
      String path = words.pop();
      if (node.isEndOfWord) {
        list.add(path); // RECORD: the path to here is a whole word
      }
      pushChildren(node, path, nodes, words);
    }
    return list;
  }

  private void pushChildren(TrieNode node, String path, Deque<TrieNode> nodes, Deque<String> words) {
    List<Character> letters = new ArrayList<>(node.children.keySet());
    for (int i = letters.size() - 1; i >= 0; i--) { // REVERSE: the first child must come off first
      char c = letters.get(i);
      nodes.push(node.children.get(c));
      words.push(path + c); // DESCEND, adding the child's letter to the path
    }
  }

  public List<String> trie(String[] words, String prefix) {
    createTrie(words);
    return this.prefix(prefix);
  }

  public static void main(String[] args) {
    PrefixMatchingIterative pm = new PrefixMatchingIterative();
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
