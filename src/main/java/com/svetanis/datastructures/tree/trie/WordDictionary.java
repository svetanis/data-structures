package com.svetanis.datastructures.tree.trie;

// 211. Design Add and Search Words Data Structure

public final class WordDictionary {

  private TrieNode root;

  public WordDictionary() {
    this.root = new TrieNode();
  }

  public void addWord(String word) {
    TrieNode node = root;
    for (char letter : word.toCharArray()) {
      int index = letter - 'a';
      if (node.children[index] == null) {
        node.children[index] = new TrieNode();
      }
      node = node.children[index];
    }
    node.isEndWord = true;
  }

  public boolean search(String word) {
    return search(word, root);
  }

  private boolean search(String word, TrieNode node) {
    for (int i = 0; i < word.length(); i++) {
      char c = word.charAt(i);
      if (c == '.') {
        for (TrieNode child : node.children) {
          String substr = word.substring(i + 1);
          if (child != null && search(substr, child)) {
            return true;
          }
        }
        return false;
      } else {
        int index = c - 'a';
        if (node.children[index] == null) {
          return false;
        }
        node = node.children[index];
      }
    }
    return node.isEndWord;
  }

  public static void main(String[] args) {
    WordDictionary wd = new WordDictionary();
    wd.addWord("bad");
    wd.addWord("dad");
    wd.addWord("mad");
    System.out.println(wd.search("pad")); // false
    System.out.println(wd.search("bad")); // true
    System.out.println(wd.search(".ad")); // true
    System.out.println(wd.search("b..")); // true
  }

  private static class TrieNode {
    private TrieNode[] children = new TrieNode[26];
    private boolean isEndWord;
  }
}