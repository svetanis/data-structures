package com.svetanis.datastructures.array;

import static java.lang.Math.max;

// Given n non-negative integers representing an elevation map where the width of each bar is 1, 
// compute how much water it is able to trap after raining.

public final class TrappingWaterSpaceEfficient {

  public static int trapWater(int[] a) {
    // Time Complexity: O(n)

    int n = a.length;
    int total = 0;
    int leftMax = 0;
    int rightMax = 0;
    int low = 0;
    int high = n - 1;
    while (low < high) {
      if (a[low] < a[high]) {
        leftMax = max(leftMax, a[low]);
        total += leftMax - a[low];
        low++;
      } else {
        rightMax = max(rightMax, a[high]);
        total += rightMax - a[high];
        high--;
      }
    }
    return total;
  }

  public static void main(String[] args) {
    int[] a1 = { 2, 0, 2 };
    System.out.println(trapWater(a1));

    int[] a2 = { 3, 0, 0, 2, 0, 4 };
    System.out.println(trapWater(a2));

    int[] a3 = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
    System.out.println(trapWater(a3));
  }
}