package com.svetanis.datastructures.array.alternate;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;

import com.google.common.collect.ImmutableList;

// Given an array named array of size n, that contains both positive and negative numbers. 
// Rearrange the array elements so that positive and negative numbers appear alternatively in the output. 
// The order in which the positive elements appear should be maintained. 
// Similarly, the order in which the negative elements appear should also be maintained.
// Number of positive and negative integers may not be equal and extra positives or negatives have to appear in the end of the array.

public final class AlternatePosNegExtraSpaceOrderMatters {

  public static ImmutableList<Integer> alternate(int[] a) {
    // Time Complexity: O(n)

    int n = a.length;
    int pos = 0;
    int neg = 0;
    List<Integer> list = newArrayList();
    
    for (int i = 0; i < n; i++) {

      // find next negative element
      while (neg < n && a[neg] >= 0) {
        neg++;
      }

      // find next positive element
      while (pos < n && a[pos] < 0) {
        pos++;
      }

      if (pos == n) {
        list.add(a[neg++]);
      } else if (neg == n) {
        list.add(a[pos++]);
      } else {
        if (i % 2 == 0) {
          list.add(a[pos++]);
        } else {
          list.add(a[neg++]);
        }
      }

    }
    return newList(list);
  }

  public static void main(String[] args) {
    int[] a1 = { -2, 3, 4, -1 };
    print(alternate(a1));

    int[] a2 = { -2, 3, 1 };
    print(alternate(a2));

    int[] a3 = { -5, 3, 4, 5, -6, -2, 8, 9, -1, -4 };
    print(alternate(a3));
  }
}
