package com.svetanis.datastructures.tree.trie.prefix;

import java.util.HashSet;
import java.util.Set;

// 3043. Find the Length of the Longest Common Prefix

public final class LongestCommonIntPrefix {
  // Time complexity: O(n * log M + n * log N)
  // Space complexity: O(m * log M)

  public static int longestCommonPrefix(int[] a1, int[] a2) {
    Set<Integer> set = prefixes(a1);
    int max = 0;
    for (int num : a2) {
      if (set.contains(num)) {
        max = Math.max(max, String.valueOf(num).length());
      }
      while (num > 0) {
        if (set.contains(num)) {
          max = Math.max(max, String.valueOf(num).length());
        }
        num /= 10;
      }
    }
    return max;
  }

  private static Set<Integer> prefixes(int[] a) {
    Set<Integer> set = new HashSet<>();
    for (int num : a) {
      while (num > 0) {
        set.add(num);
        num /= 10;
      }
    }
    return set;
  }

  public static void main(String[] args) {
    int[] a1 = { 1, 10, 100 };
    int[] a2 = { 1000 };
    System.out.println(longestCommonPrefix(a1, a2)); // 3

    int[] a3 = { 1, 2, 3 };
    int[] a4 = { 4, 4, 4 };
    System.out.println(longestCommonPrefix(a3, a4)); // 0
  }
}
