package com.svetanis.datastructures.array.subarray.zerosum;

import java.util.HashMap;
import java.util.Map;

// Find one contiguous subarray that adds up to zero.
//
// Input: an integer array a. Returns { left, right }, the first and last index of a subarray with
// sum 0, or { -1, -1 } if there is none. Not a LeetCode problem; 525. Contiguous Array and
// 325. Maximum Size Subarray Sum Equals k (k = 0) use the same prefix-sum idea. The span returned
// is the one that ENDS first, because the scan stops at the first repeated running total.
//
// The one idea: sum is the running total of a[0 .. i]. If the same total shows up at two places,
// the elements between them add up to 0. The map remembers the index where each total last
// occurred; a total already in the map at index j means a[j + 1 .. i] sums to zero. A total of 0
// at i means a[0 .. i] itself sums to zero, a span that starts at index 0 and has no earlier
// total to match. The branch for a[i] == 0 returns { i, i }, which the map branch would return too
// (a zero leaves the total unchanged, so the previous index holds it); it is a shortcut.
//
// Time: O(n) -- one pass, constant-time map operations.
// Space: O(n) -- the map holds up to n running totals.
//
// Siblings: ZeroSumSubArrays returns every span; ZeroSumLongestSubArray the length of the longest;
// ZeroSumSubArray only whether one exists.

public final class ZeroSumSubArrayIndices {

  public static int[] subArray(int[] a) {
    int n = a.length;
    int sum = 0; // RUNNING TOTAL: of a[0 .. i]
    Map<Integer, Integer> map = new HashMap<>(); // SEEN: running total -> index where it occurred

    for (int i = 0; i < n; i++) {
      sum += a[i]; // add current element to sum
      // 1. current element is 0
      if (a[i] == 0) {
        return new int[] { i, i }; // FOUND: the element alone
      }

      // 2. sum of elements from 0 to i is 0
      if (sum == 0) {
        return new int[] { 0, i }; // FOUND: from the start
      }

      // 3. sum is already present in hash map
      if (map.get(sum) != null) {
        int left = map.get(sum) + 1;
        int right = i;
        return new int[] { left, right }; // FOUND: between the two equal totals
      }
      // add sum to hash map
      map.put(sum, i); // RECORD: this running total
    }

    return new int[] { -1, -1 }; // NONE: no total ever repeated
  }

  public static void main(String[] args) {
    int[] ar1 = { 4, 2, -3, 1, 6 };
    int[] ar2 = { 4, 2, 0, 1, 6 };
    int[] ar3 = { -3, 2, 3, 1, 6 };
    System.out.println(span(subArray(ar1))); // (1, 3)
    System.out.println(span(subArray(ar2))); // (2, 2)
    System.out.println(span(subArray(ar3))); // (-1, -1)
  }

  private static String span(int[] indices) {
    return "(" + indices[0] + ", " + indices[1] + ")";
  }

}
