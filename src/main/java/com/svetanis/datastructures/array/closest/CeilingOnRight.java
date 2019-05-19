package com.svetanis.datastructures.array.closest;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.reverse;
import static com.svetanis.java.base.utils.Print.printLines;

import java.util.List;
import java.util.TreeSet;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;


public final class CeilingOnRight {

  public static ImmutableList<Pair<Integer, Integer>> ceiling(List<Integer> iterables) {
    TreeSet<Integer> set = new TreeSet<>();
    List<Pair<Integer, Integer>> list = newArrayList();
    List<Integer> reversed = reverse(iterables);
    
    for (int element : reversed) {
      Integer greater = set.ceiling(element);
      Optional<Integer> optional = greater == null ? absent() : of(greater);
      int closest = optional.isPresent() ? optional.get() : -1;
      list.add(Pair.build(element, closest));
      set.add(element);
    }
    return copyOf(reverse(list));
  }

  public static void main(String[] args) {
    List<Integer> list1 = newArrayList(10, 5, 11, 6, 20, 12);
    printLines(ceiling(list1));

    List<Integer> list2 = newArrayList(10, 5, 11, 10, 20, 12);
    printLines(ceiling(list2));

    List<Integer> list3 = newArrayList(50, 20, 200, 100, 30);
    printLines(ceiling(list3));

  }
}
