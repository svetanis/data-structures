package com.svetanis.datastructures.array.alternate;

import static com.svetanis.java.base.utils.Print.print;
import static com.svetanis.java.base.utils.Swap.swap;

public final class AlternatePosNegInPlaceNoOrderQuickSort {

  public static void alternate(int[] a) {
    // Time complexity: O(n)

    int n = a.length;
    int i = -1;

    // similar to partition process of QuickSort
    // consider 0 as pivot and divide the array
    // around it
    for (int j = 0; j < n; ++j) {
      if (a[j] < 0) {
        i++;
        swap(a, i, j);
      }
    }

    // now all positive nums are at the end and negative
    // at the beginning of array.
    // initialize indexes for starting point of
    // positive and negative numbers to be swapped
    int neg = 0;
    int pos = i + 1;

    // increment the negative index by 2 and positive
    // index by 1, i.e., swap every alternate negative number
    // with next positive number
    while (pos < n && neg < pos && a[neg] < 0) {
      swap(a, neg, pos);
      pos++;
      neg += 2;
    }
  }

  public static void main(String[] args) {
    int[] a = { -1, 2, -3, 4, 5, 6, -7, 8, 9 };
    alternate(a);
    print(a);
  }
}
