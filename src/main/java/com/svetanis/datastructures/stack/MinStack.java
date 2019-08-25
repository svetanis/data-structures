package com.svetanis.datastructures.stack;

import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.Stack;

import com.svetanis.java.base.Pair;

// Design a stack that supports push, pop, top, and 
// retrieving the minimum element in constant time.

public final class MinStack {

  private Stack<Pair<Integer, Integer>> stack;

  public MinStack() {
    this.stack = new Stack<>();
  }

  public int size() {
    return stack.size();
  }

  public boolean empty() {
    return stack.empty();
  }

  public void push(int x) {
    int min = empty() ? x : Math.min(x, stack.peek().getRight());
    stack.push(Pair.build(x, min));
  }

  public int pop() {
    if (empty()) {
      throw illegalState("stack underflow");
    }
    return stack.pop().getLeft();
  }

  public int peek() {
    if (empty()) {
      throw illegalState("stack underflow");
    }
    return stack.peek().getLeft();
  }

  public int min() {
    if (empty()) {
      throw illegalState("stack underflow");
    } else {
      return stack.peek().getRight();
    }
  }

  public static void main(String[] args) {
    MinStack stack = new MinStack();
    stack.push(6);
    System.out.println(stack.min()); // 6

    stack.push(7);
    System.out.println(stack.min()); // 6

    stack.push(8);
    System.out.println(stack.min()); // 6

    stack.push(5);
    System.out.println(stack.min()); // 5

    stack.push(3);
    System.out.println(stack.min()); // 3

    stack.pop();
    System.out.println(stack.min()); // 5

    stack.push(10);
    System.out.println(stack.min()); // 5

    stack.pop();
    System.out.println(stack.min()); // 5

    stack.pop();
    System.out.println(stack.min()); // 6
  }

}
