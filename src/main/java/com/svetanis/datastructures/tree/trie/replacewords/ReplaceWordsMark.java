package com.svetanis.datastructures.tree.trie.replacewords;

import java.util.Arrays;
import java.util.List;

// 648. Replace Words

// The node carries a boolean mark instead of an index into the root list,
// so the replacement is the letters walked so far rather than list.get(index).

public final class ReplaceWordsMark {

  public static String replace(String sentence, List<String> roots) {
    Trie trie = build(roots);
    StringBuilder sb = new StringBuilder();
    for (String word : sentence.split("\\s+")) {
      sb.append(trie.shortestRoot(word)).append(" ");
    }
    return sb.toString().trim(); // the space after the last word
  }

  private static Trie build(List<String> roots) {
    Trie trie = new Trie();
    for (String root : roots) {
      trie.insert(root);
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
    private boolean isEndOfWord;
    private Trie[] children = new Trie[26];

    public void insert(String word) {
      Trie node = this;
      for (int i = 0; i < word.length(); i++) {
        int index = word.charAt(i) - 'a';
        if (node.children[index] == null) {
          node.children[index] = new Trie();
        }
        node = node.children[index];
      }
      node.isEndOfWord = true; // after the loop -- the last node only
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
          return word; // the trie ran out
        }
        if (child.isEndOfWord) {
          return sb.toString(); // the first root met is the shortest
        }
        node = child;
      }
      return word; // the word ran out
    }
  }
}
