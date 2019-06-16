package com.svetanis.datastructures.array.pairs;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLines;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

public final class PairsGivenSumHashing {

  public static ImmutableList<Pair<Integer, Integer>> pairs(int[] a, int k) {
    int n = a.length;
    Set<Integer> set = newHashSet();
    List<Pair<Integer, Integer>> list = newArrayList();
    
    for (int i = 0; i < n; i++) {
      int diff = k - a[i];
      if (set.contains(diff)) {
        list.add(Pair.build(a[i], diff));
      }
      set.add(a[i]);
    }
    return newList(list);
  }

  public static void main(String[] args) {
    int[] a = { 9, 3, 6, 5, 7, -1, 13, 14, -2, 12, 0 };
    printLines(pairs(a, 12));
  }
}
