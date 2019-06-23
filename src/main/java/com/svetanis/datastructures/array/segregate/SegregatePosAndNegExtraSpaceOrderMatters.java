package com.svetanis.datastructures.array.segregate;

import static com.svetanis.java.base.utils.Print.print;

public final class SegregatePosAndNegExtraSpaceOrderMatters {

  public static void segregate(int[] a) {
    // Time Complexity: O(n)
    // Aux Space Complexity: O(n)

    int n = a.length;
    int[] temp = new int[n];
    int j = 0;
    for (int i = 0; i < n; i++) {
      if (a[i] >= 0) {
        temp[j++] = a[i];
      }
    }
    if (j == 0 || j == n) {
      return;
    }
    for (int i = 0; i < n; i++) {
      if (a[i] < 0) {
        temp[j++] = a[i];
      }
    }
    for (int i = 0; i < n; i++) {
      a[i] = temp[i];
    }
  }

  public static void main(String[] args) {
    int[] a1 = { -2, 3, 4, -1 };
    segregate(a1);
    print(a1);

    int[] a2 = { -2, 3, 1 };
    segregate(a2);
    print(a2);

    int[] a3 = { -5, 3, 4, 5, -6, -2, 8, 9, -1, -4 };
    segregate(a3);
    print(a3);

    int[] a4 = { 1, -1, -3, -2, 7, 5, 11, 6 };
    segregate(a4);
    print(a4);
  }
}
