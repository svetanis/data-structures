package com.svetanis.datastructures.tree.trie;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

// Fields are public so every trie package can walk the node directly,
// the way LeetCode's ListNode and TreeNode are read.

public final class Node {
  
  public char key;
  public Map<Character, Node> children;
  // true where a word ENDS, not where the node has no children.
  // Nodes.isLeaf(node) in tree/binary/ means the no-children thing.
  public boolean endOfWord;

  public static Node newNode() {
    return new Node('0');
  }

  public static Node newNode(char key) {
    return new Node(key);
  }

  public Node(char letter) {
    this.key = letter;
    this.children = newHashMap();
    this.endOfWord = false;
  }

  public boolean isEndOfWord() {
    return endOfWord;
  }

  @Override
  public String toString() {
    return Character.toString(key);
  }
}