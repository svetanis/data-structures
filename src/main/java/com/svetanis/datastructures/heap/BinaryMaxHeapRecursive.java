package com.svetanis.datastructures.heap;

public final class BinaryMaxHeapRecursive {

  public static boolean isHeap(int[] a, int i) {
    // Time Complexity: O(n)

    int n = a.length;
    // leaf node
    if (i >= (n - 2) / 2) {
      return true;
    }

    boolean one = a[i] >= a[2 * i + 1];
    boolean two = a[i] >= a[2 * i + 2];
    boolean left = isHeap(a, 2 * i + 1);
    boolean right = isHeap(a, 2 * i + 2);
    return one && two && left && right;
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
    System.out.println(isHeap(a, 0));
  }
}
