package com.svetanis.datastructures.array.alternate;

import static com.svetanis.datastructures.array.segregate.SegregatePosAndNegInPlaceNoOrder.segregate;
import static com.svetanis.java.base.utils.Print.print;
import static com.svetanis.java.base.utils.Swap.swap;

// Given an array of positive and negative numbers, 
// arrange them in an alternate fashion such that 
// every positive number is followed by negative and vice-versa. 
// Order of elements in output doesn’t matter. 
// Extra positive or negative elements should be moved to end.

public final class AlternateNegPosInPlaceNoOrder {

  public static void alternate(int[] a) {
    // Time Complexity: O(n)
    
    int n = a.length;

    // shift all negative values to the end
    // neg has index of leftmost
    // negative element
    int neg = segregate(a);
    if (neg == 0 || neg == n) {
      return;
    }

    // swap next positive element
    // at even position from
    // next negative element
    int pos = 0;
    while (pos < n && neg < n) {
      swap(a, pos, neg);
      neg++;
      pos += 2;
    }
  }

  public static void main(String[] args) {
    int[] a1 = { -2, 3, 4, -1 };
    alternate(a1);
    print(a1);

    int[] a2 = { -2, 3, 1 };
    alternate(a2);
    print(a2);

    int[] a3 = { -5, 3, 4, 5, -6, -2, 8, 9, -1, -4 };
    alternate(a3);
    print(a3);
  }
}
