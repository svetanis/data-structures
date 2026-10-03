package com.svetanis.datastructures.stack;

import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.Deque;

import com.google.common.collect.ImmutableList;

// Sorts a stack in place, recursively: smallest value at the bottom, largest on top.
//
// Input: a stack. It is sorted in place; nothing is returned.
//
// sort pops the top value, sorts the rest, then inserts the popped value into the sorted rest.
// sortedInsert pops every top value that is not smaller than x, pushes x, and pushes the popped
// values back as the calls return, so the stack is sorted after every insert. The values
// waiting to go back are held in the method calls themselves.

public final class StackSortAscendingRecursive {
  // Time Complexity: O(n^2), each of the n inserts can pop and re-push every value already sorted
  // Space Complexity: O(n), at most n sort calls plus n sortedInsert calls are open at once

  public static void sort(Deque<Integer> stack) {
    if (!stack.isEmpty()) {
      int temp = stack.pop();
      sort(stack);
      sortedInsert(stack, temp);
    }
  }

  private static void sortedInsert(Deque<Integer> stack, int x) {
    // base case: either stack is empty or
    // newly inserted element is greater than top
    if (stack.isEmpty() || stack.peek() < x) {
      stack.push(x);
      return;
    }
    int temp = stack.pop();
    sortedInsert(stack, x);
    stack.push(temp);
  }

  public static void main(String[] args) {
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(-3);
    stack.push(14);
    stack.push(18);
    stack.push(-5);
    stack.push(30);

    print(ImmutableList.copyOf(stack).reverse()); // bottom to top
    sort(stack);
    print(ImmutableList.copyOf(stack).reverse()); // bottom to top
  }
}