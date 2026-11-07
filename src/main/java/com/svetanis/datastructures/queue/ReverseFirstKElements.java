package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

// Reverse the first k items of a queue, leaving the rest in their order behind them (not a
// LeetCode problem).
//
// Input: a queue and k, the number of items to reverse from the front, 0 <= k <= the queue's
// size; any other k throws IllegalArgumentException. The same queue is changed and returned.
//
// The one idea: a stack gives items back in reverse order. Poll the first k items onto a stack
// and offer them back: they now sit at the back of the queue, reversed. The other n - k items are
// still in front of them, so each is polled and offered once more to move it behind the reversed
// block.
//
// Time: O(n) -- each item is polled and offered at most twice
// Space: O(k) -- the stack holds the first k items

public final class ReverseFirstKElements {
  public static Queue<Integer> reverse(Queue<Integer> q, int k) {
    if (k < 0 || k > q.size()) { // GUARD: there must be k items to take off the front
      throw new IllegalArgumentException("k must be between 0 and " + q.size() + ", got " + k);
    }
    Deque<Integer> s = new ArrayDeque<>();
    for (int i = 0; i < k; i++) {
      s.push(q.poll()); // TAKE: the first k items, the first one ends at the bottom
    }
    while (!s.isEmpty()) {
      q.offer(s.pop()); // REVERSE: the k-th item comes off first and goes to the back first
    }
    for (int i = 0; i < q.size() - k; i++) { // size stays n: each turn polls one, offers one
      q.offer(q.poll()); // ROTATE: an untouched item moves behind the reversed block
    }
    return q; // RETURN: the same queue
  }

  public static void main(String[] args) {
    Queue<Integer> q1 = new LinkedList<>();
    q1.offer(10);
    q1.offer(20);
    q1.offer(30);
    q1.offer(40);
    q1.offer(50);
    q1.offer(60);
    q1.offer(70);
    q1.offer(80);
    q1.offer(90);
    q1.offer(100);
    System.out.println(reverse(q1, 5)); // [50, 40, 30, 20, 10, 60, 70, 80, 90, 100]

    Queue<Integer> q2 = new LinkedList<>();
    q2.offer(10);
    q2.offer(20);
    q2.offer(30);
    q2.offer(40);
    q2.offer(50);
    q2.offer(60);
    q2.offer(70);
    q2.offer(80);
    q2.offer(90);
    q2.offer(100);
    System.out.println(reverse(q2, 4)); // [40, 30, 20, 10, 50, 60, 70, 80, 90, 100]
  }
}
