package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

import com.google.common.base.Joiner;

// Sorts the characters of a string using two stacks.
//
// Input: a string. Returns its characters in ascending char order.
//
// 'stack' holds the characters read so far, sorted, the largest on top. A new character that
// is not smaller than the top is pushed. Otherwise every larger character is moved onto 'aux',
// the new one is pushed, and the larger ones are moved back, so 'stack' stays sorted. The
// answer is 'stack' read from bottom to top.

public final class SortStrUsingStack {
  // Time Complexity: O(n^2), a small character can move every character above it off and back
  // Space Complexity: O(n) for the two stacks

  public static String sort(String str) {
    if (str.isEmpty()) {
      return str;
    }
    Deque<Character> stack = new ArrayDeque<>();
    Deque<Character> aux = new ArrayDeque<>();
    stack.push(str.charAt(0));
    for (int i = 1; i < str.length(); i++) {
      char c = str.charAt(i);
      char top = stack.peek();
      if (c >= top) {
        stack.push(c);
      } else {
        while (!stack.isEmpty() && stack.peek() > c) { // uncover the place c belongs
          aux.push(stack.pop());
        }
        stack.push(c);
        while (!aux.isEmpty()) {
          stack.push(aux.pop());
        }
      }
    }
    return Joiner.on("").join(stack.descendingIterator()); // bottom to top
  }

  public static void main(String[] args) {
    String str = "geeksforgeeks";
    System.out.println(sort(str));
  }
}
