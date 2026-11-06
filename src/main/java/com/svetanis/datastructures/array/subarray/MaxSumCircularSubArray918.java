package com.svetanis.datastructures.array.subarray;

// 918. Maximum Sum Circular Subarray
//
// Input: a circular integer array a (the last element is followed by the first). Returns the
// largest sum of a non-empty subarray of that circle; a subarray may wrap around the end but uses
// each element at most once. On LeetCode: 1 <= a.length <= 3 * 10^4, -3 * 10^4 <= a[i] <= 3 * 10^4.
//
// The one idea: the best subarray either sits inside the array (no wrap), or wraps around. A
// subarray that wraps is everything EXCEPT a middle piece a[i .. j] that does not wrap, so its sum
// is total - (sum of that middle piece), and it is largest when the middle piece is the smallest
// subarray sum. So: max is the best ordinary subarray sum (the usual "extend or restart" scan,
// currMax = a[i] + max(currMax, 0)), min is the smallest, found the same way, and the answer is
// max(max, total - min). One catch: if every number is negative, the smallest piece is the whole
// array and total - min = 0 would stand for an EMPTY subarray, which is not allowed -- but then
// max < 0 and max is the answer. So when max <= 0 return max.
//
// Time: O(n) -- one pass keeps total, the best sum and the smallest sum.
// Space: O(1) -- a few integers.

public final class MaxSumCircularSubArray918 {

  public static int maxCircularSum(int[] a) {
    int max = a[0]; // BEST: largest subarray sum, no wrap
    int min = a[0]; // WORST: smallest subarray sum
    int total = a[0];
    int currMin = a[0];
    int currMax = a[0];
    for (int i = 1; i < a.length; i++) {
      total += a[i];

      currMax = a[i] + Math.max(currMax, 0); // EXTEND or RESTART: the best sum ending at i
      max = Math.max(max, currMax);

      currMin = a[i] + Math.min(currMin, 0); // EXTEND or RESTART: the smallest sum ending at i
      min = Math.min(min, currMin);
    }
    int wrap = total - min; // WRAP: everything except the smallest middle piece
    return max > 0 ? Math.max(max, wrap) : max; // RETURN: all negative -> wrap would be empty
  }

  public static void main(String[] args) {
    int[] a1 = { 1, -2, 3, -2 };
    System.out.println(maxCircularSum(a1)); // 3

    int[] a2 = { 5, -3, 5 };
    System.out.println(maxCircularSum(a2)); // 10

    int[] a3 = { -3, -2, -3 };
    System.out.println(maxCircularSum(a3)); // -2
  }
}