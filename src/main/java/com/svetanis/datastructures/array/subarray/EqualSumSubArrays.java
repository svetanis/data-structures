package com.svetanis.datastructures.array.subarray;

import java.util.Arrays;

// Given an array of integers greater than zero,
// find if it is possible to split it in two subarrays
// (without reordering the elements),
// such that the sum of the two subarrays is the same.
//
// Returns the index where the right part starts (the left part is a[0 .. index - 1], the right
// part a[index .. n - 1]), or -1 when no split gives equal sums. Not a LeetCode problem; the
// closest is 724. Find Pivot Index, where one element is left out of both sums.
//
// The one idea: start with everything on the left (left = the total) and nothing on the right.
// Walk from the end, moving one element at a time from the left side to the right side: right
// grows by a[i], left shrinks by a[i]. After moving a[i], right is the sum of a[i .. n - 1] and
// left the sum of a[0 .. i - 1]. The split is found at the first i where the two are equal. Every
// element is greater than zero, so right only grows and left only shrinks: the two meet at most
// once.
//
// Time: O(n) -- one pass to get the total, one pass to move the elements.
// Space: O(1) -- two sums.

public final class EqualSumSubArrays {

  public static int split(int[] a) {
    int n = a.length;
    int left = 0; // LEFT: starts as the total of the whole array
    for (int num : a) {
      left += num;
    }
    int right = 0; // RIGHT: starts empty
    for (int i = n - 1; i >= 0; i--) {
      right += a[i]; // MOVE: a[i] from the left side to the right side
      left -= a[i];
      if (left == right) { // FOUND: a[0 .. i - 1] and a[i .. n - 1] have equal sums
        return i;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    int[] a = { 1, 2, 3, 4, 5, 5 };
    int index = split(a);
    System.out.println(index); // 4
    System.out.println(Arrays.toString(Arrays.copyOfRange(a, 0, index))); // [1, 2, 3, 4]
    System.out.println(Arrays.toString(Arrays.copyOfRange(a, index, a.length))); // [5, 5]
    int[] a1 = { 1, 2, 3 };
    System.out.println(split(a1)); // 2
    int[] a2 = { 1, 2, 4 };
    System.out.println(split(a2)); // -1
  }
}
