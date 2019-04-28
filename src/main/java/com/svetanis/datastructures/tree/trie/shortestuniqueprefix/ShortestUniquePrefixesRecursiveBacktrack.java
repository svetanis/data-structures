package com.svetanis.datastructures.tree.trie.shortestuniqueprefix;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLines;

import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

public final class ShortestUniquePrefixesRecursiveBacktrack {

  public static ImmutableList<String> sup(List<String> words) {
    List<String> list = newArrayList();
    Node root = Trie.build(words).getRoot();
    sup(root, newArrayList(), list);
    return newList(list);
  }

  private static void sup(Node root, List<Character> list, List<String> lists) {
    if (root == null) {
      return;
    }

    if (root.freq == 1) {
      lists.add(Joiner.on("").join(list));
      return;
    }

    Map<Character, Node> map = root.children;
    for (char c : map.keySet()) {
      Node node = map.get(c);
      list.add(c);
      sup(node, list, lists);
      // backtrack
      int index = list.indexOf(c);
      if (index != -1) {
        for (int i = index; i < list.size(); i++) {
          list.remove(index);
        }
      }
    }
  }

  public static void main(String[] args) {
    List<String> list = newArrayList("zebra", "dog", "duck", "dove");
    List<String> list2 = newArrayList("geeksgeeks", "geeksquiz", "geeksforgeeks");
    printLines(sup(list));
    printLines(sup(list2));
  }
}
