package com.svetanis.datastructures.array.subarray;

import static java.lang.Math.max;

import java.util.HashMap;
import java.util.Map;

// find the length of the longest sub-array having sum equal to the given value k.
//
// Input: an integer array a and k. Returns the length of the longest subarray whose sum is k, or 0
// if there is none. Same problem as LeetCode 325. Maximum Size Subarray Sum Equals k (a premium
// problem; there 1 <= a.length <= 2 * 10^5, -10^4 <= a[i] <= 10^4, -10^9 <= k <= 10^9).
//
// The one idea: sum is the running total of a[0 .. i]. A subarray a[j + 1 .. i] has sum k exactly
// when the running total at j is sum - k. So the map keeps, for each running total, the FIRST index
// where it occurred (the earliest left end gives the longest subarray), and each position looks up
// sum - k. A subarray that starts at index 0 has no running total before it, so the sum == k check
// covers it -- and the running total is recorded in the map even when sum == k, because a later
// position may need that same total as its left end.
//
// Time: O(n) -- one pass, constant-time map operations.
// Space: O(n) -- the map holds up to n running totals.
//
// Siblings: LongestBinarySubArrayWithDiffEqualsK is this loop with every 0 counted as -1. In
// prefixsum, LongestSubArrayLenWithSumEqualsKHashMap seeds the map with total 0 at index -1
// instead of checking sum == k.

public final class LongestSubArrLenGivenSum {

  public static int maxSubArrLen(int[] a, int k) {
    int n = a.length;
    int sum = 0;
    int max = 0;

    Map<Integer, Integer> map = new HashMap<>(); // FIRST INDEX: running total -> where it first occurred
    for (int i = 0; i < n; i++) {
      sum += a[i];
      // when subarr starts at 0
      if (sum == k) { // STARTS AT 0: the subarray a[0 .. i]
        max = i + 1;
      }
      // record this prefix even when sum == k: a later
      // position may need it as its left boundary
      if (!map.containsKey(sum)) {
        map.put(sum, i); // RECORD: only the first index of a running total
      }

      int key = sum - k;
      if (map.containsKey(key)) { // MATCH: a[index + 1 .. i] has sum k
        int index = map.get(key);
        max = max(max, i - index);
      }
    }
    return max;
  }

  public static void main(String[] args) {
    int[] a = { 10, 5, 2, 7, 1, 9 };
    System.out.println(maxSubArrLen(a, 15)); // 4
    int[] a1 = { 1, -1, 5, -2, 3 };
    System.out.println(maxSubArrLen(a1, 3)); // 4
    int[] a2 = { -3, -3, 0 };
    System.out.println(maxSubArrLen(a2, -3)); // 2
  }
}