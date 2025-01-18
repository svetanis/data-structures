package com.svetanis.datastructures.array.subarray;

// 918. Maximum Sum Circular Subarray

public final class MaxSumCircularSubArray918 {
  // Time Complexity: O(n)
  // Space Complexity: O(1)

  public static int maxCircularSum(int[] a) {
    int max = a[0];
    int min = a[0];
    int total = a[0];
    int currMin = a[0];
    int currMax = a[0];
    for (int i = 1; i < a.length; i++) {
      total += a[i];

      currMax = a[i] + Math.max(currMax, 0);
      max = Math.max(max, currMax);

      currMin = a[i] + Math.min(currMin, 0);
      min = Math.min(min, currMin);
    }
    int wrap = total - min;
    return max > 0 ? Math.max(max, wrap) : max;
  }

  public static void main(String[] args) {
    int[] a1 = { 1, -2, 3, -2 };
    System.out.println(maxCircularSum(a1)); // 3

    int[] a2 = { 5, -3, 5 };
    System.out.println(maxCircularSum(a2)); // 10

    int[] a3 = { -3, -2, -3 };
    System.out.println(maxCircularSum(a3)); // -2
  }
}