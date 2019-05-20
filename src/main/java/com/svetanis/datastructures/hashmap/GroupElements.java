package com.svetanis.datastructures.hashmap;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Arrays.repeat;
import static com.svetanis.java.base.utils.Maps.freqMap;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

// Group multiple occurrence of array elements ordered by first occurrence

public final class GroupElements {

  public static ImmutableList<Integer> group(int[] a) {
    List<Integer> list = newArrayList();
    Map<Integer, Integer> map = freqMap(a);
    for(int key : map.keySet()) {
      list.addAll(repeat(key, map.get(key)));
    }
    return newList(list);
  }

  public static void main(String[] args) {
    int[] a1 = { 5, 3, 5, 1, 3, 3 };
    print(group(a1));

    int[] a2 = { 4, 6, 9, 2, 3, 4, 9, 6, 10, 4 };
    print(group(a2));
    
    int[] a3 = { 10, 5, 3, 10, 10, 4, 1, 3 };
    print(group(a3));
  }
}