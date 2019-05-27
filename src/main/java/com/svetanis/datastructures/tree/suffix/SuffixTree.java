package com.svetanis.datastructures.tree.suffix;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

// Compress Trie is obtained from standard trie by joining chains of single nodes. 
// The nodes of a compressed trie can be stored by storing index ranges at the nodes.

public final class SuffixTree {

  private Node root = new Node();

  public SuffixTree(String str) {
    for (int i = 0; i < str.length(); ++i) {
      String suffix = str.substring(i);
      root.insert(suffix, i);
    }
  }

  public List<Integer> search(String s) {
    return root.search(s);
  }

  public static void main(String[] args) {
    String str = "mississippi";
    List<String> strings = newArrayList("is", "sip", "hi", "sis");
    SuffixTree root = new SuffixTree(str);
    for (String s : strings) {
      List<Integer> list = root.search(s);
      System.out.println(s + ": " + list);
    }
  }
}