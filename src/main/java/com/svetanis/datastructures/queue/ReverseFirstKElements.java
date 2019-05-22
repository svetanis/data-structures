package com.svetanis.datastructures.queue;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Queue;
import java.util.Stack;

public final class ReverseFirstKElements {

  public static Queue<Integer> reverse(Queue<Integer> q, int k) {
    Stack<Integer> s = new Stack<>();
    for (int i = 0; i < k; i++) {
      s.push(q.poll());
    }
    while (!s.isEmpty()) {
      q.offer(s.pop());
    }
    for (int i = 0; i < q.size() - k; i++) {
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
