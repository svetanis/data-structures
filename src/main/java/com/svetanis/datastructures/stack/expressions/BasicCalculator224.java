package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 224. Basic Calculator
//
// s holds non-negative integers, '+', '-', brackets and spaces; a '-' may also negate what
// follows it. Returns the value of s.
//
// With only + and -, each number is added to a running result with a sign of +1 or -1.
// A bracket starts a fresh result: '(' pushes the result so far, then the sign in front of the
// bracket; ')' multiplies the bracket's result by that sign and adds the saved result back.
// So the stack holds two entries per open bracket: the result before it, and its sign.

public final class BasicCalculator224 {
  // Time Complexity: O(n), each character is read once
  // Space Complexity: O(n), two stack entries per open bracket

  public static int calculate(String s) {
    int sign = 1;
    int result = 0;
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (Character.isDigit(c)) {
        int[] pair = pair(s, i);
        result += sign * pair[0];
        i = pair[1] - 1; // pair[1] is just past the number; the loop's i++ lands on it
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
          result = stack.pop() * result + stack.pop(); // the sign was pushed last, so it pops first
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
