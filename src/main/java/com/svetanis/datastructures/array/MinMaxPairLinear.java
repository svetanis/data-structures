package com.svetanis.datastructures.array;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Arrays;

// Maximum and minimum of an array in one pass
//
// Input: a -- an array of at least one int. Returns { min, max } of the array. (No LeetCode
// statement.)
//
// The one idea: keep the smallest and the largest value seen so far, and let every new element
// meet both. The first two elements are compared with each other to start (1 comparison); each
// of the other n - 2 elements is compared with the minimum and with the maximum, 2 comparisons.
//
// Time: O(n) -- one pass; exactly 1 + 2(n - 2) comparisons for n >= 2 (Math.min and Math.max each
// compare every time).
// Space: O(1) -- a minimum and a maximum.
//
// Siblings: MinMaxPairCompare takes the elements in pairs and needs about 3n / 2 comparisons;
// MinMaxPairTournament splits the array in halves recursively, also about 3n / 2.

public final class MinMaxPairLinear {

  public static int[] pair(int[] a) {
    int n = a.length;

    if (n == 1) {
      return new int[] { a[0], a[0] }; // EDGE: one element is both ends
    }

    int max = (a[0] > a[1]) ? a[0] : a[1]; // START: the first two elements, compared once
    int min = (a[0] > a[1]) ? a[1] : a[0];

    for (int i = 2; i < n; ++i) {
      min = min(min, a[i]); // MEET the running minimum
      max = max(max, a[i]); // MEET the running maximum
    }
    return new int[] { min, max }; // RETURN: { min, max }
  }

  public static void main(String[] args) {
    int[] a = { 1, 30, 40, 50, 60, 70, 23, 20 };
    System.out.println(Arrays.toString(pair(a))); // [1, 70]
  }
}