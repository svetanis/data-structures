package com.svetanis.datastructures.tree.trie.shortestuniqueprefix;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLines;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

public final class ShortestUniquePrefixesRecursive {

  private static int MAX = 256;
  
  public static ImmutableList<String> sup(List<String> words) {
    char[] chars = new char[MAX];
    List<String> list = newArrayList();
    Node root = Trie.build(words).getRoot();
    sup(root, chars, 0, list);
    return newList(list);
  }

  private static void sup(Node root, char[] chars, int index, List<String> list) {
    if (root == null) {
      return;
    }

    // Base case
    if (root.freq == 1) {
      list.add(toStr(chars, index));
      return;
    }

    Map<Character, Node> map = root.children;
    for (char c : map.keySet()) {
      Node node = map.get(c);
      chars[index] = c;
      sup(node, chars, index + 1, list);
    }
  }

  private static String toStr(char[] chars, int end) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < end; i++) {
      sb.append(chars[i]);
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    List<String> list = newArrayList("zebra", "dog", "duck", "dove");
    List<String> list2 = newArrayList("geeksgeeks", "geeksquiz", "geeksforgeeks");
    printLines(sup(list));
    printLines(sup(list2));
  }
}
