package com.svetanis.datastructures.queue.impl.array;

import static com.svetanis.java.base.Exceptions.illegalState;

public final class Queue {

  private int[] data; // data storage array
  private int head;
  private int tail;
  private int count; // total number of items in the buffer
  private int capacity; // max buffer size

  public Queue(int capacity) {
    this.capacity = capacity;
    this.head = 0;
    this.tail = capacity - 1;
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
    if (size() < capacity) {
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