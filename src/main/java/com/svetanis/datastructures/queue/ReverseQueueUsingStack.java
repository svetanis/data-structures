package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

// Reverse a queue using a stack (not a LeetCode problem).
//
// Input: a queue. The same queue is emptied, refilled in reverse order, and returned.
//
// The one idea: a stack gives items back newest first. Poll every item from the queue onto the
// stack: the last one polled is the first one popped and offered back, so the order reverses.
//
// Time: O(n) -- each item is polled, pushed, popped and offered once
// Space: O(n) -- the stack holds every item at once
//
// Siblings: ReverseQueueRecursive lets the call stack hold the items instead.

public final class ReverseQueueUsingStack {
  public static Queue<Integer> reverse(Queue<Integer> q) {
    Deque<Integer> s = new ArrayDeque<>();
    while (!q.isEmpty()) {
      s.push(q.poll()); // TAKE: the queue empties, the last item ends on top
    }
    while (!s.isEmpty()) {
      q.offer(s.pop()); // REVERSE: the last item goes back first
    }
    return q; // RETURN: the same queue, reversed
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
    System.out.println(reverse(q1)); // [100, 90, 80, 70, 60, 50, 40, 30, 20, 10]

    Queue<Integer> q2 = new LinkedList<>();
    q2.offer(1);
    q2.offer(2);
    q2.offer(3);
    q2.offer(4);
    q2.offer(5);
    System.out.println(reverse(q2)); // [5, 4, 3, 2, 1]
  }
}
