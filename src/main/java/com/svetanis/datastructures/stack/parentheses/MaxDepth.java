package com.svetanis.datastructures.stack.parentheses;

import static java.lang.Math.max;

// Maximum nesting depth of the round brackets in a string, or -1 when they are not balanced.
//
// Given a string that may hold other characters, returns the largest number of '(' open at
// the same time; 0 when there are none. Returns -1 when a ')' has no open '(' to close, or
// when a '(' is never closed. Every character other than '(' and ')' is skipped.
//
// With one bracket type, a stack of the open '(' is fully described by its size, so a counter
// replaces it: count is how many '(' are open right now, and the answer is its largest value.
// MaxDepthStack is the same method with the stack kept.

public final class MaxDepth {
  // Time Complexity: O(n), one pass over the string
  // Space Complexity: O(1), one counter instead of a stack

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
        if (count <= 0) { // a ')' with no open '(' to close
          return -1;
        }
        count--;
      }
    }
    return count != 0 ? -1 : max; // a '(' left open is unbalanced too
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