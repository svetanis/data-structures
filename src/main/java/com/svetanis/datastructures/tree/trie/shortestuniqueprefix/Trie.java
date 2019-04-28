package com.svetanis.datastructures.tree.trie.shortestuniqueprefix;

import static com.svetanis.datastructures.tree.trie.shortestuniqueprefix.Node.newNode;
import static java.util.Arrays.asList;

import java.util.Map;

public class Trie {

  private Node root;

  public Trie(String str) {
    this(asList(str));
  }

  public Trie(Iterable<String> list) {
    this.root = newNode();
    for (String str : list) {
      add(str);
    }
  }

  public static Trie build(String str) {
    return new Trie(str);
  }

  public static Trie build(Iterable<String> list) {
    return new Trie(list);
  }

  private void add(String str) {
    int len = str.length();
    Node node = root;
    for (int i = 0; i < len; ++i) {
      char c = str.charAt(i);
      Map<Character, Node> map = node.children;
      if (map.get(c) != null) {
        Node child = map.get(c);
        child.freq++;
        map.put(c, child);
      } else {
        map.put(c, newNode(c));
      }
      node = map.get(c);
    }
    node.leaf = true;
  }

  public Node getRoot() {
    return root;
  }

}
