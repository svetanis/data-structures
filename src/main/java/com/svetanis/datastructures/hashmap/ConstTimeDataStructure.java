package com.svetanis.datastructures.hashmap;

import static java.util.Collections.swap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

// Insert, remove, search and getRandom in O(1) -- no LeetCode number
//
// A set of distinct ints. add(x) inserts x if it is not already there; remove(x) deletes x
// if it is there; search(x) returns x's position in the internal list, or -1 if x is absent;
// getRandom() returns one of the stored values, each equally likely, and needs at least one.
//
// A list alone gives a random pick in O(1) but finds a value in O(n); a map alone finds a
// value in O(1) but cannot pick a random one. So keep both: the list holds the values, the map
// holds each value's position in the list. Removing from the middle of a list is O(n), so
// remove first swaps x with the last value, deletes the last slot, and tells the map where the
// moved value now sits. The map forgets x only after that: when x is itself the last value,
// "the moved value" is x, and forgetting it first would put it straight back.
//
// Siblings, same problem: none in this package. RandomizedSet (380) is the same two
// structures with LeetCode's interface: insert and remove return booleans, no search.

public final class ConstTimeDataStructure {
  // Time complexity: O(1) per operation, expected, for the hash lookups -- remove swaps with
  // the last slot, so the list never shifts
  // Auxiliary Space: O(n), n = values stored

  private static final Random generator = new Random();

  private List<Integer> list; // VALUES: in no order, no gaps
  private Map<Integer, Integer> hash; // POSITION: value -> its index in list

  public ConstTimeDataStructure() {
    this.list = new ArrayList<>();
    this.hash = new HashMap<>();
  }

  public void add(int x) {
    if (!hash.containsKey(x)) { // NEW?: values are distinct
      list.add(x); // JOIN: at the end
      hash.put(x, list.size() - 1); // POSITION: the last index
    }
  }

  public void remove(int x) {
    if (hash.containsKey(x)) { // PRESENT?
      int index = hash.get(x); // FIND: where x sits
      int size = list.size();
      int last = list.get(size - 1);
      swap(list, index, size - 1); // MOVE: x to the end, the last value into x's slot
      list.remove(size - 1); // DROP: the end, O(1), nothing shifts
      hash.put(last, index); // POSITION: the moved value's new index
      hash.remove(x); // FORGET: after the line above, which re-adds x when x was last
    }
  }

  public int getRandom() {
    int index = generator.nextInt(list.size()); // PICK: any index 0..size - 1; throws when empty
    return list.get(index);
  }

  public int search(int x) {
    return hash.getOrDefault(x, -1); // FIND: -1 when absent
  }

  public static void main(String[] args) {
    ConstTimeDataStructure ds = new ConstTimeDataStructure();
    ds.add(10);
    ds.add(20);
    ds.add(30);
    ds.add(40);
    System.out.println(ds.search(30)); // 2
    ds.remove(20);
    ds.add(50);
    System.out.println(ds.search(50)); // 3
    System.out.println(ds.getRandom()); // one of 10, 40, 30, 50
    System.out.println(ds.list); // [10, 40, 30, 50]
    System.out.println(ds.hash);
  }
}
