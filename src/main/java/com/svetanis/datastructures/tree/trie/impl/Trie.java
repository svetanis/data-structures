package com.svetanis.datastructures.tree.trie.impl;

// 208. Implement Trie (Prefix Tree)
//
// Input: calls on a set of words that starts empty. insert(word) adds a word; search(word) says
// whether exactly that word was inserted; startsWith(prefix) says whether some inserted word
// starts with prefix. On LeetCode: 1 <= word.length, prefix.length <= 2000, lowercase letters,
// at most 3 * 10^4 calls in all.
//
// The one idea: one Node is one prefix. It holds children, 26 slots where slot 0 is the node for
// that prefix plus 'a', slot 1 plus 'b', and so on, and isEndOfWord, which says that a whole
// inserted word stops at this prefix. A word is the path of Nodes spelling it, so search and
// startsWith are the same walk; they differ only in the last check, isEndOfWord or just "the
// path exists".
//
// Time: O(L) per call -- one step per letter of the word or prefix
// Space: O(total letters * 26) -- 26 child slots in every Node, used or not
//
// Siblings: TrieMap (children in a HashMap, and a delete), TrieSelfNode (no separate Node class:
// the trie is its own root), and tree/trie/Trie.java (the shared trie of the prefix files).

public final class Trie {

  private Node root;

  public Trie() {
    this.root = new Node();
  }

  public void insert(String word) {
    Node node = root;
    for (char letter : word.toCharArray()) {
      int index = letter - 'a';
      if (node.children[index] == null) {
        node.children[index] = new Node(); // CREATE the missing letter
      }
      node = node.children[index]; // WALK one letter down
    }
    node.isEndOfWord = true; // MARK: a whole word stops here
  }

  public boolean search(String word) {
    Node node = searchPrefix(word);
    return node != null && node.isEndOfWord; // RETURN: a whole word, not just a prefix
  }

  public boolean startsWith(String prefix) {
    Node node = searchPrefix(prefix);
    return node != null; // RETURN: the path exists, so some word starts with prefix
  }

  private Node searchPrefix(String s) {
    Node node = root;
    for (char letter : s.toCharArray()) {
      int index = letter - 'a';
      if (node.children[index] == null) {
        return null; // MISS: no word has this letter next
      }
      node = node.children[index]; // WALK one letter down
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

  private static class Node {
    private Node[] children;
    private boolean isEndOfWord;

    public Node() {
      this.isEndOfWord = false;
      this.children = new Node[26];
    }
  }
}