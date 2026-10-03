package com.svetanis.datastructures.stack.impl;

import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.PriorityQueue;
import java.util.Queue;

// A stack of integers built on a priority queue (a heap).
//
// push adds a value; pop and top return the most recently pushed value still held.
//
// Each value is stored in an Entry together with its push order, a number larger than the order
// of every entry still held. Entry.compareTo ranks a larger order first, so the newest value is
// always at the head of the heap. push and pop both pay O(log n) for that: a new entry always
// outranks all the others, so it climbs from a leaf to the head, and removing the head makes
// the heap re-sift one entry from the head down to a leaf.

public final class StackFromHeap {
  // Time Complexity: push, pop O(log n), one root-to-leaf path of the heap each;
  //   top, size, isEmpty O(1); print O(n log n), it drains a copy of the heap
  // Space Complexity: O(n), one Entry per value held

  private int top;
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
    data.offer(new Entry(top++, value)); // larger than every order still held
  }

  public int top() {
    if (top == -1) {
      throw illegalState("stack underflow");
    }
    return data.peek().getValue();
  }

  public int pop() {
    if (top == -1) {
      throw illegalState("stack underflow");
    }
    int value = data.poll().getValue();
    top--;
    return value;
  }

  public void print() {
    System.out.println("Stack size: " + size());
    Queue<Entry> copy = new PriorityQueue<>(data); // the heap's iterator is not in stack order
    while (!copy.isEmpty()) {
      System.out.print(copy.poll() + " ");
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
      return Integer.compare(entry.order, this.order); // reversed: the larger order comes first
    }

    @Override
    public String toString() {
      return Integer.toString(value);
    }
  }

}
