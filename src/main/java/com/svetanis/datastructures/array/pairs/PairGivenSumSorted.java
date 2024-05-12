package com.svetanis.datastructures.array.pairs;

import static java.util.Arrays.sort;

import com.svetanis.java.base.Pair;

// given an array of distinct integers
// and a number k, find whether or not
// there exist two elements in array
// whose sum is exactly k

public final class PairGivenSumSorted {

  public static Pair<Integer, Integer> pair(int[] a, int k) {
    // time complexity: O(n log n)

    int n = a.length;
    sort(a);
    return pair(a, 0, n - 1, k);
  }

  public static Pair<Integer, Integer> pair(int[] a, int left, int right, int k) {
    while (left < right) {
      if (a[left] + a[right] == k) {
        return Pair.build(a[left], a[right]);
      } else if (a[left] + a[right] < k) {
        left++;
      } else { // a[left] + a[right] > k
        right--;
      }
    }
    return Pair.build(-1, -1);
  }

  public static void main(String[] args) {
    int[] a = { 1, 4, 45, 6, 10, -8 };
    System.out.println(pair(a, 16));
  }
}