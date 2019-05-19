package com.svetanis.datastructures.array.pairs;

import static com.svetanis.java.base.utils.Maps.freqMap;

import java.util.Map;

public final class CountPairsGivenSum {

  public static int count(int[] a, int k) {
    // Time Complexity: O(n)

    int n = a.length;
    int count = 0;
    Map<Integer, Integer> map = freqMap(a);
    for (int i = 0; i < n; i++) {
      count += map.get(k - a[i]);
      if (k - a[i] == a[i]) {
        count--;
      }
    }
    return count / 2;
  }

  public static void main(String[] args) {
    int[] a = { 1, 5, 7, -1, 5 };
    System.out.println(count(a, 6));
  }
}