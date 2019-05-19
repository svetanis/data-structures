package com.svetanis.datastructures.array.closest;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.utils.Print.printLines;

import java.util.List;
import java.util.TreeSet;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

public final class ClosestGreaterValue {

  public static ImmutableList<Pair<Integer, Integer>> closest(List<Integer> iterables) {
    TreeSet<Integer> set = new TreeSet<>(iterables);
    List<Pair<Integer, Integer>> list = newArrayList();

    for (int element : iterables) {
      Integer greater = set.higher(element);
      Optional<Integer> optional = greater == null ? absent() : of(greater);
      int closest = optional.isPresent() ? optional.get() : -1;
      list.add(Pair.build(element, closest));
    }
    return copyOf(list);
  }

  public static void main(String[] args) {
    List<Integer> list1 = newArrayList(10, 5, 11, 6, 20, 12);
    printLines(closest(list1));

    List<Integer> list2 = newArrayList(10, 5, 11, 10, 20, 12);
    printLines(closest(list2));
  }
}
