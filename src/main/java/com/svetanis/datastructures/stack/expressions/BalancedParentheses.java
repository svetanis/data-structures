package com.svetanis.datastructures.stack.expressions;

import static com.svetanis.datastructures.stack.expressions.Expressions.endsWith;
import static com.svetanis.datastructures.stack.expressions.Expressions.isMatch;
import static com.svetanis.datastructures.stack.expressions.Expressions.startsWith;

import java.util.Stack;

public final class BalancedParentheses {

  public static boolean isBalanced(String str) {
    // Time Complexity: O(n)
    // Space Complexity: O(n)

    Stack<Character> stack = new Stack<Character>();
    for (char c : str.toCharArray()) {
      if (startsWith(c)) {
        stack.push(c);
      }
      if (endsWith(c)) {
        if (stack.empty()) {
          return false;
        } else if (!isMatch(stack.pop(), c)) {
          return false;
        }
      }
    }
    return stack.empty();
  }

  public static void main(String[] args) {
    String str1 = "{()}[]";
    System.out.println(isBalanced(str1));

    String str2 = "[()]{}{[()()]()}";
    System.out.println(isBalanced(str2));

    String str3 = "[(])";
    System.out.println(isBalanced(str3));
  }
}
