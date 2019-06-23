package com.svetanis.datastructures.array.segregate;

import static com.svetanis.java.base.utils.Print.print;
import static com.svetanis.java.base.utils.Swap.swap;

public final class SegregatePosAndNegInPlaceOrderMatters {

  public static void segregate(int[] a) {
    // Time complexity: O(n)

    int n = a.length;
    int neg = 0;
    // count negatives
    for (int val : a) {
      if (val < 0) {
        neg++;
      }
    }

    // move negatives to front
    int i = 0;
    int j = i + 1;
    while (i < neg) {
      if (a[i] < 0) {
        i++;
        j = i + 1;
      } else if (a[i] > 0 && j < n) {
        swap(a, i, j);
        j++;
      }
    }
  }

  public static void main(String[] args) {
    int[] a = { -12, 11, -13, -5, 6, -7, 5, -3, -6 };
    segregate(a);
    print(a);
  }
}
