package com.svetanis.datastructures.stack.expressions;

import static java.lang.Math.max;

public final class MaxDepth {

  public static int maxDepth(String str) {
    int max = 0;
    int count = 0;
    int n = str.length();
    for (int i = 0; i < n; i++) {
      char c = str.charAt(i);
      if (c == '(') {
        count++;
        max = max(max, count);
      } else if (c == ')') {
        if (count <= 0) {
          return -1;
        }
        count--;
      }
    }
    return count != 0 ? -1 : max;
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