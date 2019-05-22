package com.svetanis.datastructures.queue.impl.array;

import static com.svetanis.java.base.Exceptions.illegalState;
import static com.svetanis.java.base.Exceptions.unsupportedOperation;

import java.util.Iterator;

public final class ResizingArrayQueue<T> implements Iterable<T> {

  private T[] data; // data storage array
  private int first;
  private int last;
  private int count; // total number of items in the buffer

  public ResizingArrayQueue() {
    this.first = 0;
    this.last = 0;
    this.count = 0;
    this.data = (T[]) new Object[2];
  }

  private boolean isEmpty() {
    return count == 0;
  }

  public int size() {
    return count;
  }

  private void resize(int max) {
    T[] temp = (T[]) new Object[max];
    for (int i = 0; i < count; i++) {
      temp[i] = data[(first + i) % data.length];
    }
    data = temp;
    first = 0;
    last = count;
  }

  public void enqueue(T val) {
    if (size() == data.length) {
      resize(2 * data.length);
    }
    data[last++] = val;
    if (last == data.length) {
      last = 0;
    }
    count++;
  }

  public T dequeue() {
    if (isEmpty()) {
      throw illegalState("queue underflow");
    }
    T item = data[first];
    data[first] = null;
    count--;
    first++;
    // wrap around
    if (first == data.length) {
      first = 0;
    }
    // shrink size of array if necessary
    if (count > 0 && count == data.length / 4) {
      resize(data.length / 2);
    }
    return item;
  }

  public T peek() {
    if (isEmpty()) {
      throw illegalState("queue underflow");
    }
    return data[first];
  }

  @Override
  public Iterator<T> iterator() {
    return new ArrayIterator();
  }

  private class ArrayIterator implements Iterator<T> {
    private int i = 0;

    @Override
    public boolean hasNext() {
      return i < count;
    }

    @Override
    public void remove() {
      throw unsupportedOperation("removals not supported");
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw illegalState("no such element");
      }
      T item = data[(i + first) % data.length];
      i++;
      return item;
    }
  }
}
