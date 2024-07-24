package com.svetanis.datastructures.array.triplet;

import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.max;

// given an array of n integers and 3 integers: x, y and z
// maximize the value of (x * a[i]) + (y * a[j]) + (z * a[k])
// where i <= j <= k

public final class MaximizeProduct {
  // Time Complexity: O(n)
  // Auxiliary Space: O(n)
	
  public static int maximize(int[] a, int x, int y, int z) {
    int n = a.length;
    int[] left = left(a, x);
    int[] right = right(a, z);
    int max = MIN_VALUE;
    for (int i = 0; i < n; i++) {
      int product = left[i] + y * a[i] + right[i];
      max = max(max, product);
    }
    return max;
  }

  private static int[] left(int[] a, int x) {
    int n = a.length;
    int[] left = new int[n];
    left[0] = x * a[0];
    for (int i = 1; i < n; i++) {
      left[i] = max(left[i - 1], x * a[i]);
    }
    return left;
  }

  private static int[] right(int[] a, int z) {
    int n = a.length;
    int[] right = new int[n];
    right[n - 1] = z * a[n - 1];
    for (int i = n - 2; i >= 0; i--) {
      right[i] = max(right[i + 1], z * a[i]);
    }
    return right;
  }

  public static void main(String[] args) {
    int x = 1;
    int y = 2;
    int z = -3;
    int[] a = { -1, -2, -3, -4, -5 };
    System.out.println(maximize(a, x, y, z));
  }
}
