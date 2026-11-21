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
//
// Siblings:
//   PostfixExpressionEvaluation -- not LeetCode: the same stack on a string of single digits,
//                                  with ^ as well, and a check for malformed input
//   BasicCalculatorIII          -- 772: the same operators in infix order, which needs a second
//                                  stack for the operators

public final class ReversePolishNotation150 {
  // Time Complexity: O(n), one push, or two pops and a push, per token
  // Space Complexity: O(n) for the stack

  public static int evaluate(String[] a) {
    Deque<Integer> stack = new ArrayDeque<>(); // STACK: values not used by an operator yet
    for (String s : a) { // WALK: one token at a time
      if (isOperator(s)) {
        int second = stack.pop(); // POP: pushed last, so it is the right operand
        int first = stack.pop(); // POP: the left operand
        stack.push(evaluate(first, second, s)); // APPLY: the result is one value again
      } else {
        stack.push(Integer.parseInt(s)); // PUSH: "-11" is a number: isOperator matches whole tokens
      }
    }
    return stack.pop(); // RETURN: the one value left
  }

  private static int evaluate(int first, int second, String operator) {
    int result = 0; // START: stays 0 for a token that is not one of the four
    switch (operator) {
    case "+":
      result = first + second; // APPLY: first + second
      break;
    case "-":
      result = first - second; // APPLY: the left operand minus the right one
      break;
    case "*":
      result = first * second; // APPLY: first * second
      break;
    case "/":
      result = first / second; // APPLY: the left over the right, truncated toward zero
      break;
    }
    return result; // RETURN: the value of first operator second
  }

  private static boolean isOperator(String s) {
    return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/"); // TEST: the whole token is one operator
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
