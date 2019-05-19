package com.svetanis.datastructures.array.pairs;

import static java.lang.Math.abs;
import static java.util.Arrays.sort;

import com.svetanis.java.base.Pair;

public final class PairMaxProduct {

  private static int INT_MIN = 0;

  public static Pair<Integer, Integer> pair(int[] a) {
    // Time Complexity: O(n log n)

    int n = a.length;

    if (n < 2) {
      System.out.println("No such pair exists");
      return Pair.build(-1, -1);
    }

    if (n == 2) {
      return Pair.build(a[0], a[1]);
    }

    sort(a);

    int firstMax = INT_MIN;
    int secondMax = INT_MIN;

    int firstMin = INT_MIN;
    int secondMin = INT_MIN;

    for (int i = 0; i < n; i++) {
      if (a[i] > firstMax) {
        secondMax = firstMax;
        firstMax = a[i];
      } else if (a[i] > secondMax) {
        secondMax = a[i];
      }

      if (a[i] < 0 && abs(a[i]) > abs(firstMin)) {
        secondMin = firstMin;
        firstMin = a[i];
      } else if (a[i] < 0 && abs(a[i]) > abs(secondMin)) {
        secondMin = a[i];
      }
    }

    if (firstMax * secondMax > firstMin * secondMin) {
      return Pair.build(firstMax, secondMax);
    } else {
      return Pair.build(firstMin, secondMin);
    }
  }

  public static void main(String[] args) {
    int[] X = { 1, 4, 3, 6, 7, 0 };
    System.out.println(pair(X));
    int[] Y = { -1, -3, -4, 2, 0, -5 };
    System.out.println(pair(Y));
  }
}