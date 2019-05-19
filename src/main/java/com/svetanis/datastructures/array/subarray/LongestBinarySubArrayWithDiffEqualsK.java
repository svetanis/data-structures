package com.svetanis.datastructures.array.subarray;

import static com.google.common.collect.Maps.newHashMap;
import static java.lang.Math.max;

import java.util.Map;

// find the length of the longest subarray having  
// difference in the count of 1’s and 0’s equal to k.

// Replace all the 0’s in the arr[] with -1 and then 
// find the longest subarray of ‘arr’ having sum equal to ‘k’.

public final class LongestBinarySubArrayWithDiffEqualsK {

  public static int len(int[] a, int k) {
    // Time complexity: O(n)
    // Space complexity: O(n)

    int n = a.length;
    int sum = 0;
    int max = 0;

    Map<Integer, Integer> map = newHashMap();
    for (int i = 0; i < n; i++) {
      sum += (a[i] == 0) ? -1 : a[i];
      if (sum == k) {
        max = i + 1;
      }
      if (!map.containsKey(sum)) {
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
    int[] a = { 0, 1, 1, 0, 1 };
    System.out.println(len(a, 2));
  }
}