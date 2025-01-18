package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// 1207. Unique Number of Occurrences

public final class UniqueNumberOfOccurrences1207 {
  // Time Complexity: O(n)
  // Space Complexity: O(n)

  public static boolean uniqueOccurrences(int[] a) {
    Map<Integer, Integer> map = freqMap(a);
    Set<Integer> set = new HashSet<>(map.values());
    return set.size() == map.values().size();
  }

  private static Map<Integer, Integer> freqMap(int[] a) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int element : a) {
      map.put(element, map.getOrDefault(element, 0) + 1);
    }
    return map;
  }

  public static void main(String[] args) {
    int[] a1 = { 1, 2, 2, 1, 1, 3 };
    System.out.println(uniqueOccurrences(a1)); // true
    int[] a2 = { 1, 2 };
    System.out.println(uniqueOccurrences(a2)); // false
    int[] a3 = { -3, 0, 1, -3, 1, 1, 1, -3, 10, 0 };
    System.out.println(uniqueOccurrences(a3)); // true
  }
}
