package com.svetanis.datastructures.tree.trie.replacewords;

import java.util.Arrays;
import java.util.List;

// 648. Replace Words
//
// Input: a list of roots (the dictionary) and a sentence of words separated by single spaces.
// A word is replaced by the shortest root it begins with; a word that begins with no root stays.
// On LeetCode: 1 <= dictionary.length <= 1000, 1 <= dictionary[i].length <= 100,
// 1 <= sentence.length <= 10^6, lowercase letters and spaces only, single spaces, no leading
// or trailing space.
// Returns the sentence with every word replaced where it can be.
//
// The one idea: put the roots in a trie, where a node holds one letter and the path from the
// root spells a prefix. Here a node that ends a root also carries that root's position in the
// list. Walking a sentence word down the trie spells its prefixes one letter at a time, so the
// first node met that ends a root is the shortest root that begins the word: stop there and read
// its position. Running out of trie, or of word, before any node ends a root means the word keeps
// itself. The replacement is list.get(position).
//
// Time: O(R + S) -- R = total letters in the roots (build), S = letters in the sentence; a word
// is walked at most as far as it is long
// Space: O(R + S) -- the trie, and the output
//
// Siblings: ReplaceWordsMark flags the node with a boolean instead of an index, and builds the
// replacement from the letters walked. Roots that are prefixes of each other ("cat" and "ca")
// are handled alike by both, because the walk stops at the first flagged node.

public final class ReplaceWordsIndex {

  public static String replace(String sentence, List<String> list) {
    Trie trie = build(list);
    String[] words = sentence.split("\\s+");
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      int index = trie.search(word); // WALK: position of the shortest root, -1 if none
      String s = index == -1 ? word : list.get(index); // PICK: the root, or the word itself
      sb.append(" " + s);
    }
    return sb.toString().trim(); // RETURN: without the space before the first word
  }

  private static Trie build(List<String> words) {
    Trie trie = new Trie();
    for (int i = 0; i < words.size(); i++) {
      trie.insert(words.get(i), i); // BUILD: each root remembers its position
    }
    return trie;
  }

  public static void main(String[] args) {
    List<String> words1 = Arrays.asList("cat", "bat", "rat");
    String s1 = "the cattle was rattled by the battery";
    System.out.println(replace(s1, words1)); // the cat was rat by the bat

    List<String> words2 = Arrays.asList("a", "b", "c");
    String s2 = "aadsfasf absbs bbab cadsfafs";
    System.out.println(replace(s2, words2)); // a a b c

    List<String> words3 = Arrays.asList("cat", "ca");
    String s3 = "cattle cow c";
    System.out.println(replace(s3, words3)); // ca cow c
  }

  private static class Trie {
    private int index = -1; // position of the root that ends here, -1 when none does
    private Trie[] children = new Trie[26];

    public void insert(String word, int index) {
      Trie root = this;
      for (char c : word.toCharArray()) {
        int i = c - 'a';
        if (root.children[i] == null) {
          root.children[i] = new Trie(); // GROW: first root to use this letter here
        }
        root = root.children[i]; // STEP down
      }
      root.index = index; // MARK: a root ends at the last node only
    }

    public int search(String word) {
      Trie root = this;
      for (char c : word.toCharArray()) {
        int i = c - 'a';
        if (root.children[i] == null) {
          return -1; // MISS: the trie ran out before any root ended
        }
        root = root.children[i];
        if (root.index != -1) {
          return root.index; // FIRST root met is the shortest
        }
      }
      return -1; // the word ran out first
    }
  }
}
