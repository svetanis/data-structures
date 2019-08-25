package com.svetanis.datastructures.stack;

import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.Stack;

// Design a stack that supports push, pop, top, and 
// retrieving the minimum element in constant time.

public final class MinStackMemoryOptimized {
  // Time Complexity: O(1)
  // Space Complexity: O(1)

  private int min;
  private Stack<Integer> stack;

  public MinStackMemoryOptimized() {
    this.stack = new Stack<Integer>();
  }

  public int size() {
    return stack.size();
  }

  public boolean empty() {
    return stack.empty();
  }

  public void push(int x) {
    if (stack.isEmpty()) {
      stack.push(x);
      min = x;
    } else if (x > min) {
      stack.push(x);
    } else {
      stack.push(2 * x - min);
      min = x;
    }
  }

  public int pop() {
    if (stack.isEmpty()) {
      throw illegalState("stack underflow");
    }
    int top = stack.pop();
    int result = 0;
    if (top < min) {
      result = min;
      min = 2 * min - top;
    } else {
      result = top;
    }
    return result;
  }

  public int peek() {
    if (stack.isEmpty()) {
      throw illegalState("stack underflow");
    }
    int top = stack.peek();
    return top < min ? min : top;
  }

  public int min() {
    if (stack.isEmpty()) {
      throw illegalState("stack underflow");
    }
    return min;
  }

  public static void main(String[] args) {
    MinStackMemoryOptimized stack = new MinStackMemoryOptimized();
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
