package com.svetanis.datastructures.queue;

import java.util.LinkedList;
import java.util.Queue;

// Reverse a queue using recursion instead of an explicit stack (not a LeetCode
// problem).
//
// Input: a queue. The same queue is changed and returned, its items in reverse order.
//
// The one idea: take the front item off, reverse what is left, then offer the front item at the
// back. Each call holds its own front item while the deeper calls run, so the call stack does the
// job a stack of items would do.
//
// Known limit: the recursion is as deep as the queue is long, so a queue of about 100,000 items
// overflows the default stack (StackOverflowError); it only happens on very large input.
// ReverseQueueUsingStack is the same result with a loop and has no such limit.
//
// Time: O(n) -- one poll and one offer per item
// Space: O(n) -- call depth, one call per item
//
// Siblings: ReverseQueueUsingStack holds the items on an explicit stack instead.

public final class ReverseQueueRecursive {
  public static Queue<Integer> reverse(Queue<Integer> q) {
    if (q.isEmpty()) { // BASE: nothing to reverse
      return q;
    }
    int front = q.poll(); // TAKE: held in this call while the deeper calls run
    q = reverse(q); // RECURSE: the rest, reversed
    q.offer(front); // PLACE: the rest is already reversed, so the old front goes last
    return q;
  }

  public static void main(String[] args) {
    Queue<Integer> q1 = new LinkedList<>();
    q1.offer(56);
    q1.offer(27);
    q1.offer(30);
    q1.offer(45);
    q1.offer(85);
    q1.offer(92);
    q1.offer(58);
    q1.offer(80);
    q1.offer(90);
    q1.offer(100);
    System.out.println(reverse(q1)); // [100, 90, 80, 58, 92, 85, 45, 30, 27, 56]

    Queue<Integer> q2 = new LinkedList<>();
    q2.offer(5);
    q2.offer(24);
    q2.offer(9);
    q2.offer(6);
    q2.offer(8);
    q2.offer(4);
    q2.offer(1);
    q2.offer(8);
    q2.offer(3);
    q2.offer(6);
    System.out.println(reverse(q2)); // [6, 3, 8, 1, 4, 8, 6, 9, 24, 5]

    Queue<Integer> q3 = new LinkedList<>();
    q3.offer(8);
    q3.offer(7);
    q3.offer(2);
    q3.offer(5);
    q3.offer(1);
    System.out.println(reverse(q3)); // [1, 5, 2, 7, 8]
  }
}
