package com.svetanis.datastructures.array.triplet;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;
import static java.util.Arrays.sort;

public final class TripletClosestToGivenSum {

  public static int triplet(int[] a, int k) {
    // time complexity: O(n^2)
    int n = a.length;
    sort(a);
    int result = k;
    int min = MAX_VALUE;
    
    for (int i = 0; i < n - 2; ++i) {
      int left = i + 1;
      int right = n - 1;
      while (left < right) {
        int sum = a[i] + a[left] + a[right];
        int diff = abs(k - sum);
        if (diff == 0) {
          return sum;
        }
        if (diff < min) {
          min = diff;
          result = sum;
        }
        if (sum <= k) {
          left++;
        } else {
          right--;
        }
      }
    }
    return result;
  }

  public static void main(String[] args) {
    int[] a = { -1, 2, 1, -4 };
    System.out.println(triplet(a, 1));
  }
}