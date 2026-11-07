package com.svetanis.datastructures.hashmap;

import java.util.Arrays;

// 1657. Determine if Two Strings are Close
//
// areClose(s1, s2) returns true if s2 can be reached from s1 with two moves, used any number
// of times: swap the letters at any two positions, or swap two letters that both occur in the
// string everywhere at once (every a becomes b and every b becomes a). On LeetCode: both
// lengths 1..10^5, lowercase English letters only.
//
// The first move reorders freely, so only the count of each letter matters. The second move
// trades counts between letters already present, but never brings in a new letter or drops
// one. So the strings are close exactly when they use the same set of letters and their
// counts, sorted, are the same list. Different lengths fail the second test on their own:
// the sorted counts add up to different totals.
//
// Siblings, same problem: none in this package.

public final class AreCloseStrings1657 {
  // Time complexity: O(n + m) -- one pass over each string; the two sorts are of 26 counts,
  // a fixed cost whatever the lengths
  // Auxiliary Space: O(1) -- two arrays of 26 counts

  public static boolean areClose(String s1, String s2) {
    int[] f1 = freq(s1);
    int[] f2 = freq(s2);
    for (int i = 0; i < 26; i++) {
      boolean mismatch1 = f1[i] > 0 && f2[i] == 0; // MISSING: letter i is in s1, not in s2
      boolean mismatch2 = f2[i] > 0 && f1[i] == 0; // MISSING: letter i is in s2, not in s1
      if (mismatch1 || mismatch2) {
        return false;
      }
    }
    Arrays.sort(f1); // SORT: which letter holds a count no longer matters
    Arrays.sort(f2);
    for (int i = 0; i < 26; i++) {
      if (f1[i] != f2[i]) { // COMPARE: the same counts, letter names aside
        return false;
      }
    }
    return true;
  }

  private static int[] freq(String s) {
    int[] a = new int[26];
    for (int i = 0; i < s.length(); i++) {
      a[s.charAt(i) - 'a']++; // COUNT: copies of each letter
    }
    return a;
  }

  public static void main(String[] args) {
    System.out.println(areClose("abc", "bca")); // true
    System.out.println(areClose("a", "aa")); // false
    System.out.println(areClose("cabbba", "abbccc")); // true
  }
}