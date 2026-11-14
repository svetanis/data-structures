package com.svetanis.datastructures.tree.trie.impl;

// 208. Implement Trie (Prefix Tree)
//
// Input: calls on a set of words that starts empty. insert(word) adds a word; search(word) says
// whether exactly that word was inserted; startsWith(prefix) says whether some inserted word
// starts with prefix. On LeetCode: 1 <= word.length, prefix.length <= 2000, lowercase letters,
// at most 3 * 10^4 calls in all.
//
// The one idea: the trie is its own root node, so `this` is where every walk starts. One
// TrieSelfNode is one prefix. It holds children, 26 slots where slot 0 is the node for that
// prefix plus 'a', slot 1 plus 'b', and so on, and isEndOfWord, which says that a whole inserted
// word stops at this prefix. search and startsWith are the same walk; they differ only in the last
// check.
//
// Time: O(L) per call -- one step per letter of the word or prefix
// Space: O(total letters * 26) -- 26 child slots in every node, used or not
//
// Siblings: Trie has the same algorithm with a separate Node class; TrieMap keeps the children
// in a HashMap and adds delete.

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
        node.children[index] = new TrieSelfNode(); // CREATE the missing letter
      }
      node = node.children[index]; // WALK one letter down
    }
    node.isEndOfWord = true; // MARK: a whole word stops here
  }

  public boolean search(String word) {
    TrieSelfNode node = searchPrefix(word);
    return node != null && node.isEndOfWord; // RETURN: a whole word, not just a prefix
  }

  public boolean startsWith(String prefix) {
    TrieSelfNode node = searchPrefix(prefix);
    return node != null; // RETURN: the path exists, so some word starts with prefix
  }

  private TrieSelfNode searchPrefix(String s) {
    TrieSelfNode node = this;
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
    TrieSelfNode trie = new TrieSelfNode();
    trie.insert("apple");
    System.out.println(trie.search("apple")); // true
    System.out.println(trie.search("app")); // false
    System.out.println(trie.startsWith("app")); // true
    trie.insert("app");
    System.out.println(trie.search("app")); // true
  }
}
