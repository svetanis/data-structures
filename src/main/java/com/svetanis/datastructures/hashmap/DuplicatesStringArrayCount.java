package com.svetanis.datastructures.hashmap;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.filterEntries;
import static com.svetanis.java.base.collect.Maps.newMap;
import static com.svetanis.java.base.utils.Maps.freqMap;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public final class DuplicatesStringArrayCount {

  public static ImmutableMap<String, Integer> count(List<String> list) {
    Map<String, Integer> map = freqMap(list);
    return newMap(filterEntries(map, e -> e.getValue() > 1));
  }

  public static void main(String[] args) {
    List<String> list = build();
    print(count(list));
  }

  private static ImmutableList<String> build() {
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