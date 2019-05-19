package com.svetanis.datastructures.array.pairs;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;

import com.svetanis.java.base.Pair;

// Given two sorted arrays and a number x, 
// find the pair whose sum is closest to x 
// and the pair has an element from each array. 

public final class PairSumClosestToXTwoSorted {

  public static Pair<Integer, Integer> pair(int[] a1, int[] a2, int x) {
    // Time Complexity: O(n)
    // Space Complexity: O(1)

    int n = a1.length;
    int m = a2.length;
    int left = 0;
    int right = m - 1;
    int first = -1;
    int second = -1;
    int diff = MAX_VALUE;

    while (left < n && right >= 0) {
      int sum = a1[left] + a2[right];
      if (abs(sum - x) < diff) {
        diff = abs(sum - x);
        first = left;
        second = right;
      } else if (sum > x) {
        right--;
      } else {
        left++;
      }
    }
    return Pair.build(a1[first], a2[second]);
  }

  public static void main(String[] args) {
    int[] a1 = { 1, 4, 5, 7 };
    int[] a2 = { 10, 20, 30, 40 };
    System.out.println(pair(a1, a2, 32));

    int[] a3 = { 1, 4, 5, 7 };
    int[] a4 = { 10, 20, 30, 40 };
    System.out.println(pair(a3, a4, 50));
  }
}