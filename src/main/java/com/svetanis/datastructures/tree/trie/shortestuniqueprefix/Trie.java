package com.svetanis.datastructures.tree.trie.shortestuniqueprefix;

import static com.svetanis.datastructures.tree.trie.shortestuniqueprefix.Node.newNode;
import static java.util.Arrays.asList;

import java.util.Map;

// The prefix-counting trie behind the shortest-unique-prefix files in this package.
//
// Building it inserts each word letter by letter. A node stands for a prefix and its freq counts
// how many inserted words pass through it: a letter met for the first time makes a node with
// freq 1, and every later word that goes through the same node adds one. So freq == 1 on a node
// means exactly one word has that prefix. getRoot() hands the root to ShortestUniquePrefixes,
// ShortestUniquePrefixesRecursive and ShortestUniquePrefixesRecursiveBacktrack, which read freq.
//
// Time: O(S) to build -- one step per letter, S = total letters in the words
// Space: O(S) -- one node per distinct prefix, each with a HashMap of children
//
// Siblings: tree.trie.Trie is the plain trie with no freq, used by the other tries.

public class Trie {

  private Node root;

  public Trie(String str) {
    this(asList(str));
  }

  public Trie(Iterable<String> list) {
    this.root = newNode();
    for (String str : list) {
      add(str);
    }
  }

  public static Trie build(String str) {
    return new Trie(str);
  }

  public static Trie build(Iterable<String> list) {
    return new Trie(list);
  }

  private void add(String str) {
    int len = str.length();
    Node node = root;
    for (int i = 0; i < len; ++i) {
      char c = str.charAt(i);
      Map<Character, Node> map = node.children;
      if (map.get(c) != null) {
        Node child = map.get(c);
        child.freq++; // COUNT: one more word passes through this prefix
        map.put(c, child);
      } else {
        map.put(c, newNode(c)); // GROW: a new prefix, freq 1
      }
      node = map.get(c); // STEP down
    }
    node.endOfWord = true; // FLAG: a word ends at the last node only
  }

  public Node getRoot() {
    return root;
  }

}
