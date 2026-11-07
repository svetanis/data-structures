package com.svetanis.datastructures.queue.circular;

import static java.util.Arrays.copyOf;

// A first-in-first-out queue in one array used as a ring, which doubles the array when full (not
// a LeetCode problem).
//
// Input: enqueue never refuses an item; dequeue and front return null when the queue is empty.
// read prints the items from the oldest to the newest. The starting capacity is 0 or more.
//
// The one idea: one array slot is one place in the line, and the line wraps: after the last slot
// comes slot 0 again. first is the slot of the oldest item, last is where the next item goes,
// and count is how many are held; every index wraps modulo capacity. When the array is full,
// resize copies the items in queue order into an array twice as long, starting at slot 0.
//
// Time: enqueue amortized O(1) -- one resize copies every item, but because the array doubles,
//   n calls copy fewer than 2n items in total; dequeue, front O(1); read O(n)
// Space: O(n) -- the array is less than twice the most items ever held at once, or the starting
//   capacity if that is larger
//
// Siblings:
//   CircularQueue      LC 622, the fixed-capacity version: enqueue refuses an item when full
//   CircularQueueRear  LC 622 with rear stored as well; also fixed
//   CircularDeque      LC 641: fixed capacity, adds and removes at both ends
//   CircularBuffer     not LeetCode: fixed capacity, an append overwrites the oldest item
//   queue/impl/array/ResizingArrayQueue grows the same way and also halves its array when a
//   dequeue leaves it a quarter full.

public final class DynamicQueue<T> {
  private static final String BAD_CAPACITY = "capacity must not be negative, got %d";

  private T[] data; // data storage array
  private int first;
  private int last;
  private int count; // total number of items in the buffer
  private int capacity; // max buffer size

  @SuppressWarnings("unchecked")
  public DynamicQueue(int capacity) {
    if (capacity < 0) {
      throw new IllegalArgumentException(BAD_CAPACITY.formatted(capacity));
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
    T[] b = copyOf(data, Math.max(1, data.length * 2)); // DOUBLE: an array of 0 grows to 1
    for (int k = 0; k < count; k++) {
      b[k] = data[(first + k) % data.length]; // COPY: the k-th oldest item goes to position k
    }
    data = b;
    capacity = data.length; // next() wraps modulo capacity
    first = 0;
    last = count;
  }

  public void enqueue(T item) {
    if (count == data.length) { // FULL? then DOUBLE the array, never refuse
      resize();
    }
    data[last] = item; // WRITE
    last = next(last); // ADVANCE
    count++; // GROW
  }

  public T dequeue() {
    T ret = null;
    if (!isEmpty()) { // EMPTY? then null
      ret = data[first];
      first = next(first); // ADVANCE
      count--; // SHRINK
    }
    return ret;
  }

  public T front() {
    if (count == 0) { // EMPTY?
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

    dqueue.read(); // 5 6 7
    System.out.println("Dequeued element: " + dqueue.dequeue()); // Dequeued element: 5
    dqueue.enqueue(8);
    dqueue.read(); // 6 7 8
    dqueue.enqueue(9); // the array was full: it doubles
    dqueue.read(); // 6 7 8 9
  }
}

