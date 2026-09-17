package com.svetanis.datastructures.tree.trie.autocomplete;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.List;
import java.util.Map;

// given a series of n words
// for every word, add it to
// the dictionary, and then
// type out the word using the
// minimum number of strokes
// to auto-complete the word.
// find the minimum total number
// of strokes needed to type
// out all the words.

public final class Autocomplete {
  // Time Complexity: O(c)
  // Space Complexity: O(c)
  // c - total number of chars in the input

  public static int autocomplete(List<String> words) {
    TrieNode root = new TrieNode();
    root.freq = 1; // never 1 after an insert, so every word costs at least one stroke
    int total = 0;
    for (String word : words) {
      root.insert(word, 0);
      total += root.strokes(word, 0);
    }
    return total;
  }

  public static void main(String[] args) {
    List<String> list = newArrayList("hi", "hello", "bojack", "hills", "hill");
    System.out.println(autocomplete(list)); // 11

    List<String> list2 = newArrayList("a", "aa", "aaa", "aaaa", "aaaaa");
    System.out.println(autocomplete(list2)); // 15

    List<String> list3 = newArrayList("to", "be", "or", "not", "two", "bee");
    System.out.println(autocomplete(list3)); // 9

    List<String> list4 = newArrayList("aaaaa", "aaaa", "aaa", "aa", "a");
    System.out.println(autocomplete(list4)); // 11

    System.out.println(autocomplete(newArrayList())); // 0
  }

  private static final class TrieNode {
    private int freq; // how many inserted words pass through this node
    private Map<Character, TrieNode> children = newHashMap();

    private void insert(String word, int index) {
      freq += 1;
      if (index == word.length()) {
        return;
      }
      children.putIfAbsent(word.charAt(index), new TrieNode());
      children.get(word.charAt(index)).insert(word, index + 1);
    }

    // letters typed until only this word is left below, or the word is complete
    private int strokes(String word, int index) {
      if (index == word.length() || freq == 1) {
        return 0;
      }
      return 1 + children.get(word.charAt(index)).strokes(word, index + 1);
    }
  }
}
