package com.svetanis.datastructures.stack.expressions;

import static com.svetanis.java.base.Exceptions.illegalArgument;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetterOrDigit;
import static java.lang.Math.pow;

public final class Expressions {

  public static boolean isOperator(char c) {
    boolean one = c == '+';
    boolean two = c == '-';
    boolean three = c == '*';
    boolean four = c == '/';
    boolean five = c == '^';
    return one || two || three || four || five;
  }

  public static boolean isOperator(String str) {
    return isOperator(str.charAt(0));
  }

  public static boolean isOperand(char c) {
    return isLetterOrDigit(c);
  }

  public static boolean isNumericOperand(char c) {
    return isDigit(c);
  }

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
      return (int) pow(a, b);
    default:
      throw illegalArgument("unsupported operator --> %s", c);
    }
  }

  public static double apply(char c, double a, double b) {
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
      return (int) pow(a, b);
    default:
      throw illegalArgument("unsupported operator --> %s", c);
    }
  }

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

  public static boolean hasPrecedence(char c1, char c2) {
    if (c2 == '(' || c2 == ')') {
      return false;
    }

    boolean one = c1 == '*' || c1 == '/';
    boolean two = c2 == '+' || c2 == '-';
    if (one && two) {
      return false;
    } else {
      return true;
    }
  }

  public static boolean isMatch(char c1, char c2) {
    if (c1 == '{' && c2 == '}') {
      return true;
    } else if (c1 == '[' && c2 == ']') {
      return true;
    } else if (c1 == '(' && c2 == ')') {
      return true;
    } else {
      return false;
    }
  }

  public static boolean startsWith(char c) {
    return c == '{' || c == '[' || c == '(';
  }

  public static boolean endsWith(char c) {
    return c == '}' || c == ']' || c == ')';
  }

}
