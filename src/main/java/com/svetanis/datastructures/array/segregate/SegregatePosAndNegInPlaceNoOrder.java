package com.svetanis.datastructures.array.segregate;

import static com.svetanis.java.base.utils.Print.print;
import static com.svetanis.java.base.utils.Swap.swap;

public final class SegregatePosAndNegInPlaceNoOrder {

  public static int segregate(int[] a) {
    int n = a.length;
    int left = 0;
    int right = n - 1;

    // shift all negative values to the end
    while (left < right) {

      while (a[left] > 0) {
        left++;
      }

      while (a[right] < 0) {
        right--;
      }

      if (left < right) {
        swap(a, left, right);
      }
    }
    return left;
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
  }

}
