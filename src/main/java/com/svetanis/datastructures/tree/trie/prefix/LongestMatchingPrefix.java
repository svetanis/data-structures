package com.svetanis.datastructures.tree.trie.prefix;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.java.base.collect.Maps.checkedPut;
import static com.svetanis.java.base.collect.Maps.newMap;

import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
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
// The walk keeps going past a word and remembers the LAST one it met.
// Replace Words (648) is the same walk returning at the FIRST.

public final class LongestMatchingPrefix {

  public static ImmutableMap<String, Optional<String>> lmp(List<String> list, List<String> words) {
    Map<String, Optional<String>> map = newHashMap();
    Node root = Trie.build(words).getRoot();
    for (String str : list) {
      checkedPut(map, str, lmp(root, str));
    }
    return newMap(map);
  }

  // absent when no dictionary word begins input
  private static Optional<String> lmp(Node root, String input) {
    Node node = root;
    int longest = 0; // length of the longest dictionary word that begins input
    for (int i = 0; i < input.length(); i++) {
      node = node.children.get(input.charAt(i));
      if (node == null) {
        break;
      }
      if (node.endOfWord) {
        longest = i + 1;
      }
    }
    return longest == 0 ? absent() : of(input.substring(0, longest));
  }

  public static void main(String[] args) {
    List<String> words = newArrayList("are", "area", "base", "cat", "cater", "children", "basement");
    List<String> list = newArrayList("caterer", "basemexy", "child");
    System.out.println(lmp(list, words)); // caterer=cater, basemexy=base, child=absent
  }
}
