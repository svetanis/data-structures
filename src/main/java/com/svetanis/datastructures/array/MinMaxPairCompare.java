package com.svetanis.datastructures.array;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Arrays;

// Maximum and minimum of an array using the fewest comparisons
//
// Input: a -- an array of at least one int. Returns { min, max } of the array. (No LeetCode
// statement.)
//
// The one idea: take the elements two at a time. Comparing the two with EACH OTHER first (1
// comparison) says which one can only be the new minimum and which only the new maximum, so then
// the smaller meets the running minimum (1 comparison) and the larger meets the running maximum
// (1 comparison): 3 comparisons per 2 elements instead of 4. An odd array starts from its first
// element alone, an even one from its first pair.
//
// Time: O(n) -- one pass; about 3n / 2 comparisons (3(n - 1) / 2 when n is odd, 3n / 2 - 2 when
// n is even).
// Space: O(1) -- a minimum, a maximum and an index.
//
// Siblings: MinMaxPairLinear compares every element with both running values, about 2n
// comparisons; MinMaxPairTournament splits the array in halves recursively and also takes
// about 3n / 2.

public final class MinMaxPairCompare {

  public static int[] pair(int[] a) {
    int n = a.length;

    int i;
    int max;
    int min;

    if (n % 2 == 0) {
      min = (a[0] > a[1]) ? a[1] : a[0]; // START: an even array begins with its first pair
      max = (a[0] > a[1]) ? a[0] : a[1];
      i = 2;
    } else {
      min = a[0]; // START: an odd array begins with its first element alone
      max = a[0];
      i = 1;
    }

    // WALK: pick elements in pairs, compare the pair with itself, then the smaller with min
    // and the larger with max
    while (i < n - 1) {
      if (a[i] > a[i + 1]) {
        max = max(max, a[i]);
        min = min(min, a[i + 1]);
      } else {
        max = max(max, a[i + 1]);
        min = min(min, a[i]);
      }
      i += 2;
    }
    return new int[] { min, max }; // RETURN: { min, max }
  }

  public static void main(String[] args) {
    int[] a = { 1, 30, 40, 50, 60, 70, 23, 20 };
    System.out.println(Arrays.toString(pair(a))); // [1, 70]
  }
}