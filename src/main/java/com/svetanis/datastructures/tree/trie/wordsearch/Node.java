package com.svetanis.datastructures.tree.trie.wordsearch;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

public final class Node {
  
  protected char key;
  protected Map<Character, Node> children;
  protected boolean leaf;

  public static Node newNode() {
    return new Node('0');
  }

  public static Node newNode(char key) {
    return new Node(key);
  }

  public Node(char letter) {
    this.key = letter;
    this.children = newHashMap();
    this.leaf = false;
  }

  public boolean isLeaf() {
    return leaf;
  }

  @Override
  public String toString() {
    return Character.toString(key);
  }
}