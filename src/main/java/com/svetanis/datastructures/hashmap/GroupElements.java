package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Group multiple occurrences of array elements, ordered by first occurrence
// (GeeksforGeeks; no LeetCode number)
//
// group takes an int array and returns its elements with all copies of each value next to
// each other, the values in the order of their first occurrence: { 5, 3, 5, 1, 3, 3 }
// becomes [5, 5, 3, 3, 3, 1].
//
// A LinkedHashMap keeps its keys in the order they were first put in, so counting into one
// records both how many copies each value has and which value came first. Writing each key
// out count times, in the map's order, is the answer. A HashMap would count the same, but
// hand the values back in no useful order.

public final class GroupElements {
  // Time complexity: O(n), one pass to count, one to write out
  // Auxiliary Space: O(n) for the counts and the answer

  public static List<Integer> group(int[] a) {
    List<Integer> list = new ArrayList<>();
    Map<Integer, Integer> map = freqMap(a);
    for(int key : map.keySet()) {
      list.addAll(Collections.nCopies(map.get(key), key)); // WRITE every copy of key at once
    }
    return list;
  }

  private static Map<Integer, Integer> freqMap(int[] a) {
    Map<Integer, Integer> map = new LinkedHashMap<>(); // ORDER: keys stay in first-seen order
    for (int element : a) {
      map.merge(element, 1, Integer::sum); // COUNT
    }
    return map;
  }

  public static void main(String[] args) {
    int[] a1 = { 5, 3, 5, 1, 3, 3 };
    System.out.println(group(a1)); // 5, 5, 3, 3, 3, 1

    int[] a2 = { 4, 6, 9, 2, 3, 4, 9, 6, 10, 4 };
    System.out.println(group(a2)); // 4, 4, 4, 6, 6, 9, 9, 2, 3, 10
    
    int[] a3 = { 10, 5, 3, 10, 10, 4, 1, 3 };
    System.out.println(group(a3)); // 10, 10, 10, 5, 3, 3, 4, 1
  }
}