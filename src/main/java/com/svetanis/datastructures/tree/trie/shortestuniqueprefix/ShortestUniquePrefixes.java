package com.svetanis.datastructures.tree.trie.shortestuniqueprefix;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLines;

import java.util.List;

import com.google.common.collect.ImmutableList;

public final class ShortestUniquePrefixes {

  public static ImmutableList<String> sup(List<String> words) {
    Node root = Trie.build(words).getRoot();
    return sup(root, words);
  }

  private static ImmutableList<String> sup(Node root, List<String> words) {
    List<String> list = newArrayList();
    if (root == null) {
      return newList(list);
    }
    for (String word : words) {
      Node node = root;
      StringBuilder sb = new StringBuilder();
      for (char c : word.toCharArray()) {
        sb.append(c);
        int freq = node.children.get(c).freq;
        if (freq == 1) {
          list.add(sb.toString());
          break;
        } else {
          node = node.children.get(c);
        }
      }
    }
    return newList(list);
  }

  public static void main(String[] args) {
    List<String> list = newArrayList("zebra", "dog", "duck", "dove");
    List<String> list2 = newArrayList("geeksgeeks", "geeksquiz", "geeksforgeeks");
    printLines(sup(list));
    printLines(sup(list2));
  }
}
