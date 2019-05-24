package com.svetanis.datastructures.stack.expressions;

import static com.svetanis.datastructures.stack.expressions.Expressions.endsWith;
import static com.svetanis.datastructures.stack.expressions.Expressions.startsWith;
import static java.lang.Math.max;

import java.util.Stack;

public final class LongestValidSubStrLenEfficient {

  public static int len(String str) {
    // Time Complexity: O(n)

    int n = str.length();
    int max = 0;
    Stack<Integer> stack = new Stack<>();
    stack.push(-1);
    for (int i = 0; i < n; i++) {
      char c = str.charAt(i);
      if (startsWith(c)) {
        stack.push(i);
      } else if (endsWith(c)) {
        stack.pop();
        if (!stack.empty()) {
          int current = i - stack.peek();
          max = max(max, current);
        } else {
          stack.push(i);
        }
      }
    }
    return max;
  }

  public static void main(String[] args) {
    String str1 = "((()"; // 2 ()
    System.out.println(len(str1));

    String str2 = ")()())"; // 4 ()()
    System.out.println(len(str2));

    String str3 = "()(()))))"; // 6 ()(())
    System.out.println(len(str3));
  }
}