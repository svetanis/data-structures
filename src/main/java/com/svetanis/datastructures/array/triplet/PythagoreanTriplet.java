package com.svetanis.datastructures.array.triplet;

import static java.util.Arrays.sort;

// given an array of integers,
// check if there is a triplet 
// such that a^2 + b^2 == c^2

public final class PythagoreanTriplet {
    // Time complexity: O(n^2)

  public static boolean isPythagorean(int[] a) {
    int[] sqr = square(a);
    sort(sqr);
    return isPythagoreanUtil(sqr);
  }
  
  private static boolean isPythagoreanUtil(int[] a) {

    int n = a.length;

    for (int i = 0; i < n - 2; i++) {
      int left = i + 1;
      int right = n - 1;
      while (left < right) {
        boolean one = a[left] + a[right] == a[i];
        boolean two = a[i] + a[right] == a[left];
        boolean three = a[left] + a[i] == a[right]; 
        if (one || two || three) {
          return true;
        } else if (a[left] + a[right] < a[i]) {
          left++;
        } else {
          right--;
        }
      }
    }
    return false;
  }

  private static int[] square(int[] a) {
    int n = a.length;
    int[] square = new int[n];
    for (int i = 0; i < a.length; i++) {
      square[i] = a[i] * a[i];
    }
    return square;
  }
  
  public static void main(String[] args) {
    int[] a1 = { 3, 1, 4, 6, 5 };
    System.out.println(isPythagorean(a1));

    int[] a2 = { 10, 4, 6, 12, 5 };
    System.out.println(isPythagorean(a2));
  }
}