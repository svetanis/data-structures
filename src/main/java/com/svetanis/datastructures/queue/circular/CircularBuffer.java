package com.svetanis.datastructures.queue.circular;

public final class CircularBuffer<T> {
  private T[] data; // data storage array
  private int first;
  private int last;
  private int count; // total number of items in the buffer
  private int capacity; // max buffer size

  @SuppressWarnings("unchecked")
  public CircularBuffer(int capacity) {
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

  public void append(T item) {
    data[last] = item;
    last = next(last);
    if (count == capacity) {
      first = next(first);
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
    while (first != last - 1) {
      System.out.println(data[first]);
      first = next(first);
    }
    System.out.println(data[last - 1]);
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

