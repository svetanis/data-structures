package com.svetanis.datastructures.stack;

import java.util.Stack;

import com.google.common.base.Joiner;

public final class SortStrUsingStack {

  public static String sort(String str) {
    // Time complexity: O(n^2)
    // Space complexity: O(n)

    Stack<Character> stack = new Stack<>();
    Stack<Character> aux = new Stack<>();
    stack.push(str.charAt(0));
    for (int i = 1; i < str.length(); i++) {
      char c = str.charAt(i);
      char top = stack.peek();
      if (c >= top) {
        stack.push(c);
      } else if (top > c) {
        while (!stack.isEmpty() && stack.peek() > c) {
          aux.push(stack.pop());
        }
        stack.push(c);
        while (!aux.isEmpty()) {
          stack.push(aux.pop());
        }
      }
    }
    return Joiner.on("").join(stack);
  }

  public static void main(String[] args) {
    String str = "geeksforgeeks";
    System.out.println(sort(str));
  }
}
