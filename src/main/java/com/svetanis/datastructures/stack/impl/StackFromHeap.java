package com.svetanis.datastructures.stack.impl;

import java.util.PriorityQueue;
import java.util.Queue;

// A stack of integers built on a priority queue (a heap). Not a LeetCode problem.
//
// push adds a value; pop and top return the most recently pushed value still held.
//
// Each value is stored in an Entry together with its push order, a number larger than the order
// of every entry still held. Entry.compareTo ranks a larger order first, so the newest value is
// always at the head of the heap. push and pop both pay O(log n) for that: a new entry always
// outranks all the others, so it climbs from a leaf to the head, and removing the head makes
// the heap re-sift one entry from the head down to a leaf.
//
// Siblings, a stack built from another structure:
//   StackFromSingleQueue    one FIFO queue rotated on every push, push O(n)
//   StackFromTwoQueuesPush  two queues, push O(n), pop O(1)
//   StackFromTwoQueuesPop   two queues, push O(1), pop O(n)

public final class StackFromHeap {
  // Time Complexity: push, pop O(log n), one root-to-leaf path of the heap each;
  //   top, size, isEmpty O(1); print O(n log n), it drains a copy of the heap
  // Space Complexity: O(n), one Entry per value held

  private int top; // COUNT: size - 1, which is also the order the next push will use
  private Queue<Entry> data;

  public StackFromHeap() {
    this.top = -1;
    this.data = new PriorityQueue<>();
  }

  public boolean isEmpty() {
    return top == -1;
  }

  public int size() {
    return top + 1;
  }

  public void push(int value) {
    data.offer(new Entry(top++, value)); // ORDER: larger than every order still held
  }

  public int top() {
    if (top == -1) {
      throw new IllegalStateException("stack underflow");
    }
    return data.peek().getValue(); // TOP: the head is the newest entry
  }

  public int pop() {
    if (top == -1) {
      throw new IllegalStateException("stack underflow");
    }
    int value = data.poll().getValue(); // POP: remove the newest entry
    top--;
    return value;
  }

  public void print() {
    System.out.println("Stack size: " + size());
    Queue<Entry> copy = new PriorityQueue<>(data); // COPY: the heap's iterator is not in stack order
    while (!copy.isEmpty()) {
      System.out.print(copy.poll() + " "); // DRAIN: newest first, top to bottom
    }
    System.out.println();
  }

  public final class Entry implements Comparable<Entry> {

    private int value;
    private int order;

    public Entry(int order, int value) {
      this.value = value;
      this.order = order;
    }

    public int getValue() {
      return this.value;
    }

    public int getOrder() {
      return this.order;
    }

    @Override
    public int compareTo(Entry entry) {
      return Integer.compare(entry.order, this.order); // RANK: reversed: the larger order comes first
    }

    @Override
    public String toString() {
      return Integer.toString(value);
    }
  }

}
