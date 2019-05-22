package com.svetanis.datastructures.queue;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Queue;
import java.util.Stack;

public final class ReverseQueueUsingStack {

  public static Queue<Integer> reverse(Queue<Integer> q) {
    Stack<Integer> s = new Stack<>();
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
