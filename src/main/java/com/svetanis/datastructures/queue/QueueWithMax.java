package com.svetanis.datastructures.queue;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.Deque;
import java.util.Queue;

// A queue that also answers max() in O(1). The mirror of QueueWithMin: the only
// differences are the direction of the eviction comparison and the method name.
// The reasoning behind both is written out once, in QueueWithMin.
//
// In short: a queue removes its OLDEST element, so a per-element summary of "the
// maximum so far" goes stale the moment the front leaves. The deque instead holds
// the elements that could still become the maximum, in decreasing order, oldest
// first -- and this is legal only because a value that arrives behind a larger one
// is beaten for life, since the larger one is dequeued first.
//
// This is the same structure as LC 239, sliding window maximum, which adds expiry
// from the front by index rather than by value.

public final class QueueWithMax {
  // enqueue: amortized O(1) -- one call may drop many, but n calls drop at most n
  // dequeue, front, max: O(1)
  // Space: O(n) for the queue, plus O(n) worst case for the deque (a decreasing
  // input keeps every element as a live candidate)

  private Queue<Integer> queue;
  private Deque<Integer> deque;

  public QueueWithMax() {
    this.queue = newLinkedList();
    this.deque = newLinkedList();
  }

  public void enqueue(int val) {
    queue.offer(val);
    // STRICTLY less, and the < is load-bearing: it KEEPS duplicates. With <= the
    // second of two equal values evicts the first, the deque ends up holding one
    // copy, dequeue removes it, and the copy still in the queue has no
    // representative left -- max() then throws on a non-empty queue.
    while (!deque.isEmpty() && deque.getLast() < val) {
      deque.pollLast(); // beaten for life: it leaves before val does
    }
    deque.offerLast(val);
  }

  public int front() {
    if (queue.isEmpty()) {
      throw illegalState("queue underflow");
    }
    return queue.peek();
  }

  public int dequeue() {
    if (queue.isEmpty()) {
      throw illegalState("queue underflow");
    }
    int front = queue.peek();
    if (front == deque.getFirst()) {
      deque.pollFirst(); // it was a live candidate, and now it is gone
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

  public static void main(String[] args) {
    QueueWithMax q = new QueueWithMax();
    q.enqueue(1);
    System.out.println(q.max()); // 1
    q.enqueue(3);
    System.out.println(q.max()); // 3
    q.enqueue(2);
    System.out.println(q.max()); // 3 -- 2 stays a candidate, 3 leaves before it
    System.out.println(q.dequeue()); // 1 -- already dropped from the deque by the 3
    System.out.println(q.max()); // 3
    System.out.println(q.dequeue()); // 3
    System.out.println(q.max()); // 2

    // duplicates: the case the < protects
    QueueWithMax d = new QueueWithMax();
    d.enqueue(2);
    d.enqueue(2);
    System.out.println(d.dequeue()); // 2
    System.out.println(d.max()); // 2 -- with <= this throws instead
  }
}
