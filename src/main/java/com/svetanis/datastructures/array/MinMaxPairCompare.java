package com.svetanis.datastructures.array;

import static java.lang.Math.max;
import static java.lang.Math.min;

import com.svetanis.java.base.Pair;

// Maximum and minimum of an array using minimum number of comparisons

public final class MinMaxPairCompare {

  public static Pair<Integer, Integer> pair(int[] a) {
    // Time complexity: O(n)

    int n = a.length;

    int i;
    int max;
    int min;

    if (n % 2 == 0) {
      min = (a[0] > a[1]) ? a[1] : a[0];
      max = (a[0] > a[1]) ? a[0] : a[1];
      i = 2;
    } else {
      min = a[0];
      max = a[0];
      i = 1;
    }

    // in the while loop, pick elements in pair and
    // compare the pair with max and min so far
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
    return Pair.build(min, max);
  }

  public static void main(String[] args) {
    int[] a = { 1, 30, 40, 50, 60, 70, 23, 20 };
    System.out.println(pair(a));
  }
}