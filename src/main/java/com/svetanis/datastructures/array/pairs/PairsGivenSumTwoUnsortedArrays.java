package com.svetanis.datastructures.array.pairs;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLines;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

public final class PairsGivenSumTwoUnsortedArrays {

  public static ImmutableList<Pair<Integer, Integer>> pairs(List<Integer> list1, List<Integer> list2, int x) {
    // Time Complexity: O(n)
    // Aux Space : O(n)

    Set<Integer> set = newHashSet(list1);
    List<Pair<Integer, Integer>> list = newArrayList();
    for (int j = 0; j < list2.size(); j++) {
      int diff = x - list2.get(j);
      if (set.contains(diff)) {
        list.add(Pair.build(list2.get(j), diff));
      }
    }
    return newList(list);
  }

  public static void main(String[] args) {
    List<Integer> list1 = newArrayList(1, 2, 3, 7, 5, 4);
    List<Integer> list2 = newArrayList(0, 7, 4, 3, 2, 1);
    printLines(pairs(list1, list2, 8));

    List<Integer> list3 = newArrayList(1, 0, -4, 7, 6, 4);
    List<Integer> list4 = newArrayList(0, 2, 4, -3, 2, 1);
    printLines(pairs(list3, list4, 8));

  }
}
