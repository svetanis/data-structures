package com.svetanis.datastructures.stack.parentheses;

// 1614. Maximum Nesting Depth of the Parentheses
//
// Input: a string of balanced parentheses mixed with digits and operators. Returns the
// largest number of '(' that are open at the same moment.
//
// No stack is needed: only how many '(' are open matters, not which ones, so one counter
// stands in for the size of a stack. The answer is the highest value that counter reaches.

public final class MaxParenthesesDepth {
  // Time Complexity: O(n), one pass over the characters
  // Space Complexity: O(n), toCharArray copies the string; the counting itself is O(1)

  public static int maxDepth(String s) {
    int depth = 0;
    int maxDepth = 0;
    for (char c : s.toCharArray()) {
      if (c == '(') {
        depth += 1;
        maxDepth = Math.max(maxDepth, depth); // depth only rises here, so check it here
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
