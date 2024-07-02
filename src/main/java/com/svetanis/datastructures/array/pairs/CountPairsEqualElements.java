package com.svetanis.datastructures.array.pairs;

import static com.svetanis.java.base.utils.Maps.freqMap;

import java.util.Map;

// given array of n elements
// count total number of indices (i, j) 
// such that a[i] == a[j] and i != j

// 'n choose 2' = n*(n - 1)
public final class CountPairsEqualElements {

  public static int count(int[] a) {
    // Time Complexity: O(n)

    Map<Integer, Integer> map = freqMap(a);
    int total = 0;
    for (int x : map.keySet()) {
      int k = map.get(x);
      total += (k * (k - 1)) / 2;
    }
    return total;
  }

  public static void main(String[] args) {
    int[] a = { 1, 1, 2 };
    System.out.println(count(a));
  }
}
