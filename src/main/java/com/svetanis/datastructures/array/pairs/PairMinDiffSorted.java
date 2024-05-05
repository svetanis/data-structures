package com.svetanis.datastructures.array.pairs;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;
import static java.util.Arrays.sort;

import com.svetanis.java.base.Pair;

// given unsorted array,
// find min difference
// between any pair
public final class PairMinDiffSorted {

  public static Pair<Integer, Integer> minDiff(int[] a) {
    // Time complexity: O(n log n)

    int n = a.length;
    int min = MAX_VALUE;
    int first = -1;
    int second = -1;

    // sort array
    sort(a);

    // compare all adjacent pairs in sorted array
    // keep track of mininum difference
    for (int i = 0; i < n - 1; i++) {
      int diff = abs(a[i] - a[i + 1]);
      if (diff < min) {
        min = diff;
        first = i;
        second = i + 1;
      }
    }
    return Pair.build(a[first], a[second]);
  }

  public static void main(String[] args) {
    int[] a1 = { 1, 5, 3, 19, 18, 25 };
    System.out.println(minDiff(a1));

    int[] a2 = { 30, 5, 20, 9 };
    System.out.println(minDiff(a2));

    int[] a3 = { 1, 19, -4, 31, 38, 25, 100 };
    System.out.println(minDiff(a3));
  }
}