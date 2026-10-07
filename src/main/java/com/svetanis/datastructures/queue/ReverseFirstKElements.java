package com.svetanis.datastructures.queue;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.Exceptions.illegalArgument;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

// Reverse the first k items of a queue, leaving the rest in their order behind them.
//
// Input: a queue and k, the number of items to reverse from the front. The same queue is
// changed and returned.
//
// A stack gives items back in reverse order. Poll the first k items onto a stack and offer
// them back: they now sit at the back of the queue, reversed. The other n - k items are still
// in front of them, so each is polled and offered once more to move it behind the reversed block.

public final class ReverseFirstKElements {
  // Time Complexity: O(n), each item is polled and offered at most twice
  // Space Complexity: O(k) for the stack

  public static Queue<Integer> reverse(Queue<Integer> q, int k) {
    if (k < 0 || k > q.size()) { // there must be k items to take off the front
      throw illegalArgument("k must be between 0 and %s, got %s", q.size(), k);
    }
    Deque<Integer> s = new ArrayDeque<>();
    for (int i = 0; i < k; i++) {
      s.push(q.poll());
    }
    while (!s.isEmpty()) {
      q.offer(s.pop());
    }
    for (int i = 0; i < q.size() - k; i++) { // size stays n: each turn polls one, offers one
      q.offer(q.poll());
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
    System.out.println(reverse(q1, 5));

    Queue<Integer> q2 = newLinkedList();
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
    System.out.println(reverse(q2, 4));
  }
}
