package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

// Sorts the characters of a string using two stacks. Not a LeetCode problem.
//
// Input: a string. Returns its characters in ascending char order.
//
// 'stack' holds the characters read so far, sorted, the largest on top. A new character that
// is not smaller than the top is pushed. Otherwise every larger character is moved onto 'aux',
// the new one is pushed, and the larger ones are moved back, so 'stack' stays sorted. The
// answer is 'stack' read from bottom to top.
//
// Siblings, the same insertion on a stack of ints:
//   StackSortAscendingIterative  sends the larger values back to the INPUT stack instead of
//                                to a holding stack, so they are re-read rather than replaced
//   StackSortAscendingRecursive  holds the larger values in the method calls, not in aux

public final class SortStrUsingStack {
  // Time Complexity: O(n^2), a small character can move every character above it off and back
  // Space Complexity: O(n) for the two stacks

  public static String sort(String str) {
    if (str.isEmpty()) { // EMPTY: nothing to seed the stack with
      return str;
    }
    Deque<Character> stack = new ArrayDeque<>();
    Deque<Character> aux = new ArrayDeque<>();
    stack.push(str.charAt(0)); // SEED: one character is already sorted
    for (int i = 1; i < str.length(); i++) { // WALK: each later character
      char c = str.charAt(i);
      char top = stack.peek();
      if (c >= top) {
        stack.push(c); // PUSH: not smaller than the largest so far, it goes on top
      } else {
        while (!stack.isEmpty() && stack.peek() > c) { // LIFT: uncover the place c belongs
          aux.push(stack.pop());
        }
        stack.push(c); // PLACE: c lands above everything not larger than it
        while (!aux.isEmpty()) {
          stack.push(aux.pop()); // RESTORE: the larger ones go back above c
        }
      }
    }
    return join(stack.descendingIterator()); // READ: bottom to top
  }

  private static String join(Iterator<Character> it) {
    StringBuilder sb = new StringBuilder();
    it.forEachRemaining(sb::append); // JOIN: the characters, in iterator order
    return sb.toString();
  }

  public static void main(String[] args) {
    String str = "geeksforgeeks";
    System.out.println(sort(str));
  }
}
