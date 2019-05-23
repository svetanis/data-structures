package com.svetanis.datastructures.heap;

import static com.svetanis.java.base.utils.Print.print;
import static com.svetanis.java.base.utils.Swap.swap;

public final class ConvertMinHeapToMaxHeap {

  public static void convert(int[] a) {
    int n = (a.length - 2) / 2;
    for (int i = n; i >= 0; i--) {
      maxHeapify(a, i, n);
    }
  }

  private static void maxHeapify(int[] a, int i, int n) {
    int left = 2 * i + 1;
    int right = 2 * i + 2;
    int index = i;

    if (left < n && a[left] > a[i]) {
      index = left;
    }

    if (right < n && a[right] > a[index]) {
      index = right;
    }

    if (index != i) {
      swap(a, i, index);
      maxHeapify(a, index, n);
    }
  }

  public static void main(String[] args) {
    int[] a = { 3, 5, 9, 6, 8, 20, 10, 12, 18, 9 };
    print(a);
    convert(a);
    print(a);
  }
}