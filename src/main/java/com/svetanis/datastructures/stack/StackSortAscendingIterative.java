package com.svetanis.datastructures.stack;

import static com.svetanis.java.base.utils.Print.print;

import java.util.Stack;

public final class StackSortAscendingIterative {

  public static Stack<Integer> sort(Stack<Integer> stack) {
    // Time complexity: O(n^2)
    // Space complexity: O(n)

    Stack<Integer> aux = new Stack<>();
    while (!stack.isEmpty()) {
      int temp = stack.pop();
      while (!aux.isEmpty() && aux.peek() > temp) {
        stack.push(aux.pop());
      }
      aux.push(temp);
    }
    return aux;
  }

  public static void main(String[] args) {
    Stack<Integer> stack = new Stack<Integer>();
    stack.push(9);
    stack.push(1);
    stack.push(5);
    stack.push(12);
    print(stack);
    Stack<Integer> sorted = sort(stack);
    print(sorted);
  }
}