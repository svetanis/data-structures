package com.svetanis.datastructures.stack;

// Design a stack that supports push, pop, top, and 
// retrieving the maximum element in constant time.

import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.Stack;

public final class MaxStackMemoryOptimized {
  // Time Complexity: O(1)
  // Space Complexity: O(1)

  private int max;
  private Stack<Integer> stack;

  public MaxStackMemoryOptimized() {
    this.stack = new Stack<Integer>();
  }

  public int max() {
    if (stack.isEmpty()) {
      throw illegalState("stack underflow");
    }
    return max;
  }

  public int peek() {
    if (stack.isEmpty()) {
      throw illegalState("stack underflow");
    }
    int top = stack.peek();
    return top > max ? max : top;
  }

  public int pop() {
    if (stack.isEmpty()) {
      throw illegalState("stack underflow");
    }
    int top = stack.pop();
    int result = 0;

    if (top > max) {
      result = max;
      max = 2 * max - top;
    } else {
      result = top;
    }
    return result;
  }

  public void push(int x) {
    if (stack.isEmpty()) {
      max = x;
      stack.push(x);
      return;
    }
    if (x > max) {
      stack.push(2 * x - max);
      max = x;
    } else {
      stack.push(x);
    }
  }

  public static void main(String[] args) {
    MaxStackMemoryOptimized stack = new MaxStackMemoryOptimized();
    stack.push(3);
    stack.push(5);
    System.out.println(stack.max());
    stack.push(7);
    stack.push(19);
    System.out.println(stack.max());
    System.out.println(stack.pop());
    System.out.println(stack.max());
    System.out.println(stack.pop());
    System.out.println(stack.peek());
  }
}
