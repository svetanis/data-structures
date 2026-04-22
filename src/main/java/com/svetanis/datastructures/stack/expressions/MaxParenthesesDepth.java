package com.svetanis.datastructures.stack.expressions;

import java.util.ArrayDeque;
import java.util.Deque;

// 1614. Maximum Nesting Depth of the Parentheses

public final class MaxParenthesesDepth {
  // Time Complexity: O(n)
  // Space Complexity: O(1)

  public static int maxDepth(String s) {
    int depth = 0;
    int maxDepth = 0;
    Deque<Character> dq = new ArrayDeque<>();
    for (char c : s.toCharArray()) {
      if (c == '(') {
        depth += 1;
        maxDepth = Math.max(maxDepth, depth);
      } else if (c == ')') {
        depth -= 1;
      }
    }
    return maxDepth;
  }

  public static void main(String[] args) {
    System.out.println(maxDepth("(1+(2*3)+((8)/4))+1")); // 3
    System.out.println(maxDepth("(1)+((2))+(((3)))")); // 3
    System.out.println(maxDepth("()(())((()()))")); // 3
  }
}
