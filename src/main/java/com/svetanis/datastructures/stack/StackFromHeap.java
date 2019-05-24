package com.svetanis.datastructures.stack;

import static com.svetanis.java.base.Exceptions.illegalArgument;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public final class StackFromHeap {

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
    data.offer(new Entry(top++, value));
  }

  public int top() {
    if (top == -1) {
      throw illegalArgument("queue underflow");
    }
    return data.peek().getValue();
  }

  public int pop() {
    if (top == -1) {
      throw illegalArgument("queue underflow");
    }
    int value = data.poll().getValue();
    top--;
    return value;
  }

  public void print() {
    System.out.println("Stack size: " + size());
    Iterator<Entry> iter = data.iterator();
    while (iter.hasNext()) {
      System.out.print(iter.next() + " ");
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
      return entry.order - this.order;
    }

    @Override
    public String toString() {
      return Integer.toString(value);
    }
  }

}
