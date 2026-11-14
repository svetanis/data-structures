package com.svetanis.datastructures.tree.trie.autocomplete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Minimum strokes to type a list of words with auto-complete (not a LeetCode problem)
//
// Input: a series of n words (lowercase letters; the words are short, see Known limit). Every
// word is added to the dictionary, and then typed out using the fewest strokes: once the letters
// typed so far fit only this word among all the words added so far, the rest is completed with
// no more strokes. Returns the total number of strokes to type out all the words.
//
// The one idea: one TrieNode holds freq, how many of the added words pass through it, that is,
// start with the letters leading to it. Right after a word is added, typing it letter by letter
// stops being needed at the first node whose freq is 1, because only this word passes there. So
// the strokes for a word are the number of letters until that node, or the whole word when
// no node on its path has freq 1.
//
// Time: O(c) -- c is the total number of letters of all the words; each word is walked twice
// Space: O(c) -- the trie holds at most one node per letter
//
// Known limit: insert and strokes recurse once per letter, so a single word of several thousand
// letters overflows the default stack; words are expected to be ordinary length.
//
// Siblings: none.

public final class Autocomplete {

  public static int autocomplete(List<String> words) {
    TrieNode root = new TrieNode();
    root.freq = 1; // never 1 after an insert, so every word costs at least one stroke
    int total = 0;
    for (String word : words) {
      root.insert(word, 0); // ADD the word to the dictionary first
      total += root.strokes(word, 0); // COUNT the strokes to type it
    }
    return total;
  }

  public static void main(String[] args) {
    List<String> list = Arrays.asList("hi", "hello", "bojack", "hills", "hill");
    System.out.println(autocomplete(list)); // 11

    List<String> list2 = Arrays.asList("a", "aa", "aaa", "aaaa", "aaaaa");
    System.out.println(autocomplete(list2)); // 15

    List<String> list3 = Arrays.asList("to", "be", "or", "not", "two", "bee");
    System.out.println(autocomplete(list3)); // 9

    List<String> list4 = Arrays.asList("aaaaa", "aaaa", "aaa", "aa", "a");
    System.out.println(autocomplete(list4)); // 11

    System.out.println(autocomplete(new ArrayList<>())); // 0
  }

  private static final class TrieNode {
    private int freq; // how many inserted words pass through this node
    private Map<Character, TrieNode> children = new HashMap<>();

    private void insert(String word, int index) {
      freq += 1; // COUNT: one more word passes through this node
      if (index == word.length()) {
        return;
      }
      children.putIfAbsent(word.charAt(index), new TrieNode());
      children.get(word.charAt(index)).insert(word, index + 1); // WALK one letter down
    }

    // letters typed until only this word is left below, or the word is complete
    private int strokes(String word, int index) {
      if (index == word.length() || freq == 1) {
        return 0; // STOP: the word is complete, or no other word passes here
      }
      return 1 + children.get(word.charAt(index)).strokes(word, index + 1); // TYPE one more letter
    }
  }
}
