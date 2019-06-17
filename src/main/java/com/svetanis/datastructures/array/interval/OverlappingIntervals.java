package com.svetanis.datastructures.array.interval;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.sort;
import static java.util.Comparator.comparing;

import java.util.List;

import com.svetanis.java.base.Pair;

public final class OverlappingIntervals {

  public static boolean isOverlap(List<Pair<Integer, Integer>> pairs) {
    List<Pair<Integer, Integer>> sorted = sort(pairs, comparing(i -> i.getLeft()));
    for (int i = 1; i < sorted.size(); ++i) {
      int right = sorted.get(i).getRight();
      int prev = sorted.get(i - 1).getRight();
      if (right <= prev) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) {
    List<Pair<Integer, Integer>> list1 = newArrayList();
    list1.add(Pair.build(6, 8));
    list1.add(Pair.build(1, 9));
    list1.add(Pair.build(2, 4));
    list1.add(Pair.build(4, 7));
    System.out.println(isOverlap(list1));

    List<Pair<Integer, Integer>> list2 = newArrayList();
    list2.add(Pair.build(1, 3));
    list2.add(Pair.build(1, 7));
    list2.add(Pair.build(4, 8));
    list2.add(Pair.build(2, 5));
    System.out.println(isOverlap(list2));

    List<Pair<Integer, Integer>> list3 = newArrayList();
    list3.add(Pair.build(1, 3));
    list3.add(Pair.build(7, 9));
    list3.add(Pair.build(4, 6));
    list3.add(Pair.build(10, 13));
    System.out.println(isOverlap(list3));
  }
}
