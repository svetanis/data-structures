package com.svetanis.datastructures.queue;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.Deque;
import java.util.Queue;

// A queue that also answers min() in O(1).
//
// WHY THIS IS NOT MinStack'S TRICK. MinStack keeps one summary per element -- "the
// minimum at or below this depth" -- and that works because a stack removes the
// NEWEST element, so the summary underneath is still true. A queue removes the
// OLDEST, and every summary built over it includes the element that just left, so
// they all go stale at once. Summaries are out; actual candidates are in.
//
// AND THAT SAME ASYMMETRY LETS THIS ONE THROW WORK AWAY. When value arrives behind
// a larger one, the larger one will be dequeued FIRST -- so while it is still in
// the queue, the smaller value is too, and it can never be the minimum again.
// Discard it on the spot. A stack cannot do this: there the larger element
// OUTLIVES the smaller one and may be the minimum after it pops.
//
// So the deque holds exactly the elements that could still become the minimum, in
// increasing order, oldest first -- and min() is its front.

public final class QueueWithMin {
  // enqueue: amortized O(1) -- one call may drop many, but n calls drop at most n
  // dequeue, front, min: O(1)
  // Space: O(n) for the queue, plus O(n) worst case for the deque (an increasing
  // input keeps every element as a live candidate)

  private Queue<Integer> queue;
  private Deque<Integer> deque;

  public QueueWithMin() {
    this.queue = newLinkedList();
    this.deque = newLinkedList();
  }

  public void enqueue(int value) {
    queue.offer(value);
    // STRICTLY greater, and the > is load-bearing: it KEEPS duplicates. With >=
    // the second of two equal values evicts the first, the deque ends up holding
    // one copy, dequeue removes it, and the copy still sitting in the queue has no
    // representative left -- min() then throws on a non-empty queue.
    while (!deque.isEmpty() && deque.getLast() > value) {
      deque.pollLast(); // beaten for life: it leaves before value does
    }
    deque.offerLast(value);
  }

  public int dequeue() {
    if (queue.isEmpty()) {
      throw illegalState("queue underflow");
    }
    int ret = queue.peek();
    if (ret == deque.getFirst()) {
      deque.pollFirst(); // it was a live candidate, and now it is gone
    }
    queue.poll();
    return ret;
  }

  public int front() {
    if (queue.isEmpty()) {
      throw illegalState("queue underflow");
    }
    return queue.peek();
  }

  public int min() {
    if (queue.isEmpty()) {
      throw illegalState("queue underflow");
    }
    return deque.getFirst();
  }

  public static void main(String[] args) {
    QueueWithMin q = new QueueWithMin();
    q.enqueue(3);
    System.out.println(q.min()); // 3
    q.enqueue(1);
    System.out.println(q.min()); // 1
    q.enqueue(2);
    System.out.println(q.min()); // 1 -- 2 stays a candidate, 1 leaves before it
    System.out.println(q.dequeue()); // 3 -- already dropped from the deque by the 1
    System.out.println(q.min()); // 1
    System.out.println(q.dequeue()); // 1
    System.out.println(q.min()); // 2

    // duplicates: the case the > protects
    QueueWithMin d = new QueueWithMin();
    d.enqueue(2);
    d.enqueue(2);
    System.out.println(d.dequeue()); // 2
    System.out.println(d.min()); // 2 -- with >= this throws instead
  }
}
