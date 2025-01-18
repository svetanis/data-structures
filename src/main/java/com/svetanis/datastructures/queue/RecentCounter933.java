package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Deque;

// 933. Number of Recent Calls

public final class RecentCounter933 {
  // Time complexity: O(1)
  // Space complexity: O(n)

  private static final int OFFSET = 3000;
  private Deque<Integer> queue = new ArrayDeque<>();

  public int ping(int t) {
    int time = t - OFFSET;
    queue.offer(t);
    while (!queue.isEmpty() && queue.peek() < time) {
      queue.poll();
    }
    return queue.size();
  }

  public static void main(String[] args) {
    RecentCounter933 rc = new RecentCounter933();
    System.out.println(rc.ping(1)); // 1
    System.out.println(rc.ping(100)); // 2
    System.out.println(rc.ping(3001)); // 3
    System.out.println(rc.ping(3002)); // 3
  }
}