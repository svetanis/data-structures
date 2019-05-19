package com.svetanis.datastructures.array.subarray;

import static com.google.common.collect.Maps.newHashMap;
import static java.lang.Math.max;

import java.util.Map;

// find the length of the longest sub-array having sum equal to the given value k.

public final class LongestSubArrLenGivenSum {

  public static int maxSubArrLen(int[] a, int k) {
    // Time complexity: O(n)
    // Space complexity: O(n)

    int n = a.length;
    int sum = 0;
    int max = 0;

    Map<Integer, Integer> map = newHashMap();
    for (int i = 0; i < n; i++) {
      sum += a[i];
      // when subarr starts at 0
      if (sum == k) {
        max = i + 1;
      } else if (!map.containsKey(sum)) {
        map.put(sum, i);
      }

      int key = sum - k;
      if (map.containsKey(key)) {
        int index = map.get(key);
        max = max(max, i - index);
      }
    }
    return max;
  }

  public static void main(String[] args) {
    int[] a = { 10, 5, 2, 7, 1, 9 };
    System.out.println(maxSubArrLen(a, 15));
  }
}