package com.svetanis.datastructures.tree.suffix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// One node of the suffix trie that SuffixTree builds. Not a LeetCode problem.
//
// Input: insert(s, index) takes a suffix s of the text and the position index where s starts in
// the text. search(s) takes a pattern and returns every position of the text where the pattern
// starts; any characters are allowed, spaces included, and the empty pattern returns every
// position that was inserted.
//
// The one idea: a path of edges down from the root spells a string, one character per edge, and
// the node at its end keeps indexes, the start positions of all the suffixes that pass through
// it. A suffix that starts at position i and begins with the pattern is exactly an occurrence
// of the pattern at position i, so after inserting every suffix, the indexes at the node a
// pattern leads to are all of its occurrences. Searching is only walking the pattern's
// characters down from the root; falling off the trie means no occurrence.
//
// Time: insert O(L^2) for a suffix of length L -- L steps down, each copying the remainder with
// substring. search O(m^2) for a pattern of length m, for the same reason; the list it returns
// is handed back, not copied.
// Space: insert O(L) -- up to L new nodes and L index entries for a suffix of length L; the
// whole text of length n costs O(n^2) in SuffixTree.
//
// Notes: search returns the node's own list, not a copy. The field value is left as it is: it
// holds the first character of the last suffix inserted through this node, and nothing reads it.

public final class Node {

  public char value;
  public List<Integer> indexes;
  public Map<Character, Node> children;

  public Node() {
    this.value = '0';
    this.indexes = new ArrayList<>();
    this.children = new HashMap<>();
  }

  public void insert(String s, int index) {
    indexes.add(index); // RECORD: this suffix passes through here
    if (!isEmpty(s)) { // an empty rest ends the path
      value = s.charAt(0);
      Node child = null;
      if (children.containsKey(value)) {
        child = children.get(value); // WALK: the edge for this character exists
      } else {
        child = new Node(); // MAKE: a new edge and node for this character
        children.put(value, child);
      }
      String remainder = s.substring(1);
      child.insert(remainder, index); // DESCEND: the rest of the suffix, one level down
    }
  }

  public List<Integer> search(String s) {
    if (isEmpty(s)) { // the whole pattern is matched
      return indexes; // RETURN: every suffix that passed through this node
    } else {
      char first = s.charAt(0);
      if (children.containsKey(first)) {
        String remainder = s.substring(1);
        return children.get(first).search(remainder); // DESCEND: one pattern character
      }
    }
    return new ArrayList<>(); // MISS: no suffix has this prefix
  }

  // only the empty (or missing) string is empty: a space is a character like any other
  private static boolean isEmpty(String s) {
    return s == null || s.isEmpty();
  }
}