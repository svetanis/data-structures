package com.svetanis.datastructures.tree.trie.shortestuniqueprefix;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLines;

import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

// Shortest unique prefix for every word.
// For each word, the shortest prefix that no other word in the list starts with.
//   {zebra, dog, duck, dove}                   -> z, dog, du, dov
//   {geeksgeeks, geeksquiz, geeksforgeeks}     -> geeksg, geeksq, geeksf
// Given: no word is a prefix of another (so no duplicates either). A word
// that is -- dog beside dogs -- has no unique prefix, and gets no answer.
// Answers come out in trie order, not input order: the walk visits
// children in HashMap order, so {zebra, dog, duck, dove} -> du, dov, dog, z.

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
      list.remove(list.size() - 1); // undo by position: a letter can repeat on the path
    }
  }

  public static void main(String[] args) {
    List<String> list = newArrayList("zebra", "dog", "duck", "dove");
    List<String> list2 = newArrayList("geeksgeeks", "geeksquiz", "geeksforgeeks");
    printLines(sup(list));
    printLines(sup(list2));

    // letters repeat on the path
    List<String> repeats = newArrayList("aba", "abb", "abc");
    printLines(sup(repeats)); // aba, abb, abc
  }
}
