package com.svetanis.datastructures.tree.trie.shortestuniqueprefix;

import java.util.HashMap;
import java.util.Map;

// One node of the trie in this package.
//
// A node is a prefix: the letters on the path from the root. key is the letter on the edge into
// the node, freq is how many inserted words pass through it (a node with freq 1 is a prefix that
// only one word has, which is what the shortest unique prefix looks for), and children maps each
// next letter to its node. The root is built by newNode() with freq 0; every other node starts at
// freq 1 and Trie raises it once for each further word that passes through.
//
// Siblings: tree.trie.Node is the plain version with no freq, used by the other tries.

public final class Node {
  protected char key;
  protected int freq;
  protected Map<Character, Node> children;
  // true where a word ENDS, not where the node has no children.
  // Nodes.isLeaf(node) in tree/binary/ means the no-children thing.
  protected boolean endOfWord;

  public static Node newNode() {
    return new Node('0', 0);
  }

  public static Node newNode(char key) {
    return new Node(key, 1);
  }

  public static Node newNode(char key, int freq) {
    return new Node(key, freq);
  }

  public Node(char letter, int freq) {
    this.key = letter;
    this.freq = freq;
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