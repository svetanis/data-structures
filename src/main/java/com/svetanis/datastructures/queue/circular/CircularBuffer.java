package com.svetanis.datastructures.queue.circular;

// A fixed-size buffer that keeps the newest items (not a LeetCode problem; LC 622 refuses an
// item when full instead).
//
// Input: append(item) adds at the back; when the buffer is full it OVERWRITES the oldest item.
// remove() drops the oldest item, front() returns it (null when empty), read() prints oldest to
// newest. The capacity is at least 1.
//
// The one idea: one array slot is one place in the line, and the line wraps: after the last slot
// comes slot 0 again. first is the slot of the oldest item, last is where the next append
// writes, and count is how many slots are in use. Moving an index forward wraps from the end of
// the array back to 0, so no item is ever shifted.
//
// Time: append, remove, front, size O(1); read O(n) for the n items held
// Space: O(capacity) -- the array is allocated once
//
// Siblings:
//   CircularQueue      LC 622: front + size, and enqueue refuses an item when full
//   CircularQueueRear  LC 622 with rear stored as well; also refuses when full
//   CircularDeque      LC 641: adds and removes at both ends, refuses when full
//   DynamicQueue       not LeetCode: never refuses and never overwrites, the array doubles

public final class CircularBuffer<T> {
  private static final String BAD_CAPACITY = "capacity must be at least 1, got %d";

  private T[] data; // data storage array
  private int first;
  private int last;
  private int count; // total number of items in the buffer
  private int capacity; // max buffer size

  @SuppressWarnings("unchecked")
  public CircularBuffer(int capacity) {
    if (capacity < 1) { // next() wraps modulo capacity, so it must be at least 1
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
    return (i + 1) % capacity; // from the last position back to 0
  }

  public int size() {
    return count;
  }

  public void append(T item) {
    data[last] = item; // WRITE: never refused
    last = next(last); // ADVANCE
    if (count == capacity) { // FULL? the write above replaced the oldest item
      first = next(first); // OVERWRITE: so the oldest is now the one after it
    } else {
      count++; // GROW
    }
  }

  public void remove() {
    if (!isEmpty()) { // EMPTY? then nothing to drop
      first = next(first); // ADVANCE
      count--; // SHRINK
    }
  }

  public T front() {
    if (count == 0) { // EMPTY?
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

    buffer.read(); // Forum, thread, lang, c++, java, c, python -- one per line
  }
}

