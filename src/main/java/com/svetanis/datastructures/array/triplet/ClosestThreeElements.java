package com.svetanis.datastructures.array.triplet;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.max;
import static java.lang.Math.min;

import com.svetanis.java.base.utils.Triplet;

public final class ClosestThreeElements {

  public static Triplet<Integer, Integer, Integer> triplet(int[] a, int[] b, int[] c) {
    // Time complexity: O(n)

    int diff = MAX_VALUE;

    int n = a.length;
    int m = b.length;
    int l = c.length;

    int first = 0;
    int second = 0;
    int third = 0;

    int i = 0;
    int j = 0;
    int k = 0;

    while (i < n && j < m && k < l) {
      int min = min(a[i], min(b[j], c[k]));
      int max = max(a[i], max(b[j], c[k]));

      // if current diff is less
      // than min diff so far
      if (max - min < diff) {
        diff = max - min;
        first = i;
        second = j;
        third = k;
      }

      if (diff == 0) {
        break;
      }

      // increment index of array
      // with smallest value
      if (a[i] == min) {
        i++;
      } else if (b[j] == min) {
        j++;
      } else {
        k++;
      }
    }
    return Triplet.build(a[first], b[second], c[third]);
  }

  public static void main(String[] args) {
    int[] a = { 1, 4, 10 };
    int[] b = { 2, 15, 20 };
    int[] c = { 10, 12 };
    System.out.println(triplet(a, b, c));
  }
}
