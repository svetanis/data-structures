package com.svetanis.datastructures.tree.trie.prefix;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.svetanis.datastructures.tree.trie.Node;
import com.svetanis.datastructures.tree.trie.Trie;

// Longest prefix matching.
// Given a dictionary of words and an input string, find the longest prefix
// of the string that is also a word in the dictionary.
//   dictionary {are, area, base, cat, cater, children, basement}
//   caterer  -> cater
//   basemexy -> base
//   child    -> <Empty>   (children is a word, but no prefix of child is)
//
// Input: the dictionary words (non-empty) and a list of input strings. Returns, for each
// input string, its longest prefix that is a dictionary word, or an empty Optional when
// no prefix is. An empty dictionary word is never returned.
//
// The one idea: the dictionary goes into a trie, where a node holds one letter and a node
// marked end-of-word is a dictionary word spelled by the path above it. Walking an input
// down the trie spells its prefixes one letter at a time; every marked node passed is a
// dictionary word that begins the input. The walk keeps going past a word and remembers the
// LAST one it met, so the answer is the longest. Replace Words (648) is the same walk
// returning at the FIRST.
//
// Time: O(D + q * L) -- D = total letters in the dictionary (build), q inputs of at most L letters
// Space: O(D) -- the trie, one node per distinct dictionary prefix
//
// Siblings: replacewords.ReplaceWordsMark makes the same walk but stops at the first word.

public final class LongestMatchingPrefix {

  public static Map<String, Optional<String>> lmp(List<String> list, List<String> words) {
    Map<String, Optional<String>> map = new HashMap<>();
    Node root = Trie.build(words).getRoot(); // BUILD: the dictionary
    for (String str : list) {
      map.put(str, lmp(root, str)); // RECORD: the answer for this input
    }
    return Collections.unmodifiableMap(map);
  }

  // empty when no dictionary word begins input
  private static Optional<String> lmp(Node root, String input) {
    Node node = root;
    int longest = 0; // length of the longest dictionary word that begins input
    for (int i = 0; i < input.length(); i++) {
      node = node.children.get(input.charAt(i)); // WALK one letter down
      if (node == null) {
        break; // STOP: no dictionary word continues this way
      }
      if (node.endOfWord) {
        longest = i + 1; // REMEMBER: a word ends here; keep going, a longer one may follow
      }
    }
    return longest == 0 ? Optional.empty() : Optional.of(input.substring(0, longest)); // RETURN
  }

  public static void main(String[] args) {
    List<String> words = Arrays.asList("are", "area", "base", "cat", "cater", "children", "basement");
    List<String> list = Arrays.asList("caterer", "basemexy", "child");
    System.out.println(lmp(list, words)); // {caterer=Optional[cater], basemexy=Optional[base], child=Optional.empty}
  }
}
