package com.svetanis.datastructures.tree.trie.shortestuniqueprefix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Shortest unique prefix for every word.
// For each word, the shortest prefix that no other word in the list starts with.
//   {zebra, dog, duck, dove}                   -> z, dog, du, dov
//   {geeksgeeks, geeksquiz, geeksforgeeks}     -> geeksg, geeksq, geeksf
// Given: no word is a prefix of another (so no duplicates either). A word
// that is -- dog beside dogs -- has no unique prefix, and gets no answer.
// Answers come out in input order.
//
// The one idea: put every word in a trie where each node counts the words that pass through
// it (freq). A node is a prefix, so freq == 1 means exactly one word has that prefix -- the
// word being walked. Walk each word down from the root, collecting its letters, and stop at the
// first node with freq 1: the letters collected are its shortest unique prefix. Each word is
// walked on its own, in list order, so the answers come out in input order.
//
// Time: O(S) -- S = total letters in the words: one step per letter to build, and each word's
// walk stops by its last letter
// Space: O(S) -- the trie, one node per distinct prefix
//
// Siblings: ShortestUniquePrefixesRecursive and ShortestUniquePrefixesRecursiveBacktrack read the
// same trie with one recursive walk of the whole trie, so their answers come out in trie order.
// ShortestUniquePrefixesRecursiveBacktrackIterative is the backtracking walk with an explicit stack.

public final class ShortestUniquePrefixes {

  public static List<String> sup(List<String> words) {
    Node root = Trie.build(words).getRoot(); // BUILD: every word, counting on each node
    return sup(root, words);
  }

  private static List<String> sup(Node root, List<String> words) {
    List<String> list = new ArrayList<>();
    if (root == null) {
      return Collections.unmodifiableList(list);
    }
    for (String word : words) {
      Node node = root;
      StringBuilder sb = new StringBuilder(); // the letters walked so far
      for (char c : word.toCharArray()) {
        sb.append(c);
        int freq = node.children.get(c).freq;
        if (freq == 1) {
          list.add(sb.toString()); // RECORD: only this word has this prefix
          break; // STOP: shorter prefixes were shared, this one is the shortest unique
        } else {
          node = node.children.get(c); // STEP down: still shared with another word
        }
      }
    }
    return Collections.unmodifiableList(list); // RETURN: in input order
  }

  public static void main(String[] args) {
    List<String> list = Arrays.asList("zebra", "dog", "duck", "dove");
    List<String> list2 = Arrays.asList("geeksgeeks", "geeksquiz", "geeksforgeeks");
    System.out.println(sup(list)); // [z, dog, du, dov]
    System.out.println(sup(list2)); // [geeksg, geeksq, geeksf]
  }
}
