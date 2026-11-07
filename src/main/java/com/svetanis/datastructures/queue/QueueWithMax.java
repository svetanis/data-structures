package com.svetanis.datastructures.queue;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

// A queue that also answers max() in O(1) (not a LeetCode problem; the same structure as LC 239,
// Sliding Window Maximum, which expires the front by index rather than by value).
//
// Input: enqueue(val) adds at the back; dequeue() removes and returns the front; front() returns
// it; max() returns the largest value in the queue. dequeue, front and max throw
// IllegalStateException when the queue is empty.
//
// The one idea: a queue removes its OLDEST element, so a per-element summary of "the maximum so
// far" goes stale the moment the front leaves. The deque instead holds the values that could
// still become the maximum, in decreasing order, oldest first, so one deque entry is one such
// value, and max() is its front. This is legal because a value that arrives behind a larger one
// is beaten for life: the larger one is dequeued first. Reasoning for both mirrors is written out
// once, in QueueWithMin.
//
// Time: enqueue amortized O(1) -- one call may drop many values, but n calls drop at most n;
//   dequeue, front, max O(1)
// Space: O(n) -- the queue, plus O(n) worst case for the deque (a decreasing input keeps every
//   value as a live candidate)
//
// Siblings: QueueWithMin is the mirror; the only differences are the direction of the eviction
// comparison and the method name.

public final class QueueWithMax {
  private Queue<Integer> queue;
  private Deque<Integer> deque;

  public QueueWithMax() {
    this.queue = new LinkedList<>();
    this.deque = new LinkedList<>();
  }

  public void enqueue(int val) {
    queue.offer(val); // TAKE: at the back of the queue
    // STRICTLY less, and the < is load-bearing: it KEEPS duplicates. With <= the
    // second of two equal values evicts the first, the deque ends up holding one
    // copy, dequeue removes it, and the copy still in the queue has no
    // representative left -- max() then throws on a non-empty queue.
    while (!deque.isEmpty() && deque.getLast() < val) {
      deque.pollLast(); // EVICT: beaten for life: it leaves before val does
    }
    deque.offerLast(val); // RECORD: val is a live candidate
  }

  public int front() {
    if (queue.isEmpty()) {
      throw new IllegalStateException("queue underflow");
    }
    return queue.peek(); // RETURN: the oldest
  }

  public int dequeue() {
    if (queue.isEmpty()) {
      throw new IllegalStateException("queue underflow");
    }
    int front = queue.peek();
    if (front == deque.getFirst()) {
      deque.pollFirst(); // EVICT: it was a live candidate, and now it is gone
    }
    queue.poll(); // SKIP past the front
    return front;
  }

  public int max() {
    if (queue.isEmpty()) {
      throw new IllegalStateException("queue underflow");
    }
    return deque.getFirst(); // RETURN: the largest live candidate
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
