package com.svetanis.datastructures.stack.expressions;

import static java.lang.Math.max;

import java.util.Stack;

public final class MaxDepthStack {

  public static int maxDepth(String str) {
    int max = 0;
    int n = str.length();
    Stack<Character> stack = new Stack<Character>();
    for (int i = 0; i < n; i++) {
      char c = str.charAt(i);
      if (c == '(') {
        stack.push(c);
        max = max(max, stack.size());
      } else if (c == ')') {
        if (stack.empty()) {
          return -1;
        }
        stack.pop();
      }
    }
    return !stack.empty() ? -1 : max;
  }

  public static void main(String[] args) {
    String s1 = "( ((X)) (((Y))) )";
    System.out.println(maxDepth(s1));

    String s2 = "( a(b) (c) (d(e(f)g)h) I (j(k)l)m)";
    System.out.println(maxDepth(s2));

    String s3 = "( p((q)) ((s)t) )";
    System.out.println(maxDepth(s3));

    String s4 = "";
    System.out.println(maxDepth(s4));

    String s5 = "b) (c) ()";
    System.out.println(maxDepth(s5));

    String s6 = "(b) ((c) ()";
    System.out.println(maxDepth(s6));
  }
}