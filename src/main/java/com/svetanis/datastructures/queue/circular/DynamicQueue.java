package com.svetanis.datastructures.queue.circular;

import static com.svetanis.java.base.Exceptions.illegalArgument;
import static java.util.Arrays.copyOf;

// A first-in-first-out queue in one array used as a ring, which doubles the array when full.
//
// enqueue never refuses an item; dequeue and front return null when the queue is empty.
// read prints the items from the oldest to the newest.
//
// first is the position of the oldest item, last is where the next item goes, and count is how
// many are held; every index wraps modulo capacity. When the array is full, resize copies the
// items in queue order into an array twice as long, starting at position 0.

public final class DynamicQueue<T> {
  // Time Complexity: enqueue amortized O(1): one resize copies every item, but because the
  //   array doubles, n calls copy fewer than 2n items in total; dequeue, front: O(1);
  //   read: O(n)
  // Space Complexity: O(n), the array is less than twice the most items ever held at once

  private T[] data; // data storage array
  private int first;
  private int last;
  private int count; // total number of items in the buffer
  private int capacity; // max buffer size

  @SuppressWarnings("unchecked")
  public DynamicQueue(int capacity) {
    if (capacity < 0) {
      throw illegalArgument("capacity must not be negative, got %s", capacity);
    }
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

  private void resize() {
    T[] b = copyOf(data, Math.max(1, data.length * 2)); // an array of 0 grows to 1
    for (int k = 0; k < count; k++) {
      b[k] = data[(first + k) % data.length]; // the k-th oldest item goes to position k
    }
    data = b;
    capacity = data.length; // next() wraps modulo capacity
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
    for (int k = 0, i = first; k < count; k++, i = next(i)) { // the held items, oldest first
      System.out.print(data[i] + " ");
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

