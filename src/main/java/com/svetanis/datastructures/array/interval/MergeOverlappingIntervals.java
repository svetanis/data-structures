package com.svetanis.datastructures.array.interval;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Lists.sort;
import static com.svetanis.java.base.utils.Print.print;
import static java.lang.Math.max;
import static java.util.Comparator.comparing;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

public final class MergeOverlappingIntervals {

  public static ImmutableList<Pair<Integer, Integer>> merge(List<Pair<Integer, Integer>> pairs) {
    List<Pair<Integer, Integer>> sorted = sort(pairs, comparing(i -> i.getLeft()));
    List<Pair<Integer, Integer>> list = newArrayList();
    int start = sorted.get(0).getLeft();
    int end = sorted.get(0).getRight();
    for (int i = 1; i < sorted.size(); i++) {
      int left = sorted.get(i).getLeft();
      int right = sorted.get(i).getRight();
      if (left <= end) {
        end = max(end, right);
      } else {
        list.add(Pair.build(start, end));
        start = left;
        end = right;
      }
    }
    list.add(Pair.build(start, end));
    return newList(list);
  }

  public static void main(String[] args) {
    List<Pair<Integer, Integer>> list1 = newArrayList();
    list1.add(Pair.build(6, 8));
    list1.add(Pair.build(1, 9));
    list1.add(Pair.build(2, 4));
    list1.add(Pair.build(4, 7));
    print(merge(list1)); // [1, 9]

    List<Pair<Integer, Integer>> list2 = newArrayList();
    list2.add(Pair.build(6, 8));
    list2.add(Pair.build(1, 3));
    list2.add(Pair.build(2, 4));
    list2.add(Pair.build(4, 7));
    print(merge(list2)); // [1, 8]

    List<Pair<Integer, Integer>> list3 = newArrayList();
    list3.add(Pair.build(1, 3));
    list3.add(Pair.build(7, 9));
    list3.add(Pair.build(4, 6));
    list3.add(Pair.build(10, 13));
    print(merge(list3)); // [1, 3], [4, 6], [7, 9], [10, 13]
  }
}