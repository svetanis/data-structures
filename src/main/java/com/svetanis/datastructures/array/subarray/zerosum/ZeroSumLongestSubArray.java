package com.svetanis.datastructures.array.subarray.zerosum;

import static com.google.common.collect.Maps.newHashMap;
import static java.lang.Math.max;

import java.util.Map;

public final class ZeroSumLongestSubArray {

  public static int zeroSum(int[] a) {
    // Time Complexity: O(n)
    // Space Complexity: O(n)

    int n = a.length;
    int sum = 0;
    int max = 0;
    Map<Integer, Integer> map = newHashMap();
    for (int i = 0; i < n; i++) {
      sum += a[i];
      if (a[i] == 0 && max == 0) {
        max = 1;
      }
      if (sum == 0) {
        max = i + 1;
      }
      // if this sum already present,
      // elements in between sum up to 0
      if (map.containsKey(sum)) {
        int prev = map.get(sum);
        max = max(max, i - prev);
      } else {
        map.put(sum, i);
      }
    }
    return max;
  }

  public static void main(String[] args) {
    int[] arr1 = { 15, -2, 2, -8, 1, 7, 10, 23 };
    System.out.println(zeroSum(arr1));

    int[] arr2 = { 1, 2, 3 };
    System.out.println(zeroSum(arr2));

    int[] arr3 = { 1, 0, 3 };
    System.out.println(zeroSum(arr3));
  }
}
