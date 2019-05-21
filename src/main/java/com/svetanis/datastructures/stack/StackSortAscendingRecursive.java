package com.svetanis.datastructures.stack;

import static com.svetanis.java.base.utils.Print.print;

import java.util.Stack;

public final class StackSortAscendingRecursive {

  public static void sort(Stack<Integer> stack) {
    if (!stack.isEmpty()) {
      int temp = stack.pop();
      sort(stack);
      sortedInsert(stack, temp);
    }
  }

  private static void sortedInsert(Stack<Integer> stack, int x) {
    // base case: either stack is empty or
    // newly inserted element is greater than top
    if (stack.isEmpty() || stack.peek() < x) {
      stack.push(x);
      return;
    }
    int temp = stack.pop();
    sortedInsert(stack, x);
    stack.push(temp);
  }

  public static void main(String[] args) {
    Stack<Integer> stack = new Stack<Integer>();
    stack.push(-3);
    stack.push(14);
    stack.push(18);
    stack.push(-5);
    stack.push(30);

    print(stack);
    sort(stack);
    print(stack);
  }
}