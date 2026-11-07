package com.svetanis.datastructures.hashmap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

// 1488. Avoid Flood in The City
//
// avoidFlood(rains) takes one entry per day: rains[i] > 0 means it rains on lake rains[i] that
// day and fills it; rains[i] == 0 means no rain, and you may dry one lake of your choice. Rain
// on a lake that is already full floods it. Returns one entry per day -- -1 on a rain day,
// the lake dried on a dry day -- or an empty array if no choice avoids a flood. On LeetCode:
// 1 <= rains.length <= 10^5, 0 <= rains[i] <= 10^9; any valid answer is accepted.
//
// Decide a dry day only when it is needed: when lake L is about to be rained on a second time.
// It must be dried on some dry day after it last filled and before today, and the earliest such
// day is the best one to spend -- for every rain still to come, a later dry day can serve any
// lake an earlier one can, and more. The unused dry days sit in a sorted set, so "the first dry day after day j" is one
// higher(j). A dry day nobody claims keeps the placeholder 1: drying an empty lake is allowed.
//
// Siblings, same problem: none in this package.

public final class AvoidFloodInTheCity {
  // Time complexity: O(n log n) -- each day does a constant number of map steps and at most
  // one add, one higher and one remove on the sorted set, each O(log n)
  // Auxiliary Space: O(n) -- the set of unused dry days and the map of last rain per lake

  public static int[] avoidFlood(int[] rains) {
    TreeSet<Integer> dryDays = new TreeSet<>(); // DRY: days with no rain, not yet spent
    Map<Integer, Integer> map = new HashMap<>(); // FULL: lake -> the day it last filled
    int[] result = new int[rains.length];
    Arrays.fill(result, -1);
    for (int i = 0; i < rains.length; i++) {
      int lake = rains[i];
      if (lake == 0) {
        dryDays.add(i); // SAVE: decide later which lake this day dries
        result[i] = 1; // PLACEHOLDER: drying an empty lake is allowed
      } else {
        if (map.containsKey(lake)) { // FULL?: it rained here before and the lake is still full
          Integer dryDay = dryDays.higher(map.get(lake)); // FIND: first unused dry day after it filled
          if (dryDay == null) {
            return new int[0]; // FLOOD: no dry day between the two rains
          }
          result[dryDay] = lake; // SPEND: that day dries this lake
          dryDays.remove(dryDay);
        }
        map.put(lake, i); // FILL: full again, as of today
      }
    }
    return result;
  }

  public static void main(String[] args) {
    int[] rains = { 1, 2, 3, 4 };
    System.out.println(Arrays.toString(avoidFlood(rains))); // [-1, -1, -1, -1]
    int[] rains2 = { 1, 2, 0, 0, 2, 1 };
    System.out.println(Arrays.toString(avoidFlood(rains2))); // [-1, -1, 2, 1, -1, -1]
    int[] rains3 = { 1, 2, 0, 1, 2 };
    System.out.println(Arrays.toString(avoidFlood(rains3))); // []
  }
}