package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Deque;

// 933. Number of Recent Calls
//
// Input: ping(t) records a call at time t, in milliseconds, and returns how many recorded calls
// have a time in [t - 3000, t], this call included. On LeetCode: 1 <= t <= 10^9, every t is
// strictly larger than the one before, at most 10^4 calls to ping.
//
// The one idea: one queue entry is the time of one call still inside the window, oldest at the
// front. Because each t is larger than the one before, the calls older than t - 3000 are exactly
// the ones at the front, so they are polled off. A call that leaves is never needed again,
// because every later window starts later. What is left in the queue is the answer.
//
// Time: ping amortized O(1) -- each time is offered once and polled at most once
// Space: O(w) -- w = the number of calls inside the current 3000 ms window

public final class RecentCounter933 {
  private static final int OFFSET = 3000;
  private Deque<Integer> queue = new ArrayDeque<>();

  public int ping(int t) {
    int time = t - OFFSET; // WINDOW: the oldest time still inside it
    queue.offer(t); // TAKE: this call, the newest
    while (!queue.isEmpty() && queue.peek() < time) { // DROP: outside this window and every later one
      queue.poll();
    }
    return queue.size(); // RETURN: the calls left are the calls in the window
  }

  public static void main(String[] args) {
    RecentCounter933 rc = new RecentCounter933();
    System.out.println(rc.ping(1)); // 1
    System.out.println(rc.ping(100)); // 2
    System.out.println(rc.ping(3001)); // 3
    System.out.println(rc.ping(3002)); // 3
  }
}