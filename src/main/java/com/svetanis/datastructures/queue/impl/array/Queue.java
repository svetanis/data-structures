package com.svetanis.datastructures.queue.impl.array;

import static com.svetanis.java.base.Exceptions.illegalState;

// A first-in-first-out queue of ints with a fixed capacity, stored in one array used as a ring.
//
// push adds at the back; when the queue is full, push does nothing and the item is silently
// dropped. pop removes the front item and front returns it; both throw when the queue is empty.
//
// head is the position of the oldest item and tail the position of the newest. Both move
// forward and wrap modulo capacity, so no item is ever shifted.

public final class Queue {
  // Time Complexity: push, pop, front, size: O(1)
  // Space Complexity: O(capacity), the array is allocated once

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
    return (i + 1) % capacity;
  }

  public int size() {
    return count;
  }

  public void push(int item) {
    if (size() < capacity) { // when full the item is dropped, with no error
      tail = next(tail);
      data[tail] = item;
      count++;
    }
  }

  public void pop() {
    if (isEmpty()) {
      throw illegalState("queue underflow");
    }
    head = next(head);
    count--;
  }

  public int front() {
    if (isEmpty()) {
      throw illegalState("queue underflow");
    }
    return data[head];
  }

}