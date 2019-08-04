package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

public final class AllUniqueBTsCountMemoization {

  private static final Map<Integer, Integer> MAP = newHashMap();

  public static int count(int n) {
    if (MAP.containsKey(n)) {
      MAP.get(n);
    }
    if (n <= 1) {
      return 1;
    }
    int count = 0;
    for (int i = 1; i <= n; i++) {
      int left = count(i - 1);
      int right = count(n - i);
      count += (left * right);
    }
    MAP.put(n, count);
    return count;
  }

  public static void main(String[] args) {
    System.out.println(count(3));
  }
}
