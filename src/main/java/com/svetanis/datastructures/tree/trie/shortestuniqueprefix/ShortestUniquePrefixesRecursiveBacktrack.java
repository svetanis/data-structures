package com.svetanis.datastructures.tree.trie.shortestuniqueprefix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

// Shortest unique prefix for every word.
// For each word, the shortest prefix that no other word in the list starts with.
//   {zebra, dog, duck, dove}                   -> z, dog, du, dov
//   {geeksgeeks, geeksquiz, geeksforgeeks}     -> geeksg, geeksq, geeksf
// Given: no word is a prefix of another (so no duplicates either). A word
// that is -- dog beside dogs -- has no unique prefix, and gets no answer.
// Answers come out in trie order, not input order: the walk visits
// children in HashMap order, so {zebra, dog, duck, dove} -> du, dov, dog, z.
//
// The one idea: put every word in a trie where each node counts the words that pass through
// it (freq). A node is a prefix, so freq == 1 means exactly one word has that prefix. Walk the
// whole trie once, depth first, with a list holding the letters of the path: add a letter on
// the way down and remove it on the way back (backtracking). A node with freq 1 ends its branch,
// and the list at that moment is a shortest unique prefix. Nodes with a larger freq are shared,
// so the walk goes on into their children.
//
// Time: O(S) -- S = total letters in the words: the walk visits each trie node at most once
// Space: O(S) -- the trie; the path list and the stack are at most as deep as the longest word
//
// Known limit: the walk recurses one frame per letter of the longest word, so a word of
// roughly 10,000 letters or more overflows the default stack. It only happens on very large
// input. ShortestUniquePrefixesRecursiveBacktrackIterative is the same walk with an explicit stack.
//
// Siblings: ShortestUniquePrefixesRecursive is the same walk over a char array that each sibling
// overwrites, so it needs no undo but throws on a word longer than 256 letters;
// ShortestUniquePrefixes walks each word on its own and answers in input order.

public final class ShortestUniquePrefixesRecursiveBacktrack {

  public static List<String> sup(List<String> words) {
    List<String> list = new ArrayList<>();
    Node root = Trie.build(words).getRoot(); // BUILD: every word, counting on each node
    sup(root, new ArrayList<>(), list);
    return Collections.unmodifiableList(list);
  }

  private static void sup(Node root, List<Character> list, List<String> lists) {
    if (root == null) {
      return;
    }

    if (root.freq == 1) {
      lists.add(join(list)); // RECORD: the path to here is only one word's prefix
      return;
    }

    Map<Character, Node> map = root.children;
    for (char c : map.keySet()) {
      Node node = map.get(c);
      list.add(c); // PUSH the letter
      sup(node, list, lists); // DESCEND
      list.remove(list.size() - 1); // UNDO by position: a letter can repeat on the path
    }
  }

  private static String join(List<Character> list) {
    StringBuilder sb = new StringBuilder();
    for (char c : list) {
      sb.append(c);
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    List<String> list = Arrays.asList("zebra", "dog", "duck", "dove");
    List<String> list2 = Arrays.asList("geeksgeeks", "geeksquiz", "geeksforgeeks");
    System.out.println(sup(list)); // [du, dov, dog, z]
    System.out.println(sup(list2)); // [geeksq, geeksf, geeksg]

    // letters repeat on the path
    List<String> repeats = Arrays.asList("aba", "abb", "abc");
    System.out.println(sup(repeats)); // [aba, abb, abc]
  }
}
