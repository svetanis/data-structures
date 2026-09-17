package com.svetanis.datastructures.tree.trie.shortestuniqueprefix;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

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