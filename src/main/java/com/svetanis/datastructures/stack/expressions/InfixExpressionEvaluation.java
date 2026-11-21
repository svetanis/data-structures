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

// Evaluate an infix expression of non-negative integers with + - * / ^ and round brackets.
// Not a LeetCode problem; 772. Basic Calculator III is the same task without ^.
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
//
// Siblings:
//   BasicCalculatorIII          -- 772: the same two stacks for + - * / only, rules kept in the file
//   PostfixExpressionEvaluation -- the same operators already in postfix order, so one stack is enough
//   Expressions                 -- the operator rules this file uses

public final class InfixExpressionEvaluation {
  // Time Complexity: O(n), each character is read once; each operator is pushed and popped once
  // Space Complexity: O(n) for the two stacks

  public static int evaluate(String infix) {
    int n = infix.length();
    Deque<Integer> values = new ArrayDeque<>(); // STACK: numbers, and results not used yet
    Deque<Character> operators = new ArrayDeque<>(); // STACK: operators and '(' not applied yet

    for (int i = 0; i < n; i++) { // WALK: one character, or one whole number
      char c = infix.charAt(i);
      // current token is whitespace
      if (c == ' ') {
        continue; // SKIP: a space
      }

      if (isNumericOperand(c)) {
        // if > 1 digit in the number
        Pair<Integer, Integer> pair = getNum(infix, i); // READ: the whole number that starts at i
        values.push(pair.getLeft()); // PUSH: the number waits for its operator
        i = pair.getRight() - 1; // JUMP: getRight() is past the number; the loop's i++ moves there
      } else if (startsWith(c)) {
        operators.push(c); // OPEN: nothing above it is applied past it
      } else if (endsWith(c)) {
        while (operators.peek() != '(') { // CLOSE: apply everything inside this bracket
          int result = getResult(values, operators); // APPLY: the top operator to the top two numbers
          values.push(result); // PUSH: the result is one number again
        }
        operators.pop(); // CLOSE: drop the '(' itself
      } else if (isOperator(c)) {
        // a waiting operator that binds at least as tightly as c must be applied before c waits
        while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) { // APPLY: the waiting operator goes first
          int result = getResult(values, operators); // APPLY: the top operator to the top two numbers
          values.push(result); // PUSH: the result is one number again
        }
        operators.push(c); // PUSH: c waits for its right operand
      }
    }

    // entire expression has been parsed
    // apply remaining operators to
    // remaining values
    while (!operators.isEmpty()) { // FINISH: apply what still waits, top first
      int result = getResult(values, operators); // APPLY: the top operator to the top two numbers
      values.push(result); // PUSH: the result is one number again
    }
    // top of values contains result
    return values.peek(); // RETURN: the one number left
  }

  private static int getResult(Deque<Integer> values, Deque<Character> operators) {
    char op = operators.pop(); // POP: the operator to apply
    int v1 = values.pop(); // POP: the right-hand operand: it was pushed last
    int v2 = values.pop(); // POP: the left-hand operand
    return apply(op, v1, v2); // APPLY: v2 op v1
  }

  private static Pair<Integer, Integer> getNum(String str, int i) {
    int n = str.length();
    StringBuilder sb = new StringBuilder(); // READ: the digits of one number
    while (i < n && isNumericOperand(str.charAt(i))) { // WALK: up to the first non-digit
      sb.append(str.charAt(i)); // READ: one more digit
      i++;
    }
    return Pair.build(parseInt(sb.toString()), i); // RETURN: the number, and the position just past it
  }

  // two values carried together: here a number and the position just past it
  private static final class Pair<L, R> {
    private final L left;
    private final R right;

    private Pair(L left, R right) {
      this.left = left;
      this.right = right;
    }

    static <L, R> Pair<L, R> build(L left, R right) {
      return new Pair<>(left, right);
    }

    L getLeft() {
      return left;
    }

    R getRight() {
      return right;
    }
  }

  public static void main(String[] args) {
    System.out.println(evaluate("10 + 2 * 6")); // 22
    System.out.println(evaluate("100 * 2 + 12")); // 212
    System.out.println(evaluate("100 * ( 2 + 12 )")); // 1400
    System.out.println(evaluate("100 * ( 2 + 12 ) / 14")); // 100
  }
}