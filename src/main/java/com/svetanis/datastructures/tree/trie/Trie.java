package com.svetanis.datastructures.tree.trie;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.datastructures.tree.trie.Node.newNode;
import static com.svetanis.java.base.Optionals.isAbsent;
import static java.util.Arrays.asList;

import java.util.Map;

import com.google.common.base.Optional;

// A Trie, also called a Prefix Tree, is a tree structure that stores words 
// with a common prefix under the same sequence of edges in the tree 
// eliminating the need for storing the same prefix each time for each word.

// Unlike a binary search tree, no node in the tree stores the key associated with that node;  
// instead, its position in the tree defines the key with which it is associated. 
// All the descendants of a node have a common prefix of the string 
// associated with that node, and the root is associated with the empty string. 

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
