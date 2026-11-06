package com.svetanis.datastructures.array.subarray.zerosum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Find every contiguous subarray that adds up to zero.
//
// Input: an integer array a. Returns the list of { left, right } index pairs, one per subarray with
// sum 0, in order of the right end. The list is empty if there is none. Not a LeetCode problem;
// 560. Subarray Sum Equals K counts them for any k.
//
// The one idea: sum is the running total of a[0 .. i]. Two positions with the same running total
// bound a zero-sum span between them. So the map keeps, for each running total, the LIST of all
// indices where it occurred, and at i every earlier index j in that list gives one span a[j + 1 ..
// i]. A total of 0 at i also gives the span a[0 .. i], which has no earlier total to match.
//
// Time: O(n + z) -- one pass, plus one step per span reported; z can reach n * (n - 1) / 2 (an
// array of zeros), so the output alone can be quadratic.
// Space: O(n) -- the map holds each index once, besides the answer list.
//
// Siblings: ZeroSumSubArrayIndices returns one span; ZeroSumLongestSubArray the longest length; in
// the algorithms repo prefixsum.AllZeroSumSubarrays is the same method, and AllZeroSumSubarraysGuava
// keeps the index lists in a Guava Multimap.

public final class ZeroSumSubArrays {

  public static List<int[]> sum(int[] a) {
    int n = a.length;
    int sum = 0; // RUNNING TOTAL: of a[0 .. i]
    List<int[]> list = new ArrayList<>();
    Map<Integer, List<Integer>> map = new HashMap<>(); // SEEN: running total -> all indices where it occurred
    for (int i = 0; i < n; i++) {
      sum += a[i];
      if (sum == 0) {
        list.add(new int[] { 0, i }); // FOUND: from the start
      }
      if (map.containsKey(sum)) {
        for (int start : map.get(sum)) {
          list.add(new int[] { start + 1, i }); // FOUND: between two equal totals
        }
      }
      map.computeIfAbsent(sum, key -> new ArrayList<>()).add(i); // RECORD: this index under its total
    }
    return list;
  }

  public static void main(String[] args) {
    int[] a = { 6, 3, -1, -3, 4, -2, 2, 4, 6, -12, -7 };
    for (int[] span : sum(a)) {
      System.out.println("(" + span[0] + ", " + span[1] + ")");
    }
    // (2, 4)
    // (2, 6)
    // (5, 6)
    // (6, 9)
    // (0, 10)
  }
}
