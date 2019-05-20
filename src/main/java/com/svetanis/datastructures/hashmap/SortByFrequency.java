package com.svetanis.datastructures.hashmap;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.sort;
import static com.svetanis.java.base.collect.Maps.asPairs;
import static com.svetanis.java.base.utils.Maps.freqMap;
import static com.svetanis.java.base.utils.Print.printLines;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

public final class SortByFrequency {

  public static ImmutableList<Pair<String, Integer>> kMostFrequent(List<String> terms, int k) {
    Map<String, Integer> map = freqMap(terms);
    return sort(asPairs(map), new PairComparator());
  }

  public static void main(String[] args) {
    int k = 3;
    List<String> list1 = build1();
    printLines(kMostFrequent(list1, k));

    List<String> list2 = build2();
    printLines(kMostFrequent(list2, k));
  }

  private static class PairComparator implements Comparator<Pair<String, Integer>> {

    @Override
    public int compare(Pair<String, Integer> one, Pair<String, Integer> two) {
      if (one.getRight() == two.getRight()) {
        return one.getLeft().compareTo(two.getLeft());
      }
      return two.getRight() - one.getRight();
    }
  }

  private static ImmutableList<String> build2() {
    List<String> list = newArrayList();
    list.add("Fee");
    list.add("Fi");
    list.add("Fo");
    list.add("Fum");
    list.add("Fee");
    list.add("Fo");
    list.add("Fee");
    list.add("Fee");
    list.add("Fo");
    list.add("Fi");
    list.add("Fi");
    list.add("Fo");
    list.add("Fum");
    list.add("Fee");
    return copyOf(list);
  }

  private static ImmutableList<String> build1() {
    List<String> list = newArrayList();
    list.add("ability");
    list.add("abortion");
    list.add("aaron");
    list.add("ab");
    list.add("absence");
    list.add("abortion");
    list.add("aa");
    list.add("a");
    list.add("able");
    list.add("aa");
    list.add("aaa");
    list.add("aberdeen");
    list.add("ab");
    list.add("abc");
    list.add("aaa");
    list.add("a");
    list.add("aaron");
    list.add("absent");
    list.add("abraham");
    list.add("able");
    return copyOf(list);
  }

}
