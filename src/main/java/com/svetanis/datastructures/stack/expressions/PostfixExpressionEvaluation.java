package com.svetanis.datastructures.stack.expressions;

import static com.svetanis.datastructures.stack.expressions.Expressions.apply;
import static com.svetanis.datastructures.stack.expressions.Expressions.isNumericOperand;
import static java.lang.Character.getNumericValue;

import java.util.ArrayDeque;
import java.util.Deque;

// Evaluate a postfix expression whose operands are single digits.
// Not a LeetCode problem; 150. Evaluate Reverse Polish Notation is the same task on tokens.
//
// Given a string such as "231*+9-", which is 2 + 3 * 1 - 9, returns its int value. Every
// operand is ONE digit 0-9, so "12" is the two operands 1 and 2. Spaces are skipped; any
// other character that is not a digit is taken as an operator, one of + - * / ^.
//
// In postfix each operator comes right after its two operands, so one stack of values is
// enough: a digit is pushed; an operator pops the two most recent values, applies itself to
// them, and pushes the result. The value popped first is the right-hand operand.
//
// Siblings:
//   ReversePolishNotation150  -- 150: the same stack on whole tokens, so numbers of several digits
//                                and negative numbers; + - * / only
//   InfixExpressionEvaluation -- the same operators written in infix, so a second stack holds them

public final class PostfixExpressionEvaluation {
  // Time Complexity: O(n), each character is pushed or applied once
  // Space Complexity: O(n) for the stack of values

  public static int evaluate(String postfix) {
    int n = postfix.length();
    Deque<Integer> stack = new ArrayDeque<>(); // STACK: values not used by an operator yet
    for (int i = 0; i < n; i++) { // WALK: one character at a time
      char c = postfix.charAt(i);
      if (c == ' ') {
        continue; // SKIP: a space
      }
      if (isNumericOperand(c)) {
        stack.push(getNumericValue(c)); // PUSH: one digit is one operand
      } else { // APPLY: operator encountered
        if (stack.size() < 2) { // REJECT: an operator needs two values before it
          throw new IllegalArgumentException(String.format("malformed postfix expression: %s", postfix));
        }
        int v1 = stack.pop(); // POP: the right-hand operand: it was pushed last
        int v2 = stack.pop(); // POP: the left-hand operand
        int result = apply(c, v1, v2); // APPLY: v2 c v1
        stack.push(result); // PUSH: the result is one value again
      }
    }
    if (stack.size() != 1) { // REJECT: a well-formed expression leaves exactly one value
      throw new IllegalArgumentException(String.format("malformed postfix expression: %s", postfix));
    }
    return stack.peek(); // RETURN: the one value left
  }

  public static void main(String[] args) {
    System.out.println(evaluate("231*+9-")); // -4
  }
}