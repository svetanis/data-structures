package com.svetanis.datastructures.array.pairs;

import static java.lang.Math.abs;
import static java.util.Arrays.sort;

// given two arrays, form max number of pairs
// one from a1[] and second from a2[] such that
// an element from each array is used at most once
// and the abs difference <= to a given number K

public final class MaximizeUniquePairsTwoArrays {
	// Time Complexity: O(n log n)

  public static int count(int[] a, int[] b, int k) {
    sort(a);
    sort(b);
    int n = a.length;
    int count = 0;

    for (int i = 0, j = 0; i < n && j < n;) {
      if (abs(a[i] - b[j]) <= k) {
        count++;
        i++;
        j++;
      } else if (a[i] > b[j]) {
        j++;
      } else {
        i++;
      }
    }
    return count;
  }

  public static void main(String[] args) {
    int[] a = { 3, 4, 5, 2, 1 };
    int[] b = { 6, 5, 4, 7, 15 };
    System.out.println(count(a, b, 3));
    
    int[] c = { 10, 15, 20 };
    int[] d = { 17, 12, 24 };
    System.out.println(count(c, d, 3));
  }
}
