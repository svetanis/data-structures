package com.svetanis.datastructures.array.segregate;

import static com.svetanis.java.base.utils.Print.print;
import static com.svetanis.java.base.utils.Swap.swap;

public final class SegregateZerosOnesTwos {

  public static void segregate(int[] a) {
    // O(n)
    int n = a.length;
    int low = 0;
    int mid = 0;
    int high = n - 1;

    while (mid <= high && mid < n) {
      if (a[mid] == 0) {
        swap(a, low, mid);
        low++;
        mid++;
      } else if (a[mid] == 1) {
        mid++;
      } else {
        swap(a, mid, high);
        high--;
      }
    }
  }

  public static void main(String[] args) {
    int[] a = { 0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1 };
    segregate(a);
    print(a);
  }
}