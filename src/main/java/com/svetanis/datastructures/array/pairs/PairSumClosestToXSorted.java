package com.svetanis.datastructures.array.pairs;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;
import static java.util.Arrays.sort;

import com.svetanis.java.base.Pair;

public final class PairSumClosestToXSorted {

  public static Pair<Integer, Integer> pair(int[] a, int x) {
    // Time Complexity: O(n)

    int n = a.length;
    int first = -1;
    int second = -1;
    int left = 0;
    int right = n - 1;
    int diff = MAX_VALUE;

    sort(a);

    while (right > left) {
      int sum = a[left] + a[right];
      if (abs(sum - x) < diff) {
        first = left;
        second = right;
        diff = abs(sum - x);
      }

      if (sum > x) {
        right--;
      } else {
        left++;
      }
    }
    return Pair.build(a[first], a[second]);
  }

  public static void main(String[] args) {
    int[] a = { 10, 22, 28, 29, 30, 40 };
    System.out.println(pair(a, 54));
  }
}