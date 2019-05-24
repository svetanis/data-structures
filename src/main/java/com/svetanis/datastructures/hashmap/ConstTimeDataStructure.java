package com.svetanis.datastructures.hashmap;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.java.base.utils.Print.print;
import static java.util.Collections.swap;

import java.util.List;
import java.util.Map;
import java.util.Random;

// Design a data structure that supports following operations in Θ(1) time.
// insert(x): Inserts an item x to the data structure if not already present.
// remove(x): Removes an item x from the data structure if present. 
// search(x): Searches an item x in the data structure.
// getRandom(): Returns a random element from current set of elements 

public final class ConstTimeDataStructure {

  private static final Random generator = new Random();

  private List<Integer> list;
  private Map<Integer, Integer> hash;

  public ConstTimeDataStructure() {
    this.list = newArrayList();
    this.hash = newHashMap();
  }

  public void add(int x) {
    if (!hash.containsKey(x)) {
      list.add(x);
      hash.put(x, list.size() - 1);
    }
  }

  public void remove(int x) {
    // check if element is present
    if (hash.containsKey(x)) {
      int index = hash.get(x);
      // if present,
      // then remove element from hash
      hash.remove(x);

      // swap element with last element so that
      // remove from list can be done in O(1) time
      int size = list.size();
      int last = list.get(size - 1);
      swap(list, index, size - 1);

      // remove last element (this is O(1))
      list.remove(size - 1);
      // update hash table for new index of last element
      hash.put(last, index);
    }
  }

  public int getRandom() {
    // find a random index from 0 to size - 1
    int index = generator.nextInt(list.size());

    // return element at randomly picked index
    return list.get(index);
  }

  public int search(int x) {
    return hash.get(x);
  }

  public static void main(String[] args) {
    ConstTimeDataStructure ds = new ConstTimeDataStructure();
    ds.add(10);
    ds.add(20);
    ds.add(30);
    ds.add(40);
    System.out.println(ds.search(30));
    ds.remove(20);
    ds.add(50);
    System.out.println(ds.search(50));
    System.out.println(ds.getRandom());
    print(ds.list);
    print(ds.hash);
  }
}
