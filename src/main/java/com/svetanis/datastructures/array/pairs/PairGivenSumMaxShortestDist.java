package com.svetanis.datastructures.array.pairs;

import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.java.base.collect.Maps.newMap;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.Map;
import com.google.common.collect.ImmutableMap;

// given an array of n integers and an integer k
// pick two distinct elements whose sum is k and
// find the max shortest distance of the picked
// elements from the endpoints

public final class PairGivenSumMaxShortestDist {
  // Time Complexity: O(n)
  // Auxiliary Space: O(n)
	
  public static int maxShortestDist(int[] a, int k) {
    Map<Integer, Integer> map = distMap(a);
    int min = MAX_VALUE;
    for (int x : a) {
      int y = k - x;
      if (x != y && map.containsKey(y)) {
        int max = max(map.get(x), map.get(y));
        min = min(min, max);
      }
    }
    return min;
  }

  private static ImmutableMap<Integer, Integer> distMap(int[] a) {
    int n = a.length;
    Map<Integer, Integer> map = newHashMap();
    for (int i = 0; i < n; i++) {
      int min = min(1 + i, n - i);
      if (map.containsKey(a[i])) {
        int prev = map.get(a[i]);
        map.put(a[i], min(prev, min));
      } else {
        map.put(a[i], min);
      }
    }
    return newMap(map);
  }

  public static void main(String[] args) {
    int[] a = { 3, 5, 8, 6, 7 };
    System.out.println(maxShortestDist(a, 11));
  }
}
