package com.svetanis.datastructures.tree.trie.prefix;

// 3043. Find the Length of the Longest Common Prefix
//
// Input: two arrays of positive integers, a1 and a2. A prefix of a number is a
// run of its leading digits, so 123 has the prefixes 1, 12 and 123. On LeetCode:
// 1 <= a1.length, a2.length <= 5 * 10^4, 1 <= a1[i], a2[i] <= 10^8.
// Returns the length, in digits, of the longest prefix shared by some x in a1 and
// some y in a2; 0 when no pair shares even the first digit.
//
// The one idea: write each number of a1 into a trie of digits -- one node holds one
// digit, and the path from the root to a node spells a prefix that some x in a1 has.
// To compare y, walk its digits down the same trie: each step that finds a child is
// one more digit shared with some x, and the first missing child ends the walk. The
// number of steps taken is the longest prefix y shares with anything in a1; the
// answer is the largest of those over all y.
//
// Time: O((n + m) * d) -- n = a1.length, m = a2.length, d <= 9 digits per number
// Space: O(n * d) -- up to 9 nodes per number of a1, each with 10 child slots
//
// Siblings: LongestCommonIntPrefix keeps the prefixes of a1 in a HashSet of numbers
// instead of a trie of digits.

public final class LongestCommonIntPrefixTrie {

  private Trie trie;

  public int longestCommonPrefix(int[] a1, int[] a2) {
    this.trie = new Trie(); // RESET: a second call on this object starts from an empty trie
    for (int num : a1) {
      trie.insert(num); // BUILD: every number of a1 goes in
    }
    int maxLen = 0; // BEST: longest shared prefix so far, in digits
    for (int num : a2) {
      int len = trie.longestPrefix(num); // WALK y down the trie
      maxLen = Math.max(maxLen, len);
    }
    return maxLen; // RETURN: 0 when nothing matched
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
    private TrieNode[] children = new TrieNode[10]; // one slot per digit 0..9
  }

  public static class Trie {
    private TrieNode root = new TrieNode();

    public void insert(int num) {
      TrieNode node = root;
      String s = String.valueOf(num);
      for (char digit : s.toCharArray()) {
        int index = digit - '0';
        if (node.children[index] == null) {
          node.children[index] = new TrieNode(); // GROW: first number to use this digit here
        }
        node = node.children[index]; // STEP down to the digit's node
      }
    }

    public int longestPrefix(int num) {
      TrieNode node = root;
      String s = String.valueOf(num);
      int len = 0; // digits matched so far
      for (char digit : s.toCharArray()) {
        int index = digit - '0';
        if (node.children[index] != null) {
          len += 1; // MATCH: some number of a1 has this digit here
          node = node.children[index];
        } else {
          break; // STOP: no number of a1 continues this way
        }
      }
      return len;
    }

  }
}
