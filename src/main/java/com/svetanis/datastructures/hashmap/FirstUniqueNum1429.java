package com.svetanis.datastructures.hashmap;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

// 1429. First Unique Number
//
// A queue of integers. The constructor takes the starting numbers, add(value) puts one more
// at the back, and showFirstUnique() returns the first number in the queue that occurs in
// it exactly once, or -1 if there is none. On LeetCode: 1 <= nums.length <= 10^5,
// 1 <= nums[i], value <= 10^8, at most 50000 calls to add and showFirstUnique.
//
// A count can only grow, so a number that has stopped being unique never becomes unique
// again. The deque therefore holds each number once, joined when its count reaches 1, in
// arrival order; showFirstUnique drops from the front every number whose count has since
// gone past 1, and the front left over is the answer. A dropped number is never needed
// again, so each number is dropped at most once.

public final class FirstUniqueNum1429 {
  // Time complexity: add O(1); showFirstUnique O(1) amortized, each number joins the deque
  // once and is dropped at most once, so all the DROP steps together are at most the adds
  // Auxiliary Space: O(d), d = distinct numbers added, one count and one deque entry each

  private Deque<Integer> queue; // CANDIDATES: numbers that were unique when they arrived
  private Map<Integer, Integer> map; // COUNTS: number -> how many times it was added

  public FirstUniqueNum1429(int[] nums) {
    this.map = new HashMap<>();
    this.queue = new ArrayDeque<>();
    for (int num : nums) {
      add(num);
    }
  }

  public int showFirstUnique() {
    // DROP: from the front, every number that is no longer unique
    while (!queue.isEmpty() && map.get(queue.peekFirst()) != 1) {
      queue.pollFirst();
    }
    return queue.isEmpty() ? -1 : queue.peekFirst(); // FIRST unique, or -1 if none is left
  }

  public void add(int val) {
    map.put(val, map.getOrDefault(val, 0) + 1); // COUNT
    if (map.get(val) == 1) {
      queue.offer(val); // JOIN: only on the first copy, so each number joins once
    }
  }

  public static void main(String[] args) {
    FirstUniqueNum1429 fu = new FirstUniqueNum1429(new int[] { 2, 3, 5 });
    System.out.println(fu.showFirstUnique()); // 2
    fu.add(5);
    System.out.println(fu.showFirstUnique()); // 2
    fu.add(2);
    System.out.println(fu.showFirstUnique()); // 3
    fu.add(3);
    System.out.println(fu.showFirstUnique()); // -1
  }
}
