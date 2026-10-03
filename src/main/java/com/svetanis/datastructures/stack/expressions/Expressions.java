package com.svetanis.datastructures.stack.expressions;

import static com.svetanis.java.base.Exceptions.illegalArgument;
import static java.lang.Character.isDigit;

// Shared helpers for the expression evaluators in this package: which characters are
// operators, digits and brackets, how tightly each operator binds, and how to apply one.
// Used by InfixExpressionEvaluation, PostfixExpressionEvaluation, BasicCalculatorII and
// BasicCalculatorIIApply.

public final class Expressions {

  // true for the five operators + - * / ^
  public static boolean isOperator(char c) {
    boolean one = c == '+';
    boolean two = c == '-';
    boolean three = c == '*';
    boolean four = c == '/';
    boolean five = c == '^';
    return one || two || three || four || five;
  }

  // true for one digit character; a number of several digits is read by the caller
  public static boolean isNumericOperand(char c) {
    return isDigit(c);
  }

  // a and b come off a stack of values: a was popped first, so it is the right-hand operand
  // and b the left-hand one. '-' gives b - a; '/' gives b / a, truncated toward zero.
  public static int apply(char c, int a, int b) {
    switch (c) {
    case '+':
      return b + a;
    case '-':
      return b - a;
    case '*':
      return b * a;
    case '/':
      if (a == 0) {
        throw illegalArgument("division by zero");
      }
      return b / a;
    case '^':
      return power(b, a); // b was pushed first, so b is the base: 2 3 ^ is 2^3
    default:
      throw illegalArgument("unsupported operator --> %s", c);
    }
  }

  // base to the power exponent, by repeated squaring. Math.multiplyExact throws
  // ArithmeticException when a product leaves the int range, where (int) Math.pow would
  // quietly return Integer.MAX_VALUE or MIN_VALUE. A square is taken only while bits remain,
  // and those bits multiply it into the result, so it never overflows when the answer fits.
  private static int power(int base, int exponent) {
    if (exponent < 0) {
      throw illegalArgument("negative exponent %s", exponent);
    }
    int result = 1;
    int square = base;
    for (int e = exponent; e > 0; e >>= 1) {
      if ((e & 1) == 1) {
        result = Math.multiplyExact(result, square);
      }
      if (e > 1) {
        square = Math.multiplyExact(square, square);
      }
    }
    return result;
  }

  // how tightly an operator binds, a higher number binding tighter:
  // ^ 3, * and / 2, + and - 1; anything else -1
  public static int precedence(char c) {
    switch (c) {
    case '+':
    case '-':
      return 1;
    case '*':
    case '/':
      return 2;
    case '^':
      return 3;
    default:
      return -1;
    }
  }

  // true when c2, the operator already on the stack, must be applied before c1 is pushed:
  // it binds tighter, or equally tight and c1 groups left to right. ^ groups right to left,
  // so 2 ^ 3 ^ 2 is 2 ^ 9. A bracket on the stack is never applied.
  public static boolean hasPrecedence(char c1, char c2) {
    if (startsWith(c2) || endsWith(c2)) {
      return false;
    }
    int stacked = precedence(c2);
    int incoming = precedence(c1);
    return stacked > incoming || stacked == incoming && c1 != '^';
  }

  // true for an opening bracket: ( [ {
  public static boolean startsWith(char c) {
    return c == '{' || c == '[' || c == '(';
  }

  // true for a closing bracket: ) ] }
  public static boolean endsWith(char c) {
    return c == '}' || c == ']' || c == ')';
  }

}
