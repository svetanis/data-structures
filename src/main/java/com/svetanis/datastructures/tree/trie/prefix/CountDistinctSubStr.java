package com.svetanis.datastructures.tree.trie.prefix;

import java.util.HashSet;
import java.util.Set;

// 1698. Number of Distinct Substrings in a String
//
// Input: a string s. On LeetCode: 1 <= s.length <= 500, lowercase letters only.
// Returns the number of different substrings of s. A substring is a run of
// consecutive letters, so "ab" and "ba" are different substrings of "abba".
//
// The one idea: a substring is fixed by where it starts and where it ends, so s
// has n * (n + 1) / 2 of them -- but two different (start, end) pairs can spell
// the same letters ("a" at index 0 and "a" at index 1 of "aab"). A set keeps one
// copy of each spelling, so its size is the answer. This is the plain baseline:
// it builds every substring as a real String and lets the set drop the repeats.
//
// Time: O(n^3) -- n * (n + 1) / 2 substrings, each copied and hashed in O(n)
// Space: O(n^3) -- the set holds up to n * (n + 1) / 2 strings, about n / 3 letters each
//
// Siblings: CountDistinctSubStrTrie counts the same thing without copying a single
// substring: it walks the letters into a trie and counts the nodes it creates.

public final class CountDistinctSubStr {

  public static int countDistinct(String s) {
    Set<String> set = new HashSet<>(); // SEEN: one copy of each spelling met so far
    int len = s.length();
    for (int i = 0; i < len; i++) { // START of the substring
      for (int j = i + 1; j <= len; j++) { // END: one past its last letter
        set.add(s.substring(i, j)); // RECORD: a spelling already in the set changes nothing
      }
    }
    return set.size(); // RETURN: the number of different spellings
  }

  public static void main(String[] args) {
    System.out.println(countDistinct("aabbaba")); // 21
    System.out.println(countDistinct("abcdefg")); // 28
  }
}