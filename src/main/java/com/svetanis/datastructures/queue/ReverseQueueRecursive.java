package com.svetanis.datastructures.queue;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Queue;

// Reverse a queue using recursion instead of an explicit stack.
//
// Input: a queue. The same queue is changed and returned, its items in reverse order.
//
// Take the front item off, reverse what is left, then offer the front item at the back. Each
// call holds its front item while the deeper calls run, so the call stack does the job a stack
// of items would do.

public final class ReverseQueueRecursive {
  // Time Complexity: O(n), one poll and one offer per item
  // Space Complexity: O(n) call depth, one call per item

  public static Queue<Integer> reverse(Queue<Integer> q) {
    if (q.isEmpty()) {
      return q;
    }
    int front = q.poll();
    q = reverse(q);
    q.offer(front); // the rest is already reversed, so the old front goes last
    return q;
  }

  public static void main(String[] args) {
    Queue<Integer> q1 = newLinkedList();
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
    System.out.println(reverse(q1));

    Queue<Integer> q2 = newLinkedList();
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
    System.out.println(reverse(q2));

    Queue<Integer> q3 = newLinkedList();
    q3.offer(8);
    q3.offer(7);
    q3.offer(2);
    q3.offer(5);
    q3.offer(1);
    System.out.println(reverse(q3));
  }
}
