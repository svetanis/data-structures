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
// chars holds the path, so a word longer than MAX throws.
//
// The one idea: put every word in a trie where each node counts the words that pass through
// it (freq). A node is a prefix, so freq == 1 means exactly one word has that prefix. Walk the
// whole trie once, depth first, keeping the letters of the path in chars: a node with freq 1
// ends its branch, and the letters on the path to it are a shortest unique prefix (its parent
// had freq above 1, otherwise the walk would have stopped there). Nodes with a larger freq are
// shared, so the walk goes on into their children.
//
// Time: O(S) -- S = total letters in the words: the walk visits each trie node at most once
// Space: O(S) -- the trie; the path buffer is MAX letters and the stack is at most as deep as
// the longest word
//
// Siblings: ShortestUniquePrefixes walks each word on its own and answers in input order;
// ShortestUniquePrefixesRecursiveBacktrack is this walk with a growing list in place of the
// char array, so it has no word-length limit of 256.

public final class ShortestUniquePrefixesRecursive {

  private static int MAX = 256;

  public static List<String> sup(List<String> words) {
    char[] chars = new char[MAX]; // the letters on the path from the root
    List<String> list = new ArrayList<>();
    Node root = Trie.build(words).getRoot(); // BUILD: every word, counting on each node
    sup(root, chars, 0, list);
    return Collections.unmodifiableList(list);
  }

  private static void sup(Node root, char[] chars, int index, List<String> list) {
    if (root == null) {
      return;
    }

    // Base case
    if (root.freq == 1) {
      list.add(toStr(chars, index)); // RECORD: the path to here is only one word's prefix
      return;
    }

    Map<Character, Node> map = root.children;
    for (char c : map.keySet()) {
      Node node = map.get(c);
      chars[index] = c; // PUSH the letter; the next sibling overwrites it, so no undo
      sup(node, chars, index + 1, list); // DESCEND
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
    List<String> list = Arrays.asList("zebra", "dog", "duck", "dove");
    List<String> list2 = Arrays.asList("geeksgeeks", "geeksquiz", "geeksforgeeks");
    System.out.println(sup(list)); // [du, dov, dog, z]
    System.out.println(sup(list2)); // [geeksq, geeksf, geeksg]
  }
}
