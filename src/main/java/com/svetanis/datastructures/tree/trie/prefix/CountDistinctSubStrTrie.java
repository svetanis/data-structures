package com.svetanis.datastructures.tree.trie.prefix;

// 1698. Number of Distinct Substrings in a String

public final class CountDistinctSubStrTrie {
  // Time Complexity: O(n^2)
  // Space Complexity: O(n)

  private Trie root;

  public int countDistinct(String s) {
    this.root = new Trie();
    int len = s.length();
    int count = 0;
    for (int start = 0; start < len; start++) {
      Trie node = root;
      for (int end = start; end < len; end++) {
        int index = s.charAt(end) - 'a';
        if (node.children[index] == null) {
          count += 1;
          node.children[index] = new Trie();
        }
        node = node.children[index];
      }
    }
    return count;
  }

  public static void main(String[] args) {
    CountDistinctSubStrTrie cds = new CountDistinctSubStrTrie();
    System.out.println(cds.countDistinct("aabbaba")); // 21
    System.out.println(cds.countDistinct("abcdefg")); // 28
  }

  private static class Trie {

    public Trie[] children;

    public Trie() {
      this.children = new Trie[26];
    }
  }
}