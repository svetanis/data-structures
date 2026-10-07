package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Deque;

// 933. Number of Recent Calls
//
// ping(t) records a call at time t, in milliseconds, and returns how many recorded calls have a
// time in [t - 3000, t], this call included.
//
// The queue holds call times, oldest at the front. The code relies on each t being larger than
// the one before: then every call older than t - 3000 sits at the front and is polled off.
// A call that leaves is never needed again, because every later window starts later.

public final class RecentCounter933 {
  // Time Complexity: ping amortized O(1), each time is offered once and polled at most once
  // Space Complexity: O(w), w = the number of calls inside the current 3000 ms window

  private static final int OFFSET = 3000;
  private Deque<Integer> queue = new ArrayDeque<>();

  public int ping(int t) {
    int time = t - OFFSET; // the oldest time still inside the window
    queue.offer(t);
    while (!queue.isEmpty() && queue.peek() < time) { // outside this window and every later one
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