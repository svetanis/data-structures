package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 224. Basic Calculator

public final class BasicCalculator224 {
  // Time Complexity: O(n)
  // Space Complexity: O(n)

  public static int calculate(String s) {
    int sign = 1;
    int result = 0;
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (Character.isDigit(c)) {
        int[] pair = pair(s, i);
        result += sign * pair[0];
        i = pair[1] - 1;
      } else {
        switch (c) {
        case '+':
          sign = 1;
          break;
        case '-':
          sign = -1;
          break;
        case '(':
          stack.push(result);
          stack.push(sign);
          result = 0;
          sign = 1;
          break;
        case ')':
          result = stack.pop() * result + stack.pop();
          break;
        }
      }
    }
    return result;
  }

  private static int[] pair(String s, int i) {
    int val = 0;
    int start = i;
    while (start < s.length() && Character.isDigit(s.charAt(start))) {
      val = val * 10 + s.charAt(start) - '0';
      start++;
    }
    return new int[] { val, start };
  }

  public static void main(String[] args) {
    System.out.println(calculate("1 + 1")); // 2
    System.out.println(calculate("2 - 1 + 2")); // 3
    System.out.println(calculate("(1 + (4 + 5 + 2) - 3) + (6 + 8)")); // 23
  }
}
