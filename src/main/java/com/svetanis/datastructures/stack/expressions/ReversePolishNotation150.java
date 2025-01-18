package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 150. Evaluate Reverse Polish Notation

public final class ReversePolishNotation150 {
  // Time Complexity: O(n)
  // Space Complexity: O(n)

  public static int evaluate(String[] a) {
    Deque<Integer> stack = new ArrayDeque<>();
    for (String s : a) {
      if (operand(s)) {
        int second = stack.pop();
        int first = stack.pop();
        stack.push(evaluate(first, second, s));
      } else {
        stack.push(Integer.parseInt(s));
      }
    }
    return stack.pop();
  }

  private static int evaluate(int first, int second, String operand) {
    int result = 0;
    switch (operand) {
    case "+":
      result = first + second;
      break;
    case "-":
      result = first - second;
      break;
    case "*":
      result = first * second;
      break;
    case "/":
      result = first / second;
      break;
    }
    return result;
  }

  private static boolean operand(String s) {
    return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
  }

  public static void main(String[] args) {
    String[] a1 = { "2", "1", "+", "3", "*" };
    System.out.println(evaluate(a1)); // 9
    String[] a2 = { "4", "13", "5", "/", "+" };
    System.out.println(evaluate(a2)); // 6
    String[] a3 = { "10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+" };
    System.out.println(evaluate(a3)); // 22
  }
}
