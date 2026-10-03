package com.svetanis.datastructures.stack.impl;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.Queue;

// A stack of integers built on one first-in first-out queue.
//
// push adds a value; pop and top return the most recently pushed value still held.
//
// The queue is kept in stack order: its front is the newest value. push pays for that. It adds
// the new value at the back, then takes each older value from the front and re-adds it at the
// back, so the new value ends up at the front with the older ones behind it in their old order.
// pop and top then only read the front.

public final class StackFromSingleQueue {
  // Time Complexity: push O(n), every older value is moved once; pop, top, isEmpty O(1)
  // Space Complexity: O(n), the one queue

  private Queue<Integer> queue;

  public StackFromSingleQueue() {
    this.queue = newLinkedList();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }

  public void push(int value) {
    int size = queue.size(); // the older values, counted before the new one joins
    queue.offer(value);
    for (int i = 0; i < size; i++) {
      queue.offer(queue.poll()); // an older value goes behind the new one
    }
  }

  public int pop() {
    if (queue.isEmpty()) {
      throw illegalState("stack underflow");
    } else {
      return queue.poll();
    }
  }

  public int top() {
    if (queue.isEmpty()) {
      throw illegalState("stack underflow");
    } else {
      return queue.peek();
    }
  }

  public static void main(String[] args) {
    StackFromSingleQueue stack = new StackFromSingleQueue();
    stack.push(10);
    stack.push(20);
    System.out.println(stack.top());
    stack.pop();
    stack.push(30);
    stack.pop();
    System.out.println(stack.top());
  }
}

