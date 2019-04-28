package com.svetanis.datastructures.tree.trie;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public final class LongestCommonPrefix {

  public static String lcp(List<String> list) {
    Node root = Trie.build(list).getRoot();
    return lcp(root);
  }

  private static String lcp(Node root) {
    Node node = root;
    StringBuilder sb = new StringBuilder();
    while (node != null && !node.isLeaf() && node.children.size() == 1) {
      Iterator<Entry<Character, Node>> iter = node.children.entrySet().iterator();
      if (iter.hasNext()) {
        Entry<Character, Node> entry = iter.next();
        sb.append(entry.getKey());
        node = entry.getValue();
      }
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    List<String> list1 = newArrayList("geeksforgeeks", "geeks", "geek", "geezer");
    List<String> list2 = newArrayList("apple", "ape", "april");
    System.out.println(lcp(list1));
    System.out.println(lcp(list2));
  }
}
