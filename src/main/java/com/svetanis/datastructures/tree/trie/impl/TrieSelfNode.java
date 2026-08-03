package com.svetanis.datastructures.tree.trie.impl;

// 208. Implement Trie (Prefix Tree)

// same algorithm as Trie.java, with the Node class removed:
// the trie is its own root node, so `this` is where every walk starts

public final class TrieSelfNode {

  private TrieSelfNode[] children;
  private boolean isEndOfWord;

  public TrieSelfNode() {
    this.isEndOfWord = false;
    this.children = new TrieSelfNode[26];
  }

  public void insert(String word) {
    TrieSelfNode node = this;
    for (char letter : word.toCharArray()) {
      int index = letter - 'a';
      if (node.children[index] == null) {
        node.children[index] = new TrieSelfNode();
      }
      node = node.children[index];
    }
    node.isEndOfWord = true;
  }

  public boolean search(String word) {
    TrieSelfNode node = searchPrefix(word);
    return node != null && node.isEndOfWord;
  }

  public boolean startsWith(String prefix) {
    TrieSelfNode node = searchPrefix(prefix);
    return node != null;
  }

  private TrieSelfNode searchPrefix(String s) {
    TrieSelfNode node = this;
    for (char letter : s.toCharArray()) {
      int index = letter - 'a';
      if (node.children[index] == null) {
        return null;
      }
      node = node.children[index];
    }
    return node;
  }

  public static void main(String[] args) {
    TrieSelfNode trie = new TrieSelfNode();
    trie.insert("apple");
    System.out.println(trie.search("apple")); // true
    System.out.println(trie.search("app")); // false
    System.out.println(trie.startsWith("app")); // true
    trie.insert("app");
    System.out.println(trie.search("app")); // true
  }
}
