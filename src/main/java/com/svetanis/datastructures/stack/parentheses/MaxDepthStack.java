package com.svetanis.datastructures.stack.parentheses;

import static java.lang.Math.max;

import java.util.ArrayDeque;
import java.util.Deque;

// Maximum nesting depth of the round brackets in a string, or -1 when they are not balanced.
// Not a LeetCode problem; 1614 (MaxParenthesesDepth) is the same count on input promised balanced.
//
// Given a string that may hold other characters, returns the largest number of '(' open at
// the same time; 0 when there are none. Returns -1 when a ')' has no open '(' to close, or
// when a '(' is never closed. Every character other than '(' and ')' is skipped.
//
// One entry on the stack is one '(' not yet closed, so the stack's size is the depth at the
// current character, and the answer is the largest size it reaches. MaxDepth replaces the
// stack with a counter, since every entry is the same '('.
//
// Siblings:
//   MaxDepth            -- the same method with a counter in place of the stack, O(1) space
//   MaxParenthesesDepth -- 1614: input promised balanced, so no -1 checks

public final class MaxDepthStack {
  // Time Complexity: O(n), each '(' is pushed once and popped at most once
  // Space Complexity: O(n) for the stack, when every character is '('

  public static int maxDepth(String str) {
    int max = 0; // ANSWER: the deepest nesting seen so far
    int n = str.length();
    Deque<Character> stack = new ArrayDeque<>(); // STACK: '(' open right now
    for (int i = 0; i < n; i++) { // WALK: one character at a time
      char c = str.charAt(i);
      if (c == '(') {
        stack.push(c); // OPEN: one level deeper
        max = max(max, stack.size()); // RECORD: the size is the depth here
      } else if (c == ')') {
        if (stack.isEmpty()) { // FAIL: a ')' with no open '(' to close
          return -1;
        }
        stack.pop(); // CLOSE: one level shallower
      }
    }
    return !stack.isEmpty() ? -1 : max; // RETURN: a '(' left open is unbalanced too
  }

  public static void main(String[] args) {
    String s1 = "( ((X)) (((Y))) )";
    System.out.println(maxDepth(s1)); // 4

    String s2 = "( a(b) (c) (d(e(f)g)h) I (j(k)l)m)";
    System.out.println(maxDepth(s2)); // 4

    String s3 = "( p((q)) ((s)t) )";
    System.out.println(maxDepth(s3)); // 3

    String s4 = "";
    System.out.println(maxDepth(s4)); // 0

    String s5 = "b) (c) ()";
    System.out.println(maxDepth(s5)); // -1

    String s6 = "(b) ((c) ()";
    System.out.println(maxDepth(s6)); // -1
  }
}