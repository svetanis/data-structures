package com.svetanis.datastructures.stack.expressions;

import static com.svetanis.datastructures.stack.expressions.Expressions.apply;
import static com.svetanis.datastructures.stack.expressions.Expressions.endsWith;
import static com.svetanis.datastructures.stack.expressions.Expressions.hasPrecedence;
import static com.svetanis.datastructures.stack.expressions.Expressions.isNumericOperand;
import static com.svetanis.datastructures.stack.expressions.Expressions.isOperator;
import static com.svetanis.datastructures.stack.expressions.Expressions.startsWith;
import static java.lang.Integer.parseInt;

import java.util.Stack;

import com.svetanis.java.base.Pair;

public final class InfixExpressionEvaluation {

  public static int evaluate(String infix) {
    int n = infix.length();
    Stack<Integer> values = new Stack<>();
    Stack<Character> operators = new Stack<>();

    for (int i = 0; i < n; i++) {
      char c = infix.charAt(i);
      // current token is whitespace
      if (c == ' ') {
        continue;
      }

      if (isNumericOperand(c)) {
        // if > 1 digit in the number
        Pair<Integer, Integer> pair = getNum(infix, i);
        values.push(pair.getLeft());
        i = pair.getRight();
      } else if (startsWith(c)) {
        operators.push(c);
      } else if (endsWith(c)) {
        while (operators.peek() != '(') {
          int result = getResult(values, operators);
          values.push(result);
        }
        operators.pop();
      } else if (isOperator(c)) {
        while (!operators.empty() && hasPrecedence(c, operators.peek())) {
          int result = getResult(values, operators);
          values.push(result);
        }
        operators.push(c);
      }
    }

    // entire expression has been parsed
    // apply remaining operators to
    // remaining values
    while (!operators.empty()) {
      int result = getResult(values, operators);
      values.push(result);
    }
    // top of values contains result
    return values.peek();
  }

  private static int getResult(Stack<Integer> values, Stack<Character> operators) {
    char op = operators.pop();
    int v1 = values.pop();
    int v2 = values.pop();
    return apply(op, v1, v2);
  }

  private static Pair<Integer, Integer> getNum(String str, int i) {
    int n = str.length();
    StringBuilder sb = new StringBuilder();
    while (i < n && isNumericOperand(str.charAt(i))) {
      sb.append(str.charAt(i));
      i++;
    }
    return Pair.build(parseInt(sb.toString()), i);
  }

  public static void main(String[] args) {
    System.out.println(evaluate("10 + 2 * 6"));
    System.out.println(evaluate("100 * 2 + 12"));
    System.out.println(evaluate("100 * ( 2 + 12 )"));
    System.out.println(evaluate("100 * ( 2 + 12 ) / 14"));
  }
}