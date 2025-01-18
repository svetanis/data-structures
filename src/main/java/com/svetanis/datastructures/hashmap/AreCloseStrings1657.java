package com.svetanis.datastructures.hashmap;

import java.util.Arrays;

// 1657. Determine if Two Strings are Close

public final class AreCloseStrings1657 {
  // Time Complexity: O(n log n + m log m)
  // Space Complexity: O(n + m)

  public static boolean areClose(String s1, String s2) {
    int[] f1 = freq(s1);
    int[] f2 = freq(s2);
    for (int i = 0; i < 26; i++) {
      boolean mismatch1 = f1[i] > 0 && f2[i] == 0;
      boolean mismatch2 = f2[i] > 0 && f1[i] == 0;
      if (mismatch1 || mismatch2) {
        return false;
      }
    }
    Arrays.sort(f1);
    Arrays.sort(f2);
    for (int i = 0; i < 26; i++) {
      if (f1[i] != f2[i]) {
        return false;
      }
    }
    return true;
  }

  private static int[] freq(String s) {
    int[] a = new int[26];
    for (int i = 0; i < s.length(); i++) {
      a[s.charAt(i) - 'a']++;
    }
    return a;
  }

  public static void main(String[] args) {
    System.out.println(areClose("abc", "bca")); // true
    System.out.println(areClose("a", "aa")); // false
    System.out.println(areClose("cabbba", "abbccc")); // true
  }
}