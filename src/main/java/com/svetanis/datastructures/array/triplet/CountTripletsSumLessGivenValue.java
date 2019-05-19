package com.svetanis.datastructures.array.triplet;

import static java.util.Arrays.sort;

public final class CountTripletsSumLessGivenValue {

  public static int count(int[] a, int k) {
    // Time complexity: O(n log n)
    int n = a.length;
    int count = 0;
    sort(a);
    for (int i = 0; i < n; i++) {
      int left = i + 1;
      int right = n - 1;
      while (left < right) {
        int sum = a[i] + a[left] + a[right];
        if (sum >= k) {
          right--;
        } else {
          count += right - left;
          left++;
        }
      }
    }
    return count;
  }

  public static void main(String[] args) {
    int[] a = { 5, 1, 3, 4, 7 };
    System.out.println(count(a, 12));
  }
}
