package com.svetanis.datastructures.array.pairs;

public final class CountPairsGivenSum2SortedBinary {

  public static int count(int[] a1, int[] a2, int k) {
    // Time Complexity: O(n * log m)

    int n1 = a1.length;
    int n2 = a2.length;
    int count = 0;
    for (int i = 0; i < n1; i++) {
      int diff = k - a1[i];
      if (isBinary(a2, 0, n2 - 1, diff)) {
        count++;
      }
    }
    return count;
  }

  public static boolean isBinary(int[] a, int start, int end, int x) {
    // O(log n)

    if (end < start) {
      return false;
    }

    int mid = start + (end - start) / 2;
    if (a[mid] == x) {
      return true;
    } else if (x > a[mid]) {
      return isBinary(a, mid + 1, end, x);
    } else {
      return isBinary(a, start, mid - 1, x);
    }
  }

  public static void main(String[] args) {
    int[] a1 = { 1, 3, 5, 7 };
    int[] a2 = { 2, 3, 5, 8 };
    System.out.println(count(a1, a2, 10));

    int[] a3 = { 1, 2, 3, 4, 5, 7, 11 };
    int[] a4 = { 2, 3, 4, 5, 6, 8, 12 };
    System.out.println(count(a3, a4, 9));

  }
}
