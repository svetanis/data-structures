package com.svetanis.datastructures.array.triplet;

import static java.util.Arrays.sort;

import com.svetanis.java.base.utils.Triplet;

// fix the first element one by one and
// find the other two elements
// to find the other two elements, start two index variables
// from two corners of the array and move them toward each other

public final class TripletGivenSumSorted {

  public static Triplet<Integer, Integer, Integer> triplet(int[] a, int k) {
    // time complexity: O(n^2)
    int n = a.length;
    sort(a);
    for (int i = 0; i < n - 2; ++i) {
      int left = i + 1;
      int right = n - 1;
      while (left < right) {
        int sum = a[i] + a[left] + a[right];
        if (sum == k) {
          return Triplet.build(a[left], a[i], a[right]);
        } else if (sum < k) {
          left++;
        } else {
          right--;
        }
      }
    }
    return Triplet.build(-1, -1, -1);
  }

  public static void main(String[] args) {
    int[] a = { 1, 4, 45, 6, 10, 8 };
    System.out.println(triplet(a, 22));
  }
}