package com.svetanis.datastructures.queue;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

// Reverse a queue using a stack.
//
// Input: a queue. The same queue is emptied, refilled in reverse order, and returned.
//
// A stack gives items back newest first. Poll every item from the queue onto the stack: the
// last one polled is the first one popped and offered back, so the order reverses.

public final class ReverseQueueUsingStack {
  // Time Complexity: O(n), each item is polled, pushed, popped and offered once
  // Space Complexity: O(n) for the stack

  public static Queue<Integer> reverse(Queue<Integer> q) {
    Deque<Integer> s = new ArrayDeque<>();
    while (!q.isEmpty()) {
      s.push(q.poll());
    }
    while (!s.isEmpty()) {
      q.offer(s.pop());
    }
    return q;
  }

  public static void main(String[] args) {
    Queue<Integer> q1 = newLinkedList();
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
    System.out.println(reverse(q1));

    Queue<Integer> q2 = newLinkedList();
    q2.offer(1);
    q2.offer(2);
    q2.offer(3);
    q2.offer(4);
    q2.offer(5);
    System.out.println(reverse(q2));
  }
}
