package com.svetanis.datastructures.queue.circular;

import static com.svetanis.java.base.Exceptions.illegalArgument;

// A fixed-size buffer that keeps the newest items.
//
// append(item) adds at the back; when the buffer is full it OVERWRITES the oldest item.
// remove() drops the oldest item, front() returns it (null when empty), read() prints oldest
// to newest.
//
// One array used as a ring: first is the position of the oldest item, last is where the next
// append writes, and count is how many positions are in use. Moving an index forward wraps
// from the end of the array back to 0, so no item is ever shifted.

public final class CircularBuffer<T> {
  // Time Complexity: append, remove, front, size: O(1); read: O(n) for the n items held
  // Space Complexity: O(capacity), the array is allocated once

  private T[] data; // data storage array
  private int first;
  private int last;
  private int count; // total number of items in the buffer
  private int capacity; // max buffer size

  @SuppressWarnings("unchecked")
  public CircularBuffer(int capacity) {
    if (capacity < 1) { // next() wraps modulo capacity, so it must be at least 1
      throw illegalArgument("capacity must be at least 1, got %s", capacity);
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
    return (i + 1) % capacity; // from the last position back to 0
  }

  public int size() {
    return count;
  }

  public void append(T item) {
    data[last] = item;
    last = next(last);
    if (count == capacity) { // full: the write above replaced the oldest item
      first = next(first); // so the oldest is now the one after it
    } else {
      count++;
    }
  }

  public void remove() {
    if (!isEmpty()) {
      first = next(first);
      count--;
    }
  }

  public T front() {
    if (count == 0) {
      return null;
    } else {
      return data[first];
    }
  }

  public void read() {
    for (int k = 0, i = first; k < count; k++, i = next(i)) { // reads only: first stays put
      System.out.println(data[i]);
    }
  }

  public static void main(String[] args) {
    CircularBuffer<String> buffer = new CircularBuffer<String>(10);

    buffer.append("Inter");
    buffer.append("View");
    buffer.append("Street");
    buffer.append("Fee");
    buffer.append("Fo");
    buffer.append("Fum");
    buffer.append("Forum");
    buffer.append("thread");
    buffer.append("lang");
    buffer.append("c++");
    buffer.append("java");
    buffer.append("c");
    buffer.append("python");

    buffer.remove();
    buffer.remove();
    buffer.remove();

    System.out.println();

    buffer.read();
  }
}

