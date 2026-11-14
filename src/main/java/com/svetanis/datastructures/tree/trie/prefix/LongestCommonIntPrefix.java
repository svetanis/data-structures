package com.svetanis.datastructures.tree.trie.prefix;

import java.util.HashSet;
import java.util.Set;

// 3043. Find the Length of the Longest Common Prefix
//
// Input: two arrays of positive integers, a1 and a2. A prefix of a number is a
// run of its leading digits, so 123 has the prefixes 1, 12 and 123. On LeetCode:
// 1 <= a1.length, a2.length <= 5 * 10^4, 1 <= a1[i], a2[i] <= 10^8.
// Returns the length, in digits, of the longest prefix shared by some x in a1 and
// some y in a2; 0 when no pair shares even the first digit.
//
// The one idea: a number's prefixes are what is left when digits are dropped off its
// end -- 123, then 123 / 10 = 12, then 1. A number has at most 9 of them. Put every
// prefix of every number in a1 into a set (at most 9 per number). Then for each y in
// a2, drop its digits one at a time: the first value found in the set is the longest
// prefix y shares with anything in a1, and its digit count is the candidate answer.
// The set holds numbers, not strings, so there is no string copying.
//
// Time: O((n + m) * d) -- n = a1.length, m = a2.length, d <= 9 digits per number
// Space: O(n * d) -- the set holds up to 9 prefixes for each number of a1
//
// Siblings: LongestCommonIntPrefixTrie stores the digits of a1 in a trie and walks each
// y down it; the answer is the same, with a node per distinct prefix instead of a set entry.

public final class LongestCommonIntPrefix {

  public static int longestCommonPrefix(int[] a1, int[] a2) {
    Set<Integer> set = prefixes(a1);
    int max = 0; // BEST: longest shared prefix so far, in digits
    for (int num : a2) {
      if (set.contains(num)) { // SELF: y itself is a prefix of some x (the loop below tests it again)
        max = Math.max(max, String.valueOf(num).length());
      }
      while (num > 0) { // DROP the last digit each time round
        if (set.contains(num)) { // MATCH: this prefix of y is also a prefix of some x
          max = Math.max(max, String.valueOf(num).length());
        }
        num /= 10; // DROP: 123 -> 12 -> 1 -> 0
      }
    }
    return max; // RETURN: 0 when nothing matched
  }

  private static Set<Integer> prefixes(int[] a) {
    Set<Integer> set = new HashSet<>();
    for (int num : a) {
      while (num > 0) {
        set.add(num); // RECORD: this prefix of num
        num /= 10; // DROP the last digit
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
