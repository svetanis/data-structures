package com.svetanis.datastructures.array.triplet;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.max;

// given an integer array,
// find a max product of 
// a triplet in array

public class MaxProductTriplet {

  public static int maxProduct(int[] a) {
    // Time Complexity: O(n)
    // Space Complexity: O(1)

    int n = a.length;
    if (n < 3) {
      return -1;
    }

    int firstMax = MIN_VALUE;
    int secondMax = MIN_VALUE;
    int thirdMax = MIN_VALUE;

    int firstMin = MAX_VALUE;
    int secondMin = MAX_VALUE;

    for (int i = 0; i < n; i++) {
      // update max elements
      if (a[i] > firstMax) {
        thirdMax = secondMax;
        secondMax = firstMax;
        firstMax = a[i];
      } else if (a[i] > secondMax) {
        thirdMax = secondMax;
        secondMax = a[i];
      } else if (a[i] > thirdMax) {
        thirdMax = a[i];
      }

      // update min elements
      if (a[i] < firstMin) {
        secondMin = firstMin;
        firstMin = a[i];
      } else if (a[i] < secondMin) {
        secondMin = a[i];
      }
    }

    int max1 = firstMax * secondMax * thirdMax;
    int max2 = firstMin * secondMin * firstMax;
    return max(max1, max2);
  }

  public static void main(String[] args) {
    int[] a1 = { 10, 3, 5, 6, 20 };
    System.out.println(maxProduct(a1));

    int[] a2 = { -10, -3, -5, -6, -20 };
    System.out.println(maxProduct(a2));

    int[] a3 = { 1, -4, 3, -6, 7, 0 };
    System.out.println(maxProduct(a3));

    int[] a4 = { 1, 10, -5, -1, -100 };
    System.out.println(maxProduct(a4));
  }
}