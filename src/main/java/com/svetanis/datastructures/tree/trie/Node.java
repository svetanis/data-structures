package com.svetanis.datastructures.tree.trie;

import java.util.HashMap;
import java.util.Map;

// The node of the shared trie (Trie.java). Not a LeetCode problem.
//
// The one idea: one Node is one letter of a word. key is the letter on the edge into this node,
// children maps the next letter to the next Node, and endOfWord says that a whole word stops here.
// A word is a path of Nodes from the root, so words that share a start share the Nodes of that
// start.
//
// Time: O(1) to create a node and to read a field; Space: O(1) per node plus its children map
//
// Siblings: shortestuniqueprefix/Node.java and the nested Node classes of MapSum, WordFilter and
// impl/Trie.java are tries of their own; this one is the one Trie.java and the prefix files read.
//
// Fields are public so every trie package can walk the node directly,
// the way LeetCode's ListNode and TreeNode are read.

public final class Node {

  public char key;
  public Map<Character, Node> children;
  // true where a word ENDS, not where the node has no children.
  // Nodes.isLeaf(node) in tree/binary/ means the no-children thing.
  public boolean endOfWord;

  public static Node newNode() {
    return new Node('0'); // the root: its letter is never read
  }

  public static Node newNode(char key) {
    return new Node(key);
  }

  public Node(char letter) {
    this.key = letter;
    this.children = new HashMap<>();
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