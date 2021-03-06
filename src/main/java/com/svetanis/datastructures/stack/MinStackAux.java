package com.svetanis.datastructures.stack;

import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.Stack;

// Design a stack that supports push, pop, top, and 
// retrieving the minimum element in constant time.

public final class MinStackAux {

  private Stack<Integer> stack;
  private Stack<Integer> aux;

  public MinStackAux() {
    this.stack = new Stack<>();
    this.aux = new Stack<>();
  }

  public int size() {
    return stack.size();
  }

  public boolean empty() {
    return stack.empty();
  }

  public void push(int x) {
    stack.push(x);
    if (aux.isEmpty() || aux.peek() >= x) {
      aux.push(x);
    }
  }

  public int pop() {
    if (stack.isEmpty()) {
      throw illegalState("stack underflow");
    }
    int top = stack.pop();
    if (top == aux.peek()) {
      aux.pop();
    }
    return top;
  }

  public int peek() {
    if (stack.isEmpty()) {
      throw illegalState("stack underflow");
    }
    return stack.peek();
  }

  public int min() {
    if (aux.empty()) {
      throw illegalState("stack underflow");
    } else {
      return aux.peek();
    }
  }

  public static void main(String[] args) {
    MinStackAux stack = new MinStackAux();
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