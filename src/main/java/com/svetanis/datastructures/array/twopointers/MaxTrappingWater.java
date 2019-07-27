package com.svetanis.datastructures.array.twopointers;

import static java.lang.Math.max;

// Suppose you are given an array containing non-negative numbers 
// representing heights of a set of buildings. 
// Now, because of differences in heights of buildings water can be trapped between them. 
// Find the two buildings that will trap the most amount of water. 
// Write a function that will return the maximum volume of water 
// that will be trapped between these two buildings.

public final class MaxTrappingWater {

  public static int maxWater(int[] a) {
    int left = 0;
    int right = a.length - 1;
    int max = 0;
    int area = 0;
    while (left < right) {
      int width = right - left;
      if (a[left] < a[right]) {
        area = width * a[left];
        left++;
      } else {
        area = width * a[right];
        right--;
      }
      max = max(max, area);
    }
    return max;
  }

  public static void main(String[] args) {
    int[] a1 = { 2, 0, 2 };
    System.out.println(maxWater(a1));

    int[] a2 = { 3, 0, 0, 2, 0, 4 };
    System.out.println(maxWater(a2));

    int[] a3 = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
    System.out.println(maxWater(a3));

  }
}
