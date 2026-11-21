package com.svetanis.datastructures.stack.expressions;

import static java.lang.Character.isDigit;

// Shared helpers for the expression evaluators in this package: which characters are
// operators, digits and brackets, how tightly each operator binds, and how to apply one.
// Used by InfixExpressionEvaluation, PostfixExpressionEvaluation, BasicCalculatorII and
// BasicCalculatorIIApply.
//
// Not a LeetCode problem: a helper class with no problem of its own. Each method takes one
// character (apply also takes two operands) and returns a yes/no, a rank or a value.
//
// The one idea: the evaluators differ in how they walk the string, but agree on what each
// operator means, so those rules live here once. apply takes its operands in the order a
// stack hands them back -- right-hand operand first.
//
// Siblings: BasicCalculatorIII keeps its own private copies of isOperator, precedence and apply,
// for + - * / only, with the operands in the order a, b (left first).

public final class Expressions {
  // Time Complexity: O(1) per call, except power: O(log exponent), one squaring per bit
  // Space Complexity: O(1)

  // true for the five operators + - * / ^
  public static boolean isOperator(char c) {
    boolean one = c == '+'; // TEST: plus
    boolean two = c == '-'; // TEST: minus
    boolean three = c == '*'; // TEST: times
    boolean four = c == '/'; // TEST: divide
    boolean five = c == '^'; // TEST: power
    return one || two || three || four || five; // RETURN: any of the five
  }

  // true for one digit character; a number of several digits is read by the caller
  public static boolean isNumericOperand(char c) {
    return isDigit(c); // TEST: 0 to 9
  }

  // a and b come off a stack of values: a was popped first, so it is the right-hand operand
  // and b the left-hand one. '-' gives b - a; '/' gives b / a, truncated toward zero.
  public static int apply(char c, int a, int b) {
    switch (c) {
    case '+':
      return b + a; // APPLY: b + a
    case '-':
      return b - a; // APPLY: the left-hand b minus the right-hand a
    case '*':
      return b * a; // APPLY: b * a
    case '/':
      if (a == 0) {
        throw new IllegalArgumentException("division by zero"); // REJECT: no value to return
      }
      return b / a; // APPLY: the left-hand b over the right-hand a, truncated toward zero
    case '^':
      return power(b, a); // APPLY: b was pushed first, so b is the base: 2 3 ^ is 2^3
    default:
      throw new IllegalArgumentException(String.format("unsupported operator --> %s", c)); // REJECT: not an operator
    }
  }

  // base to the power exponent, by repeated squaring. Math.multiplyExact throws
  // ArithmeticException when a product leaves the int range, where (int) Math.pow would
  // quietly return Integer.MAX_VALUE or MIN_VALUE. A square is taken only while bits remain,
  // and those bits multiply it into the result, so it never overflows when the answer fits.
  private static int power(int base, int exponent) {
    if (exponent < 0) {
      throw new IllegalArgumentException(String.format("negative exponent %s", exponent)); // REJECT: not an int
    }
    int result = 1; // START: base to the power 0
    int square = base; // SQUARE: base to the power 1, 2, 4, 8 ... in turn
    for (int e = exponent; e > 0; e >>= 1) { // WALK: one bit of the exponent at a time, lowest first
      if ((e & 1) == 1) {
        result = Math.multiplyExact(result, square); // TAKE: this bit is set, so its square counts
      }
      if (e > 1) {
        square = Math.multiplyExact(square, square); // SQUARE: only while a higher bit remains
      }
    }
    return result; // RETURN: base to the power exponent
  }

  // how tightly an operator binds, a higher number binding tighter:
  // ^ 3, * and / 2, + and - 1; anything else -1
  public static int precedence(char c) {
    switch (c) {
    case '+':
    case '-':
      return 1; // RANK: + and - bind loosest
    case '*':
    case '/':
      return 2; // RANK: * and / bind tighter
    case '^':
      return 3; // RANK: ^ binds tightest
    default:
      return -1; // NONE: not an operator
    }
  }

  // true when c2, the operator already on the stack, must be applied before c1 is pushed:
  // it binds tighter, or equally tight and c1 groups left to right. ^ groups right to left,
  // so 2 ^ 3 ^ 2 is 2 ^ 9. A bracket on the stack is never applied.
  public static boolean hasPrecedence(char c1, char c2) {
    if (startsWith(c2) || endsWith(c2)) {
      return false; // BRACKET: a bracket on the stack is never applied
    }
    int stacked = precedence(c2); // RANK: the operator waiting on the stack
    int incoming = precedence(c1); // RANK: the operator about to be pushed
    return stacked > incoming || stacked == incoming && c1 != '^'; // RETURN: tighter, or equal and not ^
  }

  // true for an opening bracket: ( [ {
  public static boolean startsWith(char c) {
    return c == '{' || c == '[' || c == '('; // TEST: an opener
  }

  // true for a closing bracket: ) ] }
  public static boolean endsWith(char c) {
    return c == '}' || c == ']' || c == ')'; // TEST: a closer
  }

}
