package com.svetanis.datastructures.queue.impl.array;

import java.util.Iterator;
import java.util.NoSuchElementException;

// A first-in-first-out queue in one array used as a ring, which grows when full and shrinks when
// mostly empty (not a LeetCode problem).
//
// Input: enqueue(val) never refuses an item; dequeue() and peek() throw IllegalStateException
// when the queue is empty. Iteration goes from the oldest item to the newest.
//
// The one idea: one array slot is one place in the line, and the line wraps: after the last slot
// comes slot 0 again. first is the slot of the oldest item and last is where the next item goes.
// The array doubles when full and halves when a dequeue leaves it one quarter full. Either way the
// new array is half full, so many calls must pass before the next resize.
//
// Time: enqueue, dequeue amortized O(1) -- a resize copies every item, but it leaves the array
//   half full, so the calls before the next resize outnumber the items it copies by a constant
//   factor; peek, size O(1)
// Space: O(n) -- the array is never more than four times the number of items (at least two
//   slots)
//
// Siblings: queue/circular/DynamicQueue grows the same way but never shrinks.

public final class ResizingArrayQueue<T> implements Iterable<T> {
  private T[] data; // data storage array
  private int first;
  private int last;
  private int count; // total number of items in the buffer

  @SuppressWarnings("unchecked")
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

  @SuppressWarnings("unchecked")
  private void resize(int max) {
    T[] temp = (T[]) new Object[max];
    for (int i = 0; i < count; i++) {
      temp[i] = data[(first + i) % data.length]; // COPY: the i-th oldest item goes to slot i
    }
    data = temp;
    first = 0;
    last = count;
  }

  public void enqueue(T val) {
    if (size() == data.length) {
      resize(2 * data.length); // DOUBLE
    }
    data[last++] = val; // WRITE
    if (last == data.length) {
      last = 0;
    }
    count++;
  }

  public T dequeue() {
    if (isEmpty()) {
      throw new IllegalStateException("queue underflow");
    }
    T item = data[first];
    data[first] = null; // CLEAR: do not keep the item alive
    count--;
    first++;
    // wrap around
    if (first == data.length) {
      first = 0;
    }
    // shrink size of array if necessary
    if (count > 0 && count == data.length / 4) { // at a quarter, so the halved array is half full
      resize(data.length / 2); // HALVE
    }
    return item; // RETURN: the oldest
  }

  public T peek() {
    if (isEmpty()) {
      throw new IllegalStateException("queue underflow");
    }
    return data[first]; // RETURN: the oldest
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
      throw new UnsupportedOperationException("removals not supported");
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException(); // what the Iterator contract asks for
      }
      T item = data[(i + first) % data.length]; // WALK: i-th oldest, wrapping
      i++;
      return item;
    }
  }

  public static void main(String[] args) {
    ResizingArrayQueue<Integer> queue = new ResizingArrayQueue<>();
    for (int i = 1; i <= 5; i++) {
      queue.enqueue(i); // the array grows 2, 4, 8
    }
    System.out.println(queue.dequeue()); // 1
    System.out.println(queue.peek()); // 2
    System.out.println(queue.size()); // 4
    for (int x : queue) {
      System.out.println(x); // 2, 3, 4, 5, one per line
    }
  }
}
