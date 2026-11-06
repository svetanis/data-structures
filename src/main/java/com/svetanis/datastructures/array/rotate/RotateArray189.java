package com.svetanis.datastructures.array.rotate;

import java.util.Arrays;

// 189. Rotate Array
//
// Input: an integer array a and k, a non-negative number of steps. Rotates a to the right by k
// steps, in place: the last k numbers move to the front. On LeetCode: 1 <= a.length <= 10^5,
// -2^31 <= a[i] <= 2^31 - 1, 0 <= k <= 10^5.
//
// The one idea: reverse the whole array and the last k numbers have come to the front, but each
// group is backwards. Reverse the first k numbers and then the rest to put each group back in its
// original order. k % n comes first, because rotating by n is rotating by 0. When that is 0,
// reverse(a, 0, -1) does nothing and the two later reversals undo the first one.
//
// Time: O(n) -- three reversals, each touches every number it covers once.
// Space: O(1) -- reversing swaps in place.

public final class RotateArray189 {

  public static void rotate(int[] a, int k) {
    int n = a.length;
    k %= n; // SHORTEN: a full turn changes nothing
    reverse(a, 0, n - 1); // REVERSE ALL: the last k numbers are now first, backwards
    reverse(a, 0, k - 1); // FIX FRONT: the k numbers that came from the back
    reverse(a, k, n - 1); // FIX REST: the other n - k numbers
  }

  private static void reverse(int[] a, int start, int end) {
    int left = start;
    int right = end;
    while (left < right) {
      int temp = a[left]; // SWAP: the two ends, then move inward
      a[left] = a[right];
      a[right] = temp;
      left++;
      right--;
    }
  }

  public static void main(String[] args) {
    int[] a = { 1, 2, 3, 4, 5, 6, 7 };
    rotate(a, 3);
    System.out.println(Arrays.toString(a)); // [5, 6, 7, 1, 2, 3, 4]
    int[] a1 = { -1, -100, 3, 99 };
    rotate(a1, 2);
    System.out.println(Arrays.toString(a1)); // [3, 99, -1, -100]
  }
}