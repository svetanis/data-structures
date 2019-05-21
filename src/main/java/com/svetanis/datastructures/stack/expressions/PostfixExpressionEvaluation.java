package com.svetanis.datastructures.stack.expressions;

import static com.svetanis.datastructures.stack.expressions.Expressions.apply;
import static com.svetanis.datastructures.stack.expressions.Expressions.isNumericOperand;
import static java.lang.Character.getNumericValue;

import java.util.Stack;

public final class PostfixExpressionEvaluation {

  public static int evaluate(String postfix) {
    int n = postfix.length();
    Stack<Integer> stack = new Stack<Integer>();
    for (int i = 0; i < n; i++) {
      char c = postfix.charAt(i);
      if (isNumericOperand(c)) {
        stack.push(getNumericValue(c));
      } else { // operator encountered
        int v1 = stack.pop();
        int v2 = stack.pop();
        int result = apply(c, v1, v2);
        stack.push(result);
      }
    }
    // top of values contains result
    return stack.peek();
  }

  public static void main(String[] args) {
    System.out.println(evaluate("231*+9-"));
  }
}