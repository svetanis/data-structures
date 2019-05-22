package com.svetanis.datastructures.queue;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.Deque;
import java.util.Queue;

public final class QueueWithMax {

  private Queue<Integer> queue;
  private Deque<Integer> deque;

  public QueueWithMax() {
    this.queue = newLinkedList();
    this.deque = newLinkedList();
  }

  public void enqueue(int val) {
    queue.offer(val);
    while (!deque.isEmpty() && deque.getLast() < val) {
      deque.pollLast();
    }
    deque.offerLast(val);
  }

  public int front() {
    return queue.peek();
  }

  public int dequeue() {
    if (queue.isEmpty()) {
      throw illegalState("queue underflow");
    }
    int front = queue.peek();
    if (front == deque.getFirst()) {
      deque.pollFirst();
    }
    queue.poll();
    return front;
  }

  public int max() {
    if (queue.isEmpty()) {
      throw illegalState("queue underflow");
    }
    return deque.getFirst();
  }
}
