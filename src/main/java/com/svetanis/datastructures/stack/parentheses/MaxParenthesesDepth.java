package com.svetanis.datastructures.stack.parentheses;

// 1614. Maximum Nesting Depth of the Parentheses
//
// Input: a string of balanced parentheses mixed with digits and operators. Returns the
// largest number of '(' that are open at the same moment.
//
// No stack is needed: only how many '(' are open matters, not which ones, so one counter
// stands in for the size of a stack. The answer is the highest value that counter reaches.
//
// Siblings:
//   MaxDepth      -- not LeetCode: the same counter on input that may be unbalanced; returns -1 then
//   MaxDepthStack -- MaxDepth with a stack of the open '(' in place of the counter

public final class MaxParenthesesDepth {
  // Time Complexity: O(n), one pass over the characters
  // Space Complexity: O(n), toCharArray copies the string; the counting itself is O(1)

  public static int maxDepth(String s) {
    int depth = 0; // COUNT: '(' open right now
    int maxDepth = 0; // ANSWER: the deepest nesting seen so far
    for (char c : s.toCharArray()) { // WALK: one character at a time
      if (c == '(') {
        depth += 1; // OPEN: one level deeper
        maxDepth = Math.max(maxDepth, depth); // RECORD: depth only rises here, so check it here
      } else if (c == ')') {
        depth -= 1; // CLOSE: one level shallower
      }
    }
    return maxDepth; // RETURN: the deepest nesting
  }

  public static void main(String[] args) {
    System.out.println(maxDepth("(1+(2*3)+((8)/4))+1")); // 3
    System.out.println(maxDepth("(1)+((2))+(((3)))")); // 3
    System.out.println(maxDepth("()(())((()()))")); // 3
  }
}
