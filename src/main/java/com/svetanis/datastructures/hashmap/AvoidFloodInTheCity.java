package com.svetanis.datastructures.hashmap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import com.svetanis.java.base.utils.Print;

// 1488. Avoid Flood in The City

public final class AvoidFloodInTheCity {
  // Time Complexity: O(n log n )
  // Space Complexity: O(n)

  public static int[] avoidFlood(int[] rains) {
    TreeSet<Integer> dryDays = new TreeSet<>();
    Map<Integer, Integer> map = new HashMap<>();
    int[] result = new int[rains.length];
    Arrays.fill(result, -1);
    for (int i = 0; i < rains.length; i++) {
      int lake = rains[i];
      if (lake == 0) {
        dryDays.add(i);
        result[i] = 1;
      } else {
        if (map.containsKey(lake)) {
          Integer dryDay = dryDays.higher(map.get(lake));
          if (dryDay == null) {
            return new int[0];
          }
          result[dryDay] = lake;
          dryDays.remove(dryDay);
        }
        map.put(lake, i);
      }
    }
    return result;
  }

  public static void main(String[] args) {
    int[] rains = { 1, 2, 3, 4 };
    Print.print(avoidFlood(rains)); // -1, -1, -1, -1
    int[] rains2 = { 1, 2, 0, 0, 2, 1 };
    Print.print(avoidFlood(rains2)); // -1, -1, 2, 1, -1, -1
    int[] rains3 = { 1, 2, 0, 1, 2 };
    Print.print(avoidFlood(rains3)); // []
  }
}