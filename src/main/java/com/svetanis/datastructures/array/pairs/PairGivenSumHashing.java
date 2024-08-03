package com.svetanis.datastructures.array.pairs;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.List;
import java.util.Set;

import com.svetanis.java.base.Pair;

// given an array of distinct integers
// and a number k, find whether or not
// there exist two elements in array
// whose sum is exactly k

public final class PairGivenSumHashing {

  public static Pair<Integer, Integer> pair(List<Integer> list, int target) {
    int n = list.size();
    Set<Integer> set = newHashSet();
    for (int i = 0; i < n; i++) {
      int diff = target - list.get(i);
      if (set.contains(diff)) {
        return Pair.build(list.get(i), diff);
      }
      set.add(list.get(i));
    }
    return Pair.build(-1, -1);
  }

  public static boolean isPair(List<Integer> list, int k) {
    int n = list.size();
    Set<Integer> set = newHashSet();
    for (int i = 0; i < n; i++) {
      int diff = k - list.get(i);
      if (set.contains(diff)) {
        return true;
      }
      set.add(list.get(i));
    }
    return false;
  }

  public static void main(String[] args) {
    List<Integer> list = newArrayList(1, 4, 45, 6, 10, -8);
    System.out.println(pair(list, 16));
    System.out.println(isPair(list, 16));
  }
}