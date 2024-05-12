package com.svetanis.datastructures.array.pairs;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static java.lang.Math.min;
import static java.util.Arrays.sort;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

// given a list of distinct unsorted integers,
// find all pairs of elements that have
// the smallest absolute difference

public final class PairsMinDiffSorted {

  public static ImmutableList<Pair<Integer, Integer>> pairs(int[] a) {
    // Time complexity: O(n log n)

    sort(a);
    int min = minDiff(a);
    List<Pair<Integer, Integer>> pairs = newArrayList();
    for (int i = 1; i < a.length; i++) {
      if (a[i] - a[i - 1] == min) {
        pairs.add(Pair.build(a[i - 1], a[i]));
      }
    }
    return newList(pairs);
  }

  private static int minDiff(int[] a) {
    int n = a.length;
    int min = a[1] - a[0];
    for (int i = 2; i < n; i++) {
      min = min(min, a[i] - a[i - 1]);
    }
    return min;
  }

  public static void main(String[] args) {
    int[] a1 = { 10, 50, 12, 100 };
    System.out.println(pairs(a1));

    int[] a2 = { 5, 4, 3, 2 };
    System.out.println(pairs(a2));

    int[] a3 = { 5, 3, 2, 4, 1 };
    System.out.println(pairs(a3));
  }
}

