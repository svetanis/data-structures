package com.svetanis.datastructures.stack;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.Queue;

public final class StackFromTwoQueuesPush {

  private Queue<Integer> q1;
  private Queue<Integer> q2;

  public StackFromTwoQueuesPush() {
    this.q1 = newLinkedList();
    this.q2 = newLinkedList();
  }

  public boolean isEmpty() {
    return q1.isEmpty();
  }

  public void push(int value) {
    q2.offer(value);
    while (!q1.isEmpty()) {
      q2.offer(q1.poll());
    }
    // swap q1 and q2
    Queue<Integer> temp = q1;
    q1 = q2;
    q2 = temp;
  }

  public int pop() {
    if (q1.isEmpty()) {
      throw illegalState("stack underflow");
    } else {
      return q1.poll();
    }
  }

  public int top() {
    if (q1.isEmpty()) {
      throw illegalState("stack underflow");
    } else {
      return q1.peek();
    }
  }

  public static void main(String[] args) {
    StackFromTwoQueuesPush stack = new StackFromTwoQueuesPush();
    stack.push(24);
    stack.push(34);
    stack.push(4);
    stack.push(10);
    stack.push(1);
    stack.push(43);
    stack.push(21);
    System.out.println(stack.pop());
    System.out.println(stack.pop());
    System.out.println(stack.pop());
  }
}
