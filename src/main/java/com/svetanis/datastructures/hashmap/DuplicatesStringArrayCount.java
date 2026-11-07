package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Count the strings that appear more than once -- no LeetCode number
//
// count(list) returns every string that occurs at least twice in list, mapped to how many
// times it occurs, in the order each first appears. Strings seen once are left out. The map
// cannot be changed by the caller.
//
// Count every string in one pass, in a map that remembers insertion order, then copy across
// only the entries whose count is above 1. A LinkedHashMap is what keeps first-appearance
// order: a plain HashMap would list them in hash order.
//
// Siblings, same problem: none in this package.

public final class DuplicatesStringArrayCount {
  // Time complexity: O(c), c = characters in all the strings -- each string is hashed once to
  // count it, and each distinct one once more to copy it
  // Auxiliary Space: O(d), d = distinct strings -- the count map

  private static final String ENTRY = "{%s, %s}%n";

  public static Map<String, Integer> count(List<String> list) {
    Map<String, Integer> map = new LinkedHashMap<>(); // COUNT: string -> copies, first-seen order
    for (String s : list) {
      map.merge(s, 1, Integer::sum);
    }
    Map<String, Integer> repeated = new LinkedHashMap<>();
    for (Map.Entry<String, Integer> e : map.entrySet()) {
      if (e.getValue() > 1) { // REPEAT?: two copies or more
        repeated.put(e.getKey(), e.getValue()); // KEEP: in the same order
      }
    }
    return Collections.unmodifiableMap(repeated);
  }

  public static void main(String[] args) {
    List<String> list = build();
    count(list).forEach((s, n) -> System.out.printf(ENTRY, s, n)); // {abortion, 2} ... {aaa, 2}
  }

  private static List<String> build() {
    List<String> list = new ArrayList<>();
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
    return Collections.unmodifiableList(list);
  }
}