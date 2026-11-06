package com.svetanis.datastructures.array;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Arrays;

// Maximum and minimum of an array using the fewest comparisons
//
// Input: a -- an array of at least one int. Returns { min, max } of the array. (No LeetCode
// statement.)
//
// The one idea: split the array in two halves, find { min, max } of each half the same way, and
// combine: the overall minimum is the smaller of the two minimums and the overall maximum the
// larger of the two maximums (2 comparisons). One element is its own min and max with no
// comparison; two elements need 1. This is the shape of a knockout tournament, and it needs
// about 3n / 2 comparisons -- the same count as taking elements in pairs.
//
// Time: O(n) -- T(n) = 2 T(n / 2) + 2 comparisons, which is about 3n / 2 in all.
// Space: O(log n) -- the recursion is as deep as the halving, log2 n levels.
//
// Siblings: MinMaxPairCompare reaches the same count with a loop over pairs and O(1) space;
// MinMaxPairLinear compares every element with both running values, about 2n comparisons.

public final class MinMaxPairTournament {

  public static int[] pair(int[] a) {
    int n = a.length;
    int left = 0;
    int right = n - 1;
    return pair(a, left, right);
  }

  private static int[] pair(int[] a, int low, int high) {

    if (low == high) {
      return new int[] { a[low], a[low] }; // BASE: one element is both ends
    }

    if (high == low + 1) {
      int max = (a[low] > a[high]) ? a[low] : a[high]; // BASE: two elements, one comparison
      int min = (a[low] > a[high]) ? a[high] : a[low];
      return new int[] { min, max };
    }

    int mid = (low + high) / 2;
    int[] left = pair(a, low, mid); // SPLIT: each half answers { min, max }
    int[] right = pair(a, mid + 1, high);

    int min = min(left[0], right[0]); // COMBINE: smaller of the two minimums
    int max = max(left[1], right[1]); // COMBINE: larger of the two maximums

    return new int[] { min, max };
  }

  public static void main(String[] args) {
    int[] a = { 1, 30, 40, 50, 60, 70, 23, 20 };
    System.out.println(Arrays.toString(pair(a))); // [1, 70]
  }
}