package com.svetanis.datastructures.array.triplet;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;
import static java.util.Arrays.sort;

// given an array of unsorted numbers and a target,
// find a triplet in the array whose sum is as 
// close to the target number as possible
// return the sum of the triplet
// if there are more than one such triplet,
// return the sum of the triplet with the
// smallest sum

public final class TripletClosestToGivenSum {

  public static int triplet(int[] a, int target) {
    // time complexity: O(n^2)
    sort(a);

    int n = a.length;
    int min = MAX_VALUE;
    int result = target;
    
    for (int i = 0; i < n - 2; ++i) {
      int left = i + 1;
      int right = n - 1;
      while (left < right) {
        int sum = a[i] + a[left] + a[right];
        int diff = abs(target - sum);
        if (diff == 0) {
          return sum;
        }
        if (diff < min) {
          min = diff;
          result = sum;
        }
        if (sum <= target) {
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

    int[] a1 = { -2, 0, 1, 2 };
    System.out.println(triplet(a1, 2));

    
    int[] a2 = { -3, -1, 1, 2 };
    System.out.println(triplet(a2, 1));

    int[] a3 = { 1, 0, 1, 1 };
    System.out.println(triplet(a3, 100));
  }
}