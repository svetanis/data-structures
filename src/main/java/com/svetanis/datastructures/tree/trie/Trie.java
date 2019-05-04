package com.svetanis.datastructures.tree.trie;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.datastructures.tree.trie.Node.newNode;
import static com.svetanis.java.base.Optionals.isAbsent;
import static java.util.Arrays.asList;

import java.util.Map;

import com.google.common.base.Optional;

public class Trie {

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
        map.put(c, newNode(c));
      }
      node = map.get(c);
    }
    node.leaf = true;
  }

  public boolean search(String word) {
    Optional<Node> node = find(word);
    if (isAbsent(node)) {
      return false;
    }
    return node.get().leaf;
  }

  public Optional<Node> find(String str) {
    Node node = root;
    for (char c : str.toCharArray()) {
      Map<Character, Node> map = node.children;
      if (map.containsKey(c)) {
        node = map.get(c);
      } else {
        return absent();
      }
    }
    return of(node);
  }


  public boolean startsWith(String prefix) {
    Optional<Node> node = find(prefix);
    return node.isPresent();
  }


  public Node getRoot() {
    return root;
  }

}
