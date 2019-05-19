package com.svetanis.datastructures.array.subarray;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

public final class SubArrayZeroSum {

  public static boolean subArray(int[] a) {
    // Time complexity: O(n)

    Map<Integer, Integer> map = newHashMap();
    int n = a.length;
    int sum = 0; // sum of elements
    for (int i = 0; i < n; i++) {
      sum += a[i]; // add current element to sum
      // return true if
      // 1. current element is 0
      // 2. sum of elements from 0 to i is 0
      // 3. sum is already present in hash map
      if (a[i] == 0 || sum == 0 || map.get(sum) != null) {
        return true;
      }
      // add sum to hash map
      map.put(sum, i);
    }
    return false;
  }

  public static void main(String[] args) {
    int[] ar1 = { 4, 2, -3, 1, 6 };
    int[] ar2 = { 4, 2, 0, 1, 6 };
    int[] ar3 = { -3, 2, 3, 1, 6 };
    System.out.println(subArray(ar1));
    System.out.println(subArray(ar2));
    System.out.println(subArray(ar3));
  }
}
