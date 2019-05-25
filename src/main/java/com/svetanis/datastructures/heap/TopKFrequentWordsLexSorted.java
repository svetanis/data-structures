package com.svetanis.datastructures.heap;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.reverse;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Arrays.toList;
import static com.svetanis.java.base.utils.Maps.freqMap;
import static com.svetanis.java.base.utils.Print.printLines;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.google.common.collect.ImmutableList;

public final class TopKFrequentWordsLexSorted {

  public static ImmutableList<String> topKFrequent(List<String> terms, int k) {
    // Time Complexity: O(n * log k)

    Map<String, Integer> map = freqMap(terms);

    // min heap : least frequent element first
    Queue<String> queue = new PriorityQueue<>((s1, s2) -> map.get(s1).equals(map.get(s2)) ? 
                                               s2.compareTo(s1) : map.get(s1) - map.get(s2));

    // keep k top frequent elements in the heap
    for (String entry : map.keySet()) {
      queue.offer(entry);
      if (queue.size() > k) {
        queue.poll();
      }
    }
    return newList(reverse(toList(queue)));
  }

  public static void main(String[] args) {
    int k = 3;
    List<String> list1 = build1();
    printLines(topKFrequent(list1, k));

    List<String> list2 = build2();
    printLines(topKFrequent(list2, k));
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
