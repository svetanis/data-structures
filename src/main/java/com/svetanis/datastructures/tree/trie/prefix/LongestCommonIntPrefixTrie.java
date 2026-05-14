package com.svetanis.datastructures.tree.trie.prefix;

// 3043. Find the Length of the Longest Common Prefix

public final class LongestCommonIntPrefixTrie {
  // Time complexity: O(n + m)
  // Space complexity: O(m)

  private Trie trie;

  public int longestCommonPrefix(int[] a1, int[] a2) {
    this.trie = new Trie();
    for (int num : a1) {
      trie.insert(num);
    }
    int maxLen = 0;
    for (int num : a2) {
      int len = trie.longestPrefix(num);
      maxLen = Math.max(maxLen, len);
    }
    return maxLen;
  }

  public static void main(String[] args) {
    LongestCommonIntPrefixTrie lcp = new LongestCommonIntPrefixTrie();
    int[] a1 = { 1, 10, 100 };
    int[] a2 = { 1000 };
    System.out.println(lcp.longestCommonPrefix(a1, a2)); // 3

    int[] a3 = { 1, 2, 3 };
    int[] a4 = { 4, 4, 4 };
    System.out.println(lcp.longestCommonPrefix(a3, a4)); // 0
  }

  public static class TrieNode {
    private TrieNode[] children = new TrieNode[10];
  }

  public static class Trie {
    private TrieNode root = new TrieNode();

    public void insert(int num) {
      TrieNode node = root;
      String s = String.valueOf(num);
      for (char digit : s.toCharArray()) {
        int index = digit - '0';
        if (node.children[index] == null) {
          node.children[index] = new TrieNode();
        }
        node = node.children[index];
      }
    }

    public int longestPrefix(int num) {
      TrieNode node = root;
      String s = String.valueOf(num);
      int len = 0;
      for (char digit : s.toCharArray()) {
        int index = digit - '0';
        if (node.children[index] != null) {
          len += 1;
          node = node.children[index];
        } else {
          break;
        }
      }
      return len;
    }

  }
}
