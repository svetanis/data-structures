package com.svetanis.datastructures.queue.impl.array;

// A first-in-first-out queue of ints with a fixed capacity, stored in one array used as a ring
// (not a LeetCode problem; LC 622, in the circular package, is the same ring with LeetCode's
// method names).
//
// Input: push(item) adds at the back; when the queue is full, push does nothing and the item is
// silently dropped. pop() removes the front item and front() returns it; both throw
// IllegalStateException when the queue is empty. The capacity is at least 1.
//
// The one idea: one array slot is one place in the line, and the line wraps: after the last slot
// comes slot 0 again. head is the slot of the oldest item and tail the slot of the newest. Both
// move forward and wrap modulo capacity, so no item is ever shifted.
//
// Time: push, pop, front, size O(1) -- index arithmetic only
// Space: O(capacity) -- the array is allocated once
//
// Siblings: queue/circular/CircularQueue and CircularQueueRear are the same ring with LC 622's
// contract (false when full); impl/list/Queue holds the items in a linked list.

public final class Queue {
  private int[] data; // data storage array
  private int head;
  private int tail;
  private int count; // total number of items in the buffer
  private int capacity; // max buffer size

  public Queue(int capacity) {
    this.capacity = capacity;
    this.head = 0;
    this.tail = capacity - 1; // one before 0: the first push moves tail to position 0
    this.count = 0;
    this.data = new int[capacity];
  }

  public boolean isEmpty() {
    return count == 0;
  }

  public boolean isFull() {
    return count == capacity;
  }

  private int next(int i) {
    return (i + 1) % capacity; // STEP: from the last slot back to 0
  }

  public int size() {
    return count;
  }

  public void push(int item) {
    if (size() < capacity) { // when full the item is dropped, with no error
      tail = next(tail); // ADVANCE
      data[tail] = item; // WRITE
      count++; // GROW
    }
  }

  public void pop() {
    if (isEmpty()) {
      throw new IllegalStateException("queue underflow");
    }
    head = next(head); // ADVANCE: the old slot is not cleared, a later push reuses it
    count--; // SHRINK
  }

  public int front() {
    if (isEmpty()) {
      throw new IllegalStateException("queue underflow");
    }
    return data[head]; // RETURN: the oldest
  }

  public static void main(String[] args) {
    Queue q = new Queue(2);
    q.push(1);
    q.push(2);
    q.push(3); // dropped: the queue is full
    System.out.println(q.size()); // 2
    System.out.println(q.front()); // 1
    q.pop();
    q.push(4); // wraps round to the slot the pop freed
    System.out.println(q.front()); // 2
    q.pop();
    System.out.println(q.front()); // 4
  }
}