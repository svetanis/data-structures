package com.svetanis.datastructures.stack;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Queue;

public final class StackFromTwoQueuesPop {
  private Queue<Integer> q1;
  private Queue<Integer> q2;

  public StackFromTwoQueuesPop() {
    this.q1 = newLinkedList();
    this.q2 = newLinkedList();
  }

  public boolean isEmpty() {
    return q1.isEmpty();
  }

  public void push(int value) {
    q1.offer(value);
  }

  public int pop() {
    while (q1.size() != 1) {
      q2.offer(q1.poll());
    }
    int result = q1.poll();
    // swap q1 and q2
    Queue<Integer> temp = q1;
    q1 = q2;
    q2 = temp;
    return result;
  }

  public static void main(String[] args) {
    StackFromTwoQueuesPop stack = new StackFromTwoQueuesPop();
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

