package com.svetanis.datastructures.array.closest;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Maps.checkedGet;
import static com.svetanis.java.base.utils.Maps.freqMap;
import static com.svetanis.java.base.utils.Print.printLines;
import static java.lang.Math.min;

import java.util.List;
import java.util.TreeMap;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

public final class ClosestValue {

  public static ImmutableList<Pair<Integer, Integer>> closest(int[] a) {
    TreeMap<Integer, Integer> map = new TreeMap<>(freqMap(a));
    List<Pair<Integer, Integer>> list = newArrayList();

    for (int i = 0; i < a.length; i++) {
      if (checkedGet(map, a[i]) > 1) {
        list.add(Pair.build(a[i], a[i]));
      } else {
        Optional<Integer> greater = map.higherKey(a[i]) == null ? absent() : of(map.higherKey(a[i]));
        Optional<Integer> lower = map.lowerKey(a[i]) == null ? absent() : of(map.lowerKey(a[i]));
        int closest = getClosest(a[i], greater, lower);
        list.add(Pair.build(a[i], closest));
      }
    }
    return copyOf(list);
  }

  private static int getClosest(int n, Optional<Integer> greater, Optional<Integer> lower) {
    int closest = -1;
    if (lower.isPresent()) {
      closest = lower.get();
    } else if (greater.isPresent()) {
      closest = greater.get();
    } else if (greater.isPresent() && lower.isPresent()) {
      closest = min(greater.get() - n, n - lower.get());
    }
    return closest;
  }

  public static void main(String[] args) {
    int[] a1 = { 10, 5, 11, 6, 20, 12, 10 };
    printLines(closest(a1));

    int[] a2 = { 10, 5, 11, 10, 20, 12 };
    printLines(closest(a2));
  }
}
