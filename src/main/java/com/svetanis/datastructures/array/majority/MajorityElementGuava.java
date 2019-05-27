package com.svetanis.datastructures.array.majority;

import static com.google.common.primitives.Ints.indexOf;

// sorted array

public final class MajorityElementGuava {

  public static boolean isMajority(int[] a, int x) {
    // Time complexity: O(n)

    int n = a.length;
    int index = indexOf(a, x);
    return index + n / 2 < n && a[index + n / 2] == x;
  }

  public static void main(String[] args) {
    int[] array = { 1, 2, 3, 3, 3, 3, 10 };
    System.out.println(isMajority(array, 3));
  }
}