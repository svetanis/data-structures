package com.svetanis.datastructures.array.subarray.zerosum;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import com.svetanis.java.base.Pair;

public final class ZeroSumSubArrayIndices {

  public static Pair<Integer, Integer> subArray(int[] a) {
    // Time complexity: O(n)

    int n = a.length;
    int sum = 0; // sum of elements
    Map<Integer, Integer> map = newHashMap();

    for (int i = 0; i < n; i++) {
      sum += a[i]; // add current element to sum
      // 1. current element is 0
      if (a[i] == 0) {
        return Pair.build(i, i);
      }

      // 2. sum of elements from 0 to i is 0
      if (sum == 0) {
        return Pair.build(0, i);
      }

      // 3. sum is already present in hash map
      if (map.get(sum) != null) {
        int left = map.get(sum) + 1;
        int right = i;
        return Pair.build(left, right);
      }
      // add sum to hash map
      map.put(sum, i);
    }

    return Pair.build(-1, -1);
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
