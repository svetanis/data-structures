package com.svetanis.datastructures.array.pairs;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;
import static java.util.Arrays.sort;

import com.svetanis.java.base.Pair;

public final class PairSumClosestToZeroSorted {

  public static Pair<Integer, Integer> pair(int[] a) {
    // time complexity: O(n log n)

    int n = a.length;

    if (n < 2) {
      return Pair.build(-1, -1);
    }

    sort(a);

    int min = MAX_VALUE;
    int left = 0;
    int right = n - 1;
    int first = left;
    int second = right;

    while (left < right) {
      int sum = a[left] + a[right];
      if (abs(sum) < abs(min)) {
        min = sum;
        first = left;
        second = right;
      }
      if (sum < 0) {
        left++;
      } else {
        right--;
      }
    }
    return Pair.build(a[first], a[second]);
  }

  public static void main(String[] args) {
    int[] a = { 1, 60, -10, 70, -80, 85 };
    System.out.println(pair(a));
  }
}