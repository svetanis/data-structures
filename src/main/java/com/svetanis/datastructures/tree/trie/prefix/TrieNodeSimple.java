package com.svetanis.datastructures.tree.trie.prefix;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

// How many inserted words start with a prefix. The same count as TrieNode,
// written as a loop instead of a recursion.

public final class TrieNodeSimple {

  protected int freq;
  protected Map<Character, TrieNodeSimple> children;

  public TrieNodeSimple() {
    this.freq = 0;
    this.children = newHashMap();
  }

  public void insert(String s) {
    TrieNodeSimple node = this;
    for (char c : s.toCharArray()) {
      node.children.putIfAbsent(c, new TrieNodeSimple());
      node = node.children.get(c);
      node.freq++;
    }
  }

  // how many inserted words start with this prefix. the empty prefix answers
  // 0, not the word total: insert counts each node after stepping down to it,
  // so the root is never counted. TrieNode counts the root, and answers the total.
  public int query(String prefix) {
    TrieNodeSimple node = this;
    for (char c : prefix.toCharArray()) {
      if (!node.children.containsKey(c)) {
        return 0;
      }
      node = node.children.get(c);
    }
    return node.freq;
  }

  public static void main(String[] args) {
    TrieNodeSimple trie = new TrieNodeSimple();
    trie.insert("cat");
    trie.insert("car");
    trie.insert("dog");
    System.out.println(trie.query("c")); // 2
    System.out.println(trie.query("ca")); // 2
    System.out.println(trie.query("cat")); // 1
    System.out.println(trie.query("d")); // 1
    System.out.println(trie.query("z")); // 0
  }
}