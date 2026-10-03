package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 150. Evaluate Reverse Polish Notation
//
// a holds the tokens of an expression in postfix order: each operator comes after its two
// operands. Returns the value of the expression.
//
// The stack holds values not yet used by an operator. An operator pops two: the top one is its
// SECOND operand, because it was pushed last. It pushes its result back as a single value, so
// when the tokens run out the one value left is the answer.

public final class ReversePolishNotation150 {
  // Time Complexity: O(n), one push, or two pops and a push, per token
  // Space Complexity: O(n) for the stack

  public static int evaluate(String[] a) {
    Deque<Integer> stack = new ArrayDeque<>();
    for (String s : a) {
      if (isOperator(s)) {
        int second = stack.pop(); // pushed last, so it is the right operand
        int first = stack.pop();
        stack.push(evaluate(first, second, s));
      } else {
        stack.push(Integer.parseInt(s)); // "-11" is a number: isOperator matches whole tokens
      }
    }
    return stack.pop();
  }

  private static int evaluate(int first, int second, String operator) {
    int result = 0;
    switch (operator) {
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

  private static boolean isOperator(String s) {
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
