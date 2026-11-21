package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 224. Basic Calculator
//
// s holds non-negative integers, '+', '-', brackets and spaces; a '-' may also negate what
// follows it. Returns the value of s.
//
// With only + and -, each number is added to a running result with a sign of +1 or -1.
// A bracket starts a fresh result: '(' pushes the result so far, then the sign in front of the
// bracket; ')' multiplies the bracket's result by that sign and adds the saved result back.
// So the stack holds two entries per open bracket: the result before it, and its sign.
//
// Siblings -- the same family of calculators:
//   BasicCalculator224Submit  -- 224 again, same stack; builds each number digit by digit and
//                                adds it when the next operator or ')' ends it
//   BasicCalculatorII         -- 227: + - * / with no brackets; one stack of signed numbers
//   BasicCalculatorIIApply    -- 227 again, the last number applied after the loop
//   BasicCalculatorIII        -- 772: + - * / and brackets; a stack of numbers and one of operators
//   InfixExpressionEvaluation -- not LeetCode: 772 plus ^, with the operator rules in Expressions

public final class BasicCalculator224 {
  // Time Complexity: O(n), each character is read once
  // Space Complexity: O(n), two stack entries per open bracket

  public static int calculate(String s) {
    int sign = 1; // SIGN: the sign of the next number; '+' until a '-' says otherwise
    int result = 0; // START: the running result of the innermost open bracket
    Deque<Integer> stack = new ArrayDeque<>(); // STACK: two entries per open bracket
    for (int i = 0; i < s.length(); i++) { // WALK: one character, or one whole number
      char c = s.charAt(i);
      if (Character.isDigit(c)) {
        int[] pair = pair(s, i); // READ: the whole number that starts at i
        result += sign * pair[0]; // ADD: the number, with the sign in front of it
        i = pair[1] - 1; // JUMP: pair[1] is just past the number; the loop's i++ lands on it
      } else {
        switch (c) {
        case '+':
          sign = 1; // SIGN: the next number is added
          break;
        case '-':
          sign = -1; // SIGN: the next number, or the next bracket, is subtracted
          break;
        case '(':
          stack.push(result); // OPEN: save the result before the bracket
          stack.push(sign); // OPEN: then the sign in front of the bracket
          result = 0; // START: the bracket's own result
          sign = 1; // SIGN: the bracket's first number is added
          break;
        case ')':
          result = stack.pop() * result + stack.pop(); // CLOSE: the sign was pushed last, so it pops first
          break;
        }
      }
    }
    return result; // RETURN: every bracket is closed, so this is the value of s
  }

  private static int[] pair(String s, int i) {
    int val = 0; // READ: the value of the digits read so far
    int start = i;
    while (start < s.length() && Character.isDigit(s.charAt(start))) { // WALK: up to the first non-digit
      val = val * 10 + s.charAt(start) - '0'; // READ: one more digit
      start++;
    }
    return new int[] { val, start }; // RETURN: the number, and the position just past it
  }

  public static void main(String[] args) {
    System.out.println(calculate("1 + 1")); // 2
    System.out.println(calculate("2 - 1 + 2")); // 3
    System.out.println(calculate("(1 + (4 + 5 + 2) - 3) + (6 + 8)")); // 23
  }
}
