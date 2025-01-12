package com.svetanis.datastructures.array.rotate;

import static com.svetanis.java.base.utils.Print.print;

// 189. Rotate Array

// given an integer array,
// rotate the array to the
// right by k steps, where
// k is non-negative

public final class RotateArray189 {
  // Time Complexity: O(n)
  // Space Complexity: O(1)

  public static void rotate(int[] a, int k) {
    int n = a.length;
    k %= n;
    reverse(a, 0, n - 1);
    reverse(a, 0, k - 1);
    reverse(a, k, n - 1);
  }

  private static void reverse(int[] a, int start, int end) {
    int left = start;
    int right = end;
    while (left < right) {
      int temp = a[left];
      a[left] = a[right];
      a[right] = temp;
      left++;
      right--;
    }
  }

  public static void main(String[] args) {
    int[] a = { 1, 2, 3, 4, 5, 6, 7 };
    rotate(a, 3);
    print(a); // 5,6,7,1,2,3,4
    int[] a1 = { -1, -100, 3, 99 };
    rotate(a1, 2);
    print(a1); // 3,99,-1,-100
  }
}