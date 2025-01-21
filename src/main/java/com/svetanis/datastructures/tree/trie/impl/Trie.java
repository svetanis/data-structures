package com.svetanis.datastructures.tree.trie.impl;

// 208. Implement Trie (Prefix Tree)

public final class Trie {

  private Trie[] children;
  private boolean isEndOfWord;

  public Trie() {
    this.isEndOfWord = false;
    this.children = new Trie[26];
  }

  public void insert(String word) {
    Trie node = this;
    for (char letter : word.toCharArray()) {
      int index = letter - 'a';
      if (node.children[index] == null) {
        node.children[index] = new Trie();
      }
      node = node.children[index];
    }
    node.isEndOfWord = true;
  }

  public boolean search(String word) {
    Trie node = searchPrefix(word);
    return node != null && node.isEndOfWord;
  }

  public boolean startsWith(String prefix) {
    Trie node = searchPrefix(prefix);
    return node != null;
  }

  private Trie searchPrefix(String s) {
    Trie node = this;
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
    Trie trie = new Trie();
    trie.insert("apple");
    System.out.println(trie.search("apple")); // true
    System.out.println(trie.search("app")); // false
    System.out.println(trie.startsWith("app")); // true
    trie.insert("app");
    System.out.println(trie.search("app")); // true
  }
}