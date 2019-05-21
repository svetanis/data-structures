package com.svetanis.datastructures.stack;

import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.Stack;

import com.svetanis.java.base.Pair;

public final class MaxStack {

  private Stack<Pair<Integer, Integer>> stack;

  public MaxStack() {
    this.stack = new Stack<>();
  }

  public int size() {
    return stack.size();
  }

  public boolean empty() {
    return stack.empty();
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

  public void push(int x) {
    int max;
    if (empty()) {
      max = x;
    } else {
      max = Math.max(x, stack.peek().getRight());
    }
    stack.push(Pair.build(x, max));
  }

  public int max() {
    if (empty()) {
      throw illegalState("stack underflow");
    }
    return stack.peek().getRight();
  }

  public static void main(String[] args) {
    MaxStack s = new MaxStack();
    s.push(1);
    s.push(2);
    System.out.println(s.max()); // 2
    System.out.println(s.pop()); // 2
    System.out.println(s.max()); // 1
    s.push(3);
    s.push(2);
    System.out.println(s.max()); // 3
    s.pop();
    System.out.println(s.max()); // 3
    s.pop();
    System.out.println(s.max()); // 1
    s.pop();
    try {
      s.max();
      s.pop();
      s.pop();
      s.pop();
      s.pop();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
