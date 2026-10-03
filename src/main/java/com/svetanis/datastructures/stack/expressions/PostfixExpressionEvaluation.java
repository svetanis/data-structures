package com.svetanis.datastructures.stack.expressions;

import static com.svetanis.datastructures.stack.expressions.Expressions.apply;
import static com.svetanis.datastructures.stack.expressions.Expressions.isNumericOperand;
import static com.svetanis.java.base.Exceptions.illegalArgument;
import static java.lang.Character.getNumericValue;

import java.util.ArrayDeque;
import java.util.Deque;

// Evaluate a postfix expression whose operands are single digits.
//
// Given a string such as "231*+9-", which is 2 + 3 * 1 - 9, returns its int value. Every
// operand is ONE digit 0-9, so "12" is the two operands 1 and 2. Spaces are skipped; any
// other character that is not a digit is taken as an operator, one of + - * / ^.
//
// In postfix each operator comes right after its two operands, so one stack of values is
// enough: a digit is pushed; an operator pops the two most recent values, applies itself to
// them, and pushes the result. The value popped first is the right-hand operand.

public final class PostfixExpressionEvaluation {
  // Time Complexity: O(n), each character is pushed or applied once
  // Space Complexity: O(n) for the stack of values

  public static int evaluate(String postfix) {
    int n = postfix.length();
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < n; i++) {
      char c = postfix.charAt(i);
      if (c == ' ') {
        continue;
      }
      if (isNumericOperand(c)) {
        stack.push(getNumericValue(c));
      } else { // operator encountered
        if (stack.size() < 2) { // an operator needs two values before it
          throw illegalArgument("malformed postfix expression: %s", postfix);
        }
        int v1 = stack.pop(); // the right-hand operand: it was pushed last
        int v2 = stack.pop();
        int result = apply(c, v1, v2);
        stack.push(result);
      }
    }
    if (stack.size() != 1) { // a well-formed expression leaves exactly one value
      throw illegalArgument("malformed postfix expression: %s", postfix);
    }
    return stack.peek();
  }

  public static void main(String[] args) {
    System.out.println(evaluate("231*+9-"));
  }
}