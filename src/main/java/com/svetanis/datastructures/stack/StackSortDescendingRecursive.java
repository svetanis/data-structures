package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

// Sorts a stack in place, recursively: largest value at the bottom, smallest on top.
// Not a LeetCode problem.
//
// Input: a stack. It is sorted in place; nothing is returned.
//
// sort pops the top value, sorts the rest, then inserts the popped value into the sorted rest.
// sortedInsert pops every top value that is not larger than x, pushes x, and pushes the popped
// values back as the calls return, so the stack is sorted after every insert. The values
// waiting to go back are held in the method calls themselves.
//
// Siblings, same task:
//   StackSortAscendingRecursive  the same recursion with the comparison flipped, smallest at bottom
//   StackSortAscendingIterative  a second stack holds the sorted values instead of the calls

public final class StackSortDescendingRecursive {
  // Time Complexity: O(n^2), each of the n inserts can pop and re-push every value already sorted
  // Space Complexity: O(n), at most n sort calls plus n sortedInsert calls are open at once

  public static void sort(Deque<Integer> stack) {
    if (!stack.isEmpty()) { // STOP: an empty stack is sorted
      int temp = stack.pop(); // HOLD: the top waits in this call
      sort(stack); // SORT: the rest
      sortedInsert(stack, temp); // INSERT: the held value into the sorted rest
    }
  }

  private static void sortedInsert(Deque<Integer> stack, int x) {
    if (stack.isEmpty() || stack.peek() > x) { // PLACE: x is smaller than the top: it goes on top
      stack.push(x);
      return;
    }
    int temp = stack.pop(); // HOLD: a value not larger than x waits in the call
    sortedInsert(stack, x); // DIG: one level further down
    stack.push(temp); // RESTORE: back above x
  }

  public static void main(String[] args) {
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(9);
    stack.push(1);
    stack.push(5);
    stack.push(12);
    System.out.println(stack.reversed()); // bottom to top
    sort(stack);
    System.out.println(stack.reversed()); // bottom to top
  }
}