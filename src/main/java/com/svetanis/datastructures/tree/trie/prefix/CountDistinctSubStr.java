package com.svetanis.datastructures.tree.trie.prefix;

import java.util.HashSet;
import java.util.Set;

// 1698. Number of Distinct Substrings in a String

public final class CountDistinctSubStr {
  // Time Complexity: O(n^3)
  // Space Complexity: O(n^2)

  public static int countDistinct(String s) {
    Set<String> set = new HashSet<>();
    int len = s.length();
    for (int i = 0; i < len; i++) {
      for (int j = i + 1; j <= len; j++) {
        set.add(s.substring(i, j));
      }
    }
    return set.size();
  }

  public static void main(String[] args) {
    System.out.println(countDistinct("aabbaba")); // 21
    System.out.println(countDistinct("abcdefg")); // 28
  }
}