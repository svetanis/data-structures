package com.svetanis.datastructures.queue.circular;

import static java.util.Arrays.copyOf;

public final class DynamicQueue<T> {

  private T[] data; // data storage array
  private int first;
  private int last;
  private int count; // total number of items in the buffer
  private int capacity; // max buffer size

  @SuppressWarnings("unchecked")
  public DynamicQueue(int capacity) {
    this.first = 0;
    this.last = 0;
    this.count = 0;
    this.capacity = capacity;
    this.data = (T[]) new Object[capacity];
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

  public void resize() {
    T[] b = copyOf(data, data.length * 2);
    for (int k = 0; k < count; k++) {
      b[k] = data[(first + k) % data.length];
    }
    data = b;
    first = 0;
    last = count;
  }

  public void enqueue(T item) {
    if (count == data.length) {
      resize();
    }
    data[last] = item;
    last = next(last);
    count++;
  }

  public T dequeue() {
    T ret = null;
    if (!isEmpty()) {
      ret = data[first];
      first = next(first);
      count--;
    }
    return ret;
  }

  public T front() {
    if (count == 0) {
      return null;
    } else {
      return data[first];
    }
  }

  public void read() {
    for (T e : data) {
      System.out.print(e + " ");
    }
    System.out.println();
  }

  public static void main(String[] args) {
    DynamicQueue<Integer> dqueue = new DynamicQueue<Integer>(3);
    dqueue.enqueue(5);
    dqueue.enqueue(6);
    dqueue.enqueue(7);

    dqueue.read();
    System.out.println("Dequeued element: " + dqueue.dequeue());
    dqueue.enqueue(8);
    dqueue.read();
    dqueue.enqueue(9);
    dqueue.read();
  }
}

