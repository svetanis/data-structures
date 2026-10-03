package com.svetanis.datastructures.stack;

import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.Deque;

import com.google.common.collect.ImmutableList;

// Sorts a stack with the help of one extra stack, smallest value at the bottom, largest on top.
//
// Input: a stack, which is emptied. Returns a new stack holding the same values, sorted.
//
// 'aux' is kept sorted, largest on top. Each value popped from the input goes onto 'aux' only
// after every larger value in 'aux' has been moved back onto the input stack; those larger
// values are popped again later and land above it.

public final class StackSortAscendingIterative {
  // Time Complexity: O(n^2), placing one value can send every larger value in aux back
  // Space Complexity: O(n) for aux

  public static Deque<Integer> sort(Deque<Integer> stack) {
    Deque<Integer> aux = new ArrayDeque<>();
    while (!stack.isEmpty()) {
      int temp = stack.pop();
      while (!aux.isEmpty() && aux.peek() > temp) { // larger values must end up above temp
        stack.push(aux.pop());
      }
      aux.push(temp);
    }
    return aux;
  }

  public static void main(String[] args) {
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(9);
    stack.push(1);
    stack.push(5);
    stack.push(12);
    print(ImmutableList.copyOf(stack).reverse()); // bottom to top
    Deque<Integer> sorted = sort(stack);
    print(ImmutableList.copyOf(sorted).reverse()); // bottom to top
  }
}