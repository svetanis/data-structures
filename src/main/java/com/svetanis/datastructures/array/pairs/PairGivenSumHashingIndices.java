package com.svetanis.datastructures.array.pairs;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.List;
import java.util.Map;

import com.svetanis.java.base.Pair;

// given an array of distinct integer,
// find indices of the two numbers 
// such that they add up to a specific target

public final class PairGivenSumHashingIndices {

  public static Pair<Integer, Integer> pair(List<Integer> list, int target) {
    // Time Complexity: O(n)
    // Space Complexity: O(n)

    int n = list.size();
    Map<Integer, Integer> map = newHashMap();
    for (int i = 0; i < n; i++) {
      int diff = target - list.get(i);
      if (map.containsKey(diff)) {
        return Pair.build(map.get(diff), i);
      }
      map.put(list.get(i), i);
    }
    return Pair.build(-1, -1);
  }

  public static void main(String[] args) {
    List<Integer> list = newArrayList(1, 4, 45, 6, 10, -8);
    System.out.println(pair(list, 16));
  }
}
