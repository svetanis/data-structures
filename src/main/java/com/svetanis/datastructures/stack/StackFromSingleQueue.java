package com.svetanis.datastructures.stack;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.Queue;

public final class StackFromSingleQueue {
  
  private Queue<Integer> queue;

  public StackFromSingleQueue() {
    this.queue = newLinkedList();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }

  public void push(int value) {
    int size = queue.size();
    queue.offer(value);
    for (int i = 0; i < size; i++) {
      queue.offer(queue.poll());
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

