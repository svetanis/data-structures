package com.svetanis.datastructures.array.subarray;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLines;

import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.svetanis.java.base.Pair;

public final class AllSubArraysWithZeroSum {

  public static ImmutableList<Pair<Integer, Integer>> sum(int[] a) {
    int n = a.length;
    int sum = 0;
    List<Pair<Integer, Integer>> list = newArrayList();
    Multimap<Integer, Integer> multimap = ArrayListMultimap.create();
    for (int i = 0; i < n; i++) {
      sum += a[i];
      if (sum == 0) {
        list.add(Pair.build(0, i));
      }
      if (multimap.containsKey(sum)) {
        for (int start : multimap.get(sum)) {
          list.add(Pair.build(start + 1, i));
        }
      }
      multimap.put(sum, i);
    }
    return newList(list);
  }

  public static void main(String[] args) {
    int[] a = { 6, 3, -1, -3, 4, -2, 2, 4, 6, -12, -7 };
    printLines(sum(a));
  }
}
