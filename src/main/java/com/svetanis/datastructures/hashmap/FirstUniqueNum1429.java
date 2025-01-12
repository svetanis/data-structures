package com.svetanis.datastructures.hashmap;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

// 1429. First Unique Number

public final class FirstUniqueNum1429 {

  private Deque<Integer> queue;
  private Map<Integer, Integer> map;

  public FirstUniqueNum1429(int[] nums) {
    this.map = new HashMap<>();
    this.queue = new ArrayDeque<>();
    for (int num : nums) {
      add(num);
    }
  }

  public int showFirstUnique() {
    while (!queue.isEmpty() && map.get(queue.peekFirst()) != 1) {
      queue.pollFirst();
    }
    return queue.isEmpty() ? -1 : queue.peekFirst();
  }

  public void add(int val) {
    map.put(val, map.getOrDefault(val, 0) + 1);
    if (map.get(val) == 1) {
      queue.offer(val);
    }
  }

  public static void main(String[] args) {
  }
}
