package com.svetanis.datastructures.tree.trie;

import static com.svetanis.datastructures.tree.trie.Node.newNode;
import static java.util.Arrays.asList;

import java.util.Map;
import java.util.Optional;

// A Trie, also called a Prefix Tree, is a tree structure that stores words
// with a common prefix under the same sequence of edges in the tree
// eliminating the need for storing the same prefix each time for each word.

// Unlike a binary search tree, no node in the tree stores the key associated with that node;
// instead, its position in the tree defines the key with which it is associated.
// All the descendants of a node have a common prefix of the string
// associated with that node, and the root is associated with the empty string.
//
// Not a LeetCode problem: this is the shared trie of the prefix, word-search and review files.
// The one idea: one Node (Node.java) is one letter; a word is the path of Nodes spelling it, and
// the Node where it stops has endOfWord set, so "is this a whole word" and "does some word start
// with this" are the same walk that differs only in the last check.
//
// Time: O(L) per add, search, find and startsWith, where L is the length of the word -- one step
// per letter; building from n words is O(total letters)
// Space: O(total letters) -- one Node per distinct letter position, with a HashMap of children
//
// Siblings: impl/Trie.java (LeetCode 208, fixed 26-slot arrays), impl/TrieMap.java (a map of
// children, plus delete); this one keeps its children in a map and exposes the root.

public final class Trie {

  private Node root;

  public static Trie build(String str) {
    return new Trie(str);
  }

  public static Trie build(Iterable<String> list) {
    return new Trie(list);
  }

  public Trie(String str) {
    this(asList(str));
  }

  public Trie(Iterable<String> list) {
    this.root = newNode();
    for (String str : list) {
      add(str);
    }
  }

  private void add(String str) {
    Node node = root;
    for (char c : str.toCharArray()) {
      Map<Character, Node> map = node.children;
      if (map.get(c) == null) {
        map.put(c, newNode(c)); // CREATE the missing letter
      }
      node = map.get(c); // WALK one letter down
    }
    node.endOfWord = true; // MARK: a whole word stops here
  }

  public boolean search(String word) {
    Optional<Node> node = find(word);
    if (!node.isPresent()) {
      return false; // MISS: the path does not exist
    }
    return node.get().endOfWord; // RETURN: a whole word, not just a prefix
  }

  public Optional<Node> find(String str) {
    Node node = root;
    for (char c : str.toCharArray()) {
      Map<Character, Node> map = node.children;
      if (map.containsKey(c)) {
        node = map.get(c); // WALK one letter down
      } else {
        return Optional.empty(); // MISS: no word has this letter next
      }
    }
    return Optional.of(node);
  }

  public boolean startsWith(String prefix) {
    Optional<Node> node = find(prefix);
    return node.isPresent(); // RETURN: the path exists, so some word starts with prefix
  }

  public Node getRoot() {
    return root;
  }

}
