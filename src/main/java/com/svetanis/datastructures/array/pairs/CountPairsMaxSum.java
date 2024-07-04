package com.svetanis.datastructures.array.pairs;

import static com.svetanis.java.base.collect.Maps.checkedGet;
import static com.svetanis.java.base.utils.Maps.freqMap;
import static java.lang.Integer.MIN_VALUE;

import java.util.Map;

import com.svetanis.java.base.Pair;

// given an array, count number of pairs
// s.t. a[i] + a[j] is max and i < j

public final class CountPairsMaxSum {
  // Time Complexity: O(n)

  public static int countPairs(int[] a) {
    Pair<Integer, Integer> pair = largestPair(a);
    Map<Integer, Integer> map = freqMap(a);
    // left is max element
    int maxCount = checkedGet(map, pair.getLeft());
    // if max element appears more than once
    // choose 2 elements from maxCount:
    int sum = maxCount * (maxCount - 1) / 2;
    // if max element appears once, then result
    // is equal to count of second max element
    return maxCount > 1 ? sum : checkedGet(map, pair.getRight());
  }

  private static Pair<Integer, Integer> largestPair(int[] a) {

    int n = a.length;
    int first = MIN_VALUE;
    int second = MIN_VALUE;

    for (int i = 0; i < n; ++i) {
      if (a[i] > first) {
        second = first;
        first = a[i];
      } else if (a[i] > second) {
        second = a[i];
      }
    }
    return Pair.build(first, second);
  }


  public static void main(String[] args) {
    int[] a1 = { 1, 1, 1, 2, 2, 2, 3 };
    System.out.println(countPairs(a1));
  }
}
