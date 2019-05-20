package com.svetanis.datastructures.hashmap;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.reverse;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Lists.transform;
import static com.svetanis.java.base.utils.Arrays.toList;
import static com.svetanis.java.base.utils.Maps.freqMap;
import static com.svetanis.java.base.utils.Print.printLines;
import static java.util.Comparator.comparing;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

public final class TopKWordsPQ {

  public static ImmutableList<Pair<String, Integer>> kMostFrequent(List<String> terms, int k) {
    // Time Complexity: O(n * log k)
    
    Map<String, Integer> map = freqMap(terms);
    Queue<Entry<String, Integer>> queue = new PriorityQueue<>(comparing(e -> e.getValue()));
    for (Entry<String, Integer> entry : map.entrySet()) {
      queue.offer(entry);
      if (queue.size() > k) {
        queue.poll();
      }
    }
    List<Pair<String, Integer>> list = transform(reverse(toList(queue)), e -> Pair.build(e.getKey(), e.getValue()));
    return newList(list.subList(0, k));
  }

  public static void main(String[] args) {
    int k = 3;
    List<String> list1 = build1();
    printLines(kMostFrequent(list1, k));

    List<String> list2 = build2();
    printLines(kMostFrequent(list2, k));
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
