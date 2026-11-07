package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// 1207. Unique Number of Occurrences
//
// uniqueOccurrences(arr) returns true if no two distinct values in arr appear the same number
// of times, and false otherwise. On LeetCode: 1 <= arr.length <= 1000,
// -1000 <= arr[i] <= 1000.
//
// Count each value in a map, then put the counts into a set. A set drops repeats, so it is
// smaller than the list of counts exactly when two values share a count.
//
// Siblings, same problem: none in this package.

public final class UniqueNumberOfOccurrences1207 {
  // Time complexity: O(n), one pass to count and one over the distinct values
  // Auxiliary Space: O(n), the map and the set hold at most one entry per value

  public static boolean uniqueOccurrences(int[] a) {
    Map<Integer, Integer> map = freqMap(a);
    Set<Integer> set = new HashSet<>(map.values()); // DEDUPE: equal counts collapse
    return set.size() == map.values().size(); // SAME SIZE: no count repeated
  }

  private static Map<Integer, Integer> freqMap(int[] a) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int element : a) {
      map.put(element, map.getOrDefault(element, 0) + 1); // COUNT
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
