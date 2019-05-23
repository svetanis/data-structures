package com.svetanis.datastructures.heap;

public final class BinaryMaxHeapIterative {

  public static boolean isHeap(int[] a) {
    // Time Complexity: O(n)

    int n = (a.length - 2) / 2;
    for (int i = 0; i < n; i++) {

      // if left child is greater
      if (a[i] < a[2 * i + 1]) {
        return false;
      }

      // if right child is greater
      if (a[i] < a[2 * i + 2]) {
        return false;
      }
    }
    return true;
  }

  private static int left(int index) {
    return 2 * index + 1;
  }

  private static int right(int index) {
    return 2 * index + 2;
  }

  public static void main(String[] args) {
    int[] a = { 90, 15, 10, 7, 12, 2, 7, 3 };
    // to explain (n - 2)/2 boundary
    for (int i = 0; i < a.length; i++) {
      System.out.println(i + ": " + left(i) + " " + right(i));
    }
    System.out.println(isHeap(a));
  }
}

