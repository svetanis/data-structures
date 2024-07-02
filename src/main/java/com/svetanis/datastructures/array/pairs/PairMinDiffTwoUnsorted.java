package com.svetanis.datastructures.array.pairs;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.util.Arrays.sort;

// given two unsorted arrays, 
// compute the pair of values
// (one value in each array)
// with the smallest difference

public final class PairMinDiffTwoUnsorted {

  public static int minDiff(int[] a, int[] b) {
    // Time Complexity: O(m log m + n log n)

    int n = a.length;
    int m = b.length;

    sort(a);
    sort(b);

    int i = 0;
    int j = 0;
    int min = MAX_VALUE;

    while (i < n && j < m) {
      int diff = abs(a[i] - b[j]);
      min = min(min, diff);
      if (a[i] < b[j]) {
        i++;
      } else {
        j++;
      }
    }
    return min;
  }

  public static void main(String[] args) {
    int[] a = { 1, 3, 15, 11, 2 };
    int[] b = { 23, 127, 235, 19, 8 };
    System.out.println(minDiff(a, b));

    int[] c = { 10, 5, 40 };
    int[] d = { 50, 90, 80 };
    System.out.println(minDiff(c, d));

    int[] e = { 1, 2, 11, 5 };
    int[] f = { 4, 12, 19, 23, 127, 235 };
    System.out.println(minDiff(e, f));
  }
}
