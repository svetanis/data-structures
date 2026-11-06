package com.svetanis.datastructures.array.subarray.zerosum;

import static java.lang.Math.max;

import java.util.HashMap;
import java.util.Map;

// Length of the longest contiguous subarray whose elements add up to zero.
//
// Input: an integer array a. Returns the length of the longest subarray with sum 0, or 0 if there
// is none. Not a LeetCode problem; it is 325. Maximum Size Subarray Sum Equals k (a premium
// problem) with k = 0, and 525. Contiguous Array is the same idea on 0s and 1s.
//
// The one idea: sum is the running total of a[0 .. i]. If the same total shows up at two places,
// the elements between them add up to 0. So the map keeps, for each running total, the FIRST index
// where it occurred, and at every position a total that is already in the map gives a zero-sum
// subarray from just after that first index up to i; the earliest left end is the longest. A total
// of 0 at i means a[0 .. i] itself sums to zero, a subarray that starts at index 0 and has no
// earlier total to match.
//
// Time: O(n) -- one pass, constant-time map operations.
// Space: O(n) -- the map holds up to n running totals.
//
// Siblings: ZeroSumSubArray answers only yes or no; ZeroSumSubArrayIndices returns one span;
// ZeroSumSubArrays returns every span.

public final class ZeroSumLongestSubArray {

  public static int zeroSum(int[] a) {
    int n = a.length;
    int sum = 0;
    int max = 0;
    Map<Integer, Integer> map = new HashMap<>(); // FIRST INDEX: running total -> where it first occurred
    for (int i = 0; i < n; i++) {
      sum += a[i];
      if (a[i] == 0 && max == 0) { // ZERO ELEMENT: a one-element span
        max = 1;
      }
      if (sum == 0) { // STARTS AT 0: the subarray a[0 .. i]
        max = i + 1;
      }
      // if this sum already present,
      // elements in between sum up to 0
      if (map.containsKey(sum)) {
        int prev = map.get(sum);
        max = max(max, i - prev); // MATCH: a[prev + 1 .. i] sums to 0
      } else {
        map.put(sum, i); // RECORD: only the first index of a running total
      }
    }
    return max;
  }

  public static void main(String[] args) {
    int[] arr1 = { 15, -2, 2, -8, 1, 7, 10, 23 };
    System.out.println(zeroSum(arr1)); // 5

    int[] arr2 = { 1, 2, 3 };
    System.out.println(zeroSum(arr2)); // 0

    int[] arr3 = { 1, 0, 3 };
    System.out.println(zeroSum(arr3)); // 1
  }
}
