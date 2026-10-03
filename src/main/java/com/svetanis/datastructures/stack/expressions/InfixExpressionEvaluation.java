package com.svetanis.datastructures.stack.expressions;

import static com.svetanis.datastructures.stack.expressions.Expressions.apply;
import static com.svetanis.datastructures.stack.expressions.Expressions.endsWith;
import static com.svetanis.datastructures.stack.expressions.Expressions.hasPrecedence;
import static com.svetanis.datastructures.stack.expressions.Expressions.isNumericOperand;
import static com.svetanis.datastructures.stack.expressions.Expressions.isOperator;
import static com.svetanis.datastructures.stack.expressions.Expressions.startsWith;
import static java.lang.Integer.parseInt;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.java.base.Pair;

// Evaluate an infix expression of non-negative integers with + - * / ^ and round brackets.
//
// Given a string such as "100 * ( 2 + 12 )", returns its int value. Division truncates toward
// zero, and ^ groups right to left, so 2 ^ 3 ^ 2 is 2 ^ 9. Only '(' is ever closed: any
// closing bracket works back to the nearest '(', and '[' or '{' is pushed but never matched,
// so an expression using them throws. A leading minus sign, as in "-3 + 4", also throws.
//
// Two stacks. values holds the numbers not yet used by an operator, including results already
// computed; operators holds the operators and '(' still waiting. Before an operator is pushed,
// every waiting operator that binds at least as tightly is applied (Expressions.hasPrecedence),
// so between two brackets the waiting operators bind more tightly from bottom to top. Only ^
// may wait on top of another ^, since ^ groups right to left. A ')' applies everything down to
// its '('.

public final class InfixExpressionEvaluation {
  // Time Complexity: O(n), each character is read once; each operator is pushed and popped once
  // Space Complexity: O(n) for the two stacks

  public static int evaluate(String infix) {
    int n = infix.length();
    Deque<Integer> values = new ArrayDeque<>();
    Deque<Character> operators = new ArrayDeque<>();

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
        i = pair.getRight() - 1; // getRight() is past the number; the loop's i++ moves there
      } else if (startsWith(c)) {
        operators.push(c);
      } else if (endsWith(c)) {
        while (operators.peek() != '(') { // apply everything inside this bracket
          int result = getResult(values, operators);
          values.push(result);
        }
        operators.pop();
      } else if (isOperator(c)) {
        // a waiting operator that binds at least as tightly as c must be applied before c waits
        while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
          int result = getResult(values, operators);
          values.push(result);
        }
        operators.push(c);
      }
    }

    // entire expression has been parsed
    // apply remaining operators to
    // remaining values
    while (!operators.isEmpty()) {
      int result = getResult(values, operators);
      values.push(result);
    }
    // top of values contains result
    return values.peek();
  }

  private static int getResult(Deque<Integer> values, Deque<Character> operators) {
    char op = operators.pop();
    int v1 = values.pop(); // the right-hand operand: it was pushed last
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