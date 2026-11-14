package com.svetanis.datastructures.tree.trie.prefix;

import java.util.HashMap;
import java.util.Map;

// Prefix-counting trie node, with loops.
// How many inserted words start with a prefix. The same count as TrieNode,
// written as a loop instead of a recursion.
//
// Input: words inserted one at a time with insert; a prefix passed to query. Returns the number
// of inserted words that begin with the prefix, duplicates counted each time.
//
// The one idea: a node is a prefix (the letters on the path from the root), and freq holds
// how many inserted words pass through it. Insert steps down one letter and then adds one to
// the freq of the node it arrived at, so every node below the root is counted and the root never
// is. A query walks the prefix down and reads freq where it ends; a missing child means no word
// has that prefix, so 0.
//
// Time: O(L) per insert and per query -- one step per letter of the word or prefix
// Space: O(S) -- one node per distinct prefix of an inserted word, S = total letters inserted
//
// Siblings: TrieNode is the recursive version. It also counts the root, so there the empty
// prefix answers the total number of words; here it answers 0.

public final class TrieNodeSimple {

  protected int freq;
  protected Map<Character, TrieNodeSimple> children;

  public TrieNodeSimple() {
    this.freq = 0;
    this.children = new HashMap<>();
  }

  public void insert(String s) {
    TrieNodeSimple node = this;
    for (char c : s.toCharArray()) {
      node.children.putIfAbsent(c, new TrieNodeSimple()); // GROW: first word to use this letter here
      node = node.children.get(c); // STEP down
      node.freq++; // COUNT: one more word passes through the node we arrived at
    }
  }

  // how many inserted words start with this prefix. the empty prefix answers
  // 0, not the word total: insert counts each node after stepping down to it,
  // so the root is never counted. TrieNode counts the root, and answers the total.
  public int query(String prefix) {
    TrieNodeSimple node = this;
    for (char c : prefix.toCharArray()) {
      if (!node.children.containsKey(c)) {
        return 0; // MISS: no inserted word has this prefix
      }
      node = node.children.get(c); // STEP down
    }
    return node.freq; // RETURN: the count where the prefix ends
  }

  public static void main(String[] args) {
    TrieNodeSimple trie = new TrieNodeSimple();
    trie.insert("cat");
    trie.insert("car");
    trie.insert("dog");
    System.out.println(trie.query("c")); // 2
    System.out.println(trie.query("ca")); // 2
    System.out.println(trie.query("cat")); // 1
    System.out.println(trie.query("d")); // 1
    System.out.println(trie.query("z")); // 0
  }
}