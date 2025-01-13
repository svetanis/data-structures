package com.svetanis.datastructures.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 163. Missing Ranges

public final class MissingRanges163 {
  // Time Complexity: O(n)
  // Space Complexity: O(n)

  public static List<List<Integer>> missingRanges(int[] a, int lower, int upper) {
    int n = a.length;
    List<List<Integer>> list = new ArrayList<>();

    if (n == 0) {
      list.add(Arrays.asList(lower, upper));
      return list;
    }
    if (a[0] > lower) {
      list.add(Arrays.asList(lower, a[0] - 1));
    }
    for (int i = 1; i < n; i++) {
      if (a[i] - a[i - 1] > 1) {
        list.add(Arrays.asList(a[i - 1] + 1, a[i] - 1));
      }
    }
    if (a[n - 1] < upper) {
      list.add(Arrays.asList(a[n - 1] + 1, upper));
    }
    return list;
  }

  public static void main(String[] args) {
    int[] a = { 2, 3, 7, 9 };
    // [[1, 1], [4, 6], [8, 8], [10, 10]]
    System.out.println(missingRanges(a, 1, 10));
  }
}