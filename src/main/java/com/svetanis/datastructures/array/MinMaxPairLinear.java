package com.svetanis.datastructures.array;

import static java.lang.Math.max;
import static java.lang.Math.min;

import com.svetanis.java.base.Pair;

// Maximum and minimum of an array using minimum number of comparisons

public final class MinMaxPairLinear {

  public static Pair<Integer, Integer> pair(int[] a) {
    // Time complexity: O(n)
    // num of comparisons:
    // 1 + 2*(n - 2) in worst case
    // 1 + n - 2 in best case

    int n = a.length;

    if (n == 1) {
      return Pair.build(a[0], a[0]);
    }

    int max = (a[0] > a[1]) ? a[0] : a[1];
    int min = (a[0] > a[1]) ? a[1] : a[0];

    for (int i = 2; i < n; ++i) {
      min = min(min, a[i]);
      max = max(max, a[i]);
    }
    return Pair.build(min, max);
  }

  public static void main(String[] args) {
    int[] a = { 1, 30, 40, 50, 60, 70, 23, 20 };
    System.out.println(pair(a));
  }
}