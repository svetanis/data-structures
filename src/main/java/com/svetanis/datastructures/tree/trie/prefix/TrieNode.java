package com.svetanis.datastructures.tree.trie.prefix;

import java.util.HashMap;
import java.util.Map;

// Prefix-counting trie node.
// How many inserted words start with a prefix.
//
// Input: words inserted one at a time with insert; a prefix passed to prefixQuery. Returns the
// number of inserted words that begin with the prefix, duplicates counted each time; the empty
// prefix returns the total number of words, because the root counts every insert.
//
// The one idea: a node is a prefix (the letters on the path from the root), and freq holds
// how many inserted words pass through it. Insert walks a word down and adds one to freq at
// every node it touches, the root and the node at the word's last letter included. A query
// walks the prefix down and reads freq where it ends; a missing child means no word has that
// prefix, so 0.
//
// Time: O(L) per insert and per query -- one step per letter of the word or prefix
// Space: O(S) -- one node per distinct prefix of an inserted word, S = total letters inserted
//
// Known limit: insert and prefixQuery recurse one frame per letter, so a word or prefix of
// roughly 5,000 letters or more overflows the default stack. It only happens on very large
// input. TrieNodeSimple is the same count written with loops, with one difference: the empty
// prefix answers 0 there.
//
// Siblings: TrieNodeSimple (loops, no recursion). PrefixCount uses this class to answer a
// list of prefix queries.

public final class TrieNode {

  protected int freq;
  protected char key;
  protected Map<Character, TrieNode> children;

  public TrieNode() {
    this('$');
  }

  public TrieNode(char letter) {
    this.freq = 0;
    this.key = letter;
    this.children = new HashMap<>();
  }

  public void insert(String str) {
    insert(str, 0);
  }

  private void insert(String str, int index) {
    freq += 1; // COUNT: one more word passes through this node
    if (index == str.length()) {
      return; // BASE: the whole word is spelled
    }
    char c = str.charAt(index);
    if (!children.containsKey(c)) {
      children.put(c, new TrieNode(c)); // GROW: first word to use this letter here
    }
    TrieNode node = children.get(c);
    node.insert(str, index + 1); // DESCEND one letter
  }

  public int prefixQuery(String prefix) {
    return prefixQuery(prefix, 0);
  }

  private int prefixQuery(String s, int index) {
    if (index == s.length()) {
      return freq; // BASE: the prefix is used up, this node's count is the answer
    }
    char c = s.charAt(index);
    if (children.containsKey(c)) {
      TrieNode node = children.get(c);
      return node.prefixQuery(s, index + 1); // DESCEND one letter
    } else {
      return 0; // MISS: no inserted word has this prefix
    }
  }

  @Override
  public String toString() {
    return Character.toString(key);
  }
}