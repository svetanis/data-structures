package com.svetanis.datastructures.array.subarray;

import static java.lang.Math.max;

import java.util.HashMap;
import java.util.Map;

// find the length of the longest subarray having
// difference in the count of 1's and 0's equal to k.
//
// Input: an array a of 0s and 1s, and k. Returns the length of the longest subarray in which
// (number of 1s) - (number of 0s) == k, or 0 if there is none. Not a LeetCode problem; with k = 0
// it is 525. Contiguous Array.
//
// The one idea: replace each 0 by -1. The difference of the counts of 1s and 0s in a subarray is
// then its sum, so the question is the longest subarray with sum k. sum is the running total of
// a[0 .. i]; a subarray a[j + 1 .. i] has sum k exactly when the running total at j equals
// sum - k. So the map keeps, for each running total, the FIRST index where it occurred (the
// earliest left end gives the longest subarray), and each position looks up sum - k. A subarray
// that starts at index 0 has no running total before it, so it is handled by the sum == k check.
//
// Time: O(n) -- one pass, constant-time map operations.
// Space: O(n) -- the map holds up to n running totals.
//
// Siblings: LongestSubArrLenGivenSum is the same loop on the raw numbers instead of 1 / -1.

public final class LongestBinarySubArrayWithDiffEqualsK {

  public static int len(int[] a, int k) {
    int n = a.length;
    int sum = 0;
    int max = 0;

    Map<Integer, Integer> map = new HashMap<>(); // FIRST INDEX: running total -> where it first occurred
    for (int i = 0; i < n; i++) {
      sum += (a[i] == 0) ? -1 : a[i]; // MAP: a 0 counts as -1
      if (sum == k) { // STARTS AT 0: the subarray a[0 .. i]
        max = i + 1;
      }
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
    int[] a = { 0, 1, 1, 0, 1 };
    System.out.println(len(a, 2)); // 4
    int[] a1 = { 0, 1, 0 };
    System.out.println(len(a1, 0)); // 2
    int[] a2 = { 1, 1, 1 };
    System.out.println(len(a2, 0)); // 0
  }
}