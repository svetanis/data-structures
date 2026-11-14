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
// root spells a prefix. A node that ends a root carries a boolean mark. Walking a sentence word
// down the trie spells its prefixes one letter at a time, so the first marked node met is the
// shortest root that begins the word: the letters walked so far ARE that root, so the answer
// is read off the walk and the root list is not needed again. Running out of trie, or of word,
// before any marked node means the word keeps itself.
//
// Time: O(R + S) -- R = total letters in the roots (build), S = letters in the sentence; a word
// is walked at most as far as it is long
// Space: O(R + S) -- the trie, and the output
//
// Siblings: ReplaceWordsIndex carries the root's position in the list in the node instead of a
// boolean, and returns list.get(position).

public final class ReplaceWordsMark {

  public static String replace(String sentence, List<String> roots) {
    Trie trie = build(roots);
    StringBuilder sb = new StringBuilder();
    for (String word : sentence.split("\\s+")) {
      sb.append(trie.shortestRoot(word)).append(" "); // REPLACE the word by its shortest root
    }
    return sb.toString().trim(); // RETURN: without the space after the last word
  }

  private static Trie build(List<String> roots) {
    Trie trie = new Trie();
    for (String root : roots) {
      trie.insert(root); // BUILD: every root into the trie
    }
    return trie;
  }

  public static void main(String[] args) {
    List<String> roots1 = Arrays.asList("cat", "bat", "rat");
    String s1 = "the cattle was rattled by the battery";
    System.out.println(replace(s1, roots1)); // the cat was rat by the bat

    List<String> roots2 = Arrays.asList("a", "b", "c");
    String s2 = "aadsfasf absbs bbab cadsfafs";
    System.out.println(replace(s2, roots2)); // a a b c

    List<String> roots3 = Arrays.asList("cat", "ca");
    String s3 = "cattle cow c";
    System.out.println(replace(s3, roots3)); // ca cow c
  }

  private static class Trie {
    private boolean isEndOfWord; // a root ends at this node
    private Trie[] children = new Trie[26];

    public void insert(String word) {
      Trie node = this;
      for (int i = 0; i < word.length(); i++) {
        int index = word.charAt(i) - 'a';
        if (node.children[index] == null) {
          node.children[index] = new Trie(); // GROW: first root to use this letter here
        }
        node = node.children[index]; // STEP down
      }
      node.isEndOfWord = true; // MARK: after the loop -- the last node only
    }

    // the shortest root that begins word, or word itself when no root does
    public String shortestRoot(String word) {
      Trie node = this;
      StringBuilder sb = new StringBuilder(); // the letters walked so far
      for (int i = 0; i < word.length(); i++) {
        char c = word.charAt(i);
        sb.append(c);
        Trie child = node.children[c - 'a'];
        if (child == null) {
          return word; // MISS: the trie ran out
        }
        if (child.isEndOfWord) {
          return sb.toString(); // FIRST root met is the shortest
        }
        node = child;
      }
      return word; // the word ran out
    }
  }
}
