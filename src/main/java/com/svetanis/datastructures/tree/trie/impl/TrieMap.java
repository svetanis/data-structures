package com.svetanis.datastructures.tree.trie.impl;

import java.util.HashMap;
import java.util.Map;

// 208. Implement Trie (Prefix Tree)
//
// Input: calls on a set of words that starts empty. insert(word) adds a word; search(word) says
// whether exactly that word was inserted; startsWith(prefix) says whether some inserted word
// starts with prefix; delete(word), which LeetCode does not have, removes a word. On LeetCode:
// 1 <= word.length, prefix.length <= 2000, lowercase letters, at most 3 * 10^4 calls in all.
//
// The one idea: one Node is one prefix. It holds children, a map from the next letter to the Node
// for the prefix plus that letter, and isEndOfWord, which says that a whole inserted word stops
// at this prefix. delete clears isEndOfWord at the word's last Node and then, walking back up,
// removes every Node that has no children left and ends no word, so the trie never keeps a branch
// that spells nothing: a word that is the start of a longer one keeps its Nodes, and a word that
// was never inserted changes nothing.
//
// Time: O(L) per call -- one step per letter of the word or prefix
// Space: O(total letters) -- a Node per distinct prefix, with children in a map, so only used
// letters take space
//
// Siblings: Trie (26 slots in an array, no delete), TrieSelfNode (no separate Node class: the
// trie is its own root), and tree/trie/Trie.java (the shared trie of the prefix files).
//
// Note: delete recurses once per letter of the word; LeetCode's 2000 letters are far from
// overflowing the default stack.

public final class TrieMap {

  private Node root;

  public TrieMap() {
    this.root = new Node();
  }

  public void insert(String word) {
    Node node = root;
    for (char letter : word.toCharArray()) {
      node.children.putIfAbsent(letter, new Node()); // CREATE the missing letter
      node = node.children.get(letter); // WALK one letter down
    }
    node.isEndOfWord = true; // MARK: a whole word stops here
  }

  public boolean search(String word) {
    Node node = searchPrefix(word);
    return node != null && node.isEndOfWord; // RETURN: a whole word, not just a prefix
  }

  public boolean startsWith(String prefix) {
    return searchPrefix(prefix) != null; // RETURN: the path exists
  }

  private Node searchPrefix(String s) {
    Node node = root;
    for (char letter : s.toCharArray()) {
      node = node.children.get(letter); // WALK one letter down
      if (node == null) {
        return null; // MISS: no word has this letter next
      }
    }
    return node;
  }

  public void delete(String word) {
    dfs(root, word, 0);
  }

  // returns true when this node can be removed: it ends no word and has no children
  private boolean dfs(Node node, String word, int index) {
    if (index == word.length()) {
      node.isEndOfWord = false; // UNMARK: the word no longer stops here
      return node.children.isEmpty();
    }

    char c = word.charAt(index);
    Node child = node.children.get(c);
    if (child == null) {
      return false; // MISS: the word was never inserted, change nothing
    }
    boolean shouldDeleteChild = dfs(child, word, index + 1);
    if (shouldDeleteChild) {
      node.children.remove(c); // PRUNE: the child spells nothing any more
    }
    return !node.isEndOfWord && node.children.isEmpty();
  }

  public static void main(String[] args) {
    searchDemo();
    deleteDemo();
  }

  private static void searchDemo() {
    TrieMap trie = new TrieMap();
    trie.insert("apple");
    System.out.println(trie.search("apple")); // true
    System.out.println(trie.search("app")); // false
    System.out.println(trie.startsWith("app")); // true
    trie.insert("app");
    System.out.println(trie.search("app")); // true
  }

  private static void deleteDemo() {
    TrieMap trie = new TrieMap();
    trie.insert("apple");
    trie.insert("app");
    trie.insert("banana");

    // "app" ends inside "apple", so the prune must stop at the shared prefix
    trie.delete("app");
    System.out.println(trie.search("app")); // false
    System.out.println(trie.search("apple")); // true
    System.out.println(trie.startsWith("app")); // true

    // nothing shares banana's nodes, so every one of them goes
    trie.delete("banana");
    System.out.println(trie.startsWith("ban")); // false

    // a word that was never inserted must leave the trie untouched
    trie.delete("apples");
    System.out.println(trie.search("apple")); // true
  }

  private static class Node {
    private Map<Character, Node> children;
    private boolean isEndOfWord;

    public Node() {
      this.isEndOfWord = false;
      this.children = new HashMap<>();
    }
  }
}