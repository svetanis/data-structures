package com.svetanis.datastructures.queue;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.Deque;
import java.util.Queue;

public final class QueueWithMin {

  private Queue<Integer> queue;
  private Deque<Integer> deque;

  public QueueWithMin() {
    this.queue = newLinkedList();
    this.deque = newLinkedList();
  }

  public void enqueue(int value) {
    queue.offer(value);
    while (!deque.isEmpty() && deque.getLast() > value) {
      deque.pollLast();
    }
    deque.offerLast(value);
  }

  public int dequeue() {
    if (queue.isEmpty()) {
      throw illegalState("queue underflow");
    }
    int ret = queue.peek();
    if (ret == deque.getFirst()) {
      deque.pollFirst();
    }
    queue.poll();
    return ret;
  }

  public int front() {
    return queue.peek();
  }

  public int min() {
    if (deque.isEmpty()) {
      throw illegalState("queue underflow");
    }
    return deque.getFirst();
  }
}
