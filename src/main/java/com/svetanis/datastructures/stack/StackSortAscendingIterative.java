package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

// Sorts a stack with the help of one extra stack, smallest value at the bottom, largest on top.
// Not a LeetCode problem.
//
// Input: a stack, which is emptied. Returns a new stack holding the same values, sorted.
//
// 'aux' is kept sorted, largest on top. Each value popped from the input goes onto 'aux' only
// after every larger value in 'aux' has been moved back onto the input stack; those larger
// values are popped again later and land above it.
//
// Siblings, same task:
//   StackSortAscendingRecursive   sorts in place; the larger values wait in method calls
//   StackSortDescendingRecursive  the same recursion with the order flipped, largest at bottom
//   SortStrUsingStack             the same insertion on characters, with a holding stack that
//                                 sends the larger ones straight back

public final class StackSortAscendingIterative {
  // Time Complexity: O(n^2), placing one value can send every larger value in aux back
  // Space Complexity: O(n) for aux

  public static Deque<Integer> sort(Deque<Integer> stack) {
    Deque<Integer> aux = new ArrayDeque<>();
    while (!stack.isEmpty()) { // WALK: until every value is placed in aux
      int temp = stack.pop(); // TAKE: the next value to place
      while (!aux.isEmpty() && aux.peek() > temp) { // BACK: larger values must end up above temp
        stack.push(aux.pop());
      }
      aux.push(temp); // PLACE: everything below it in aux is not larger
    }
    return aux; // RETURN: sorted, largest on top
  }

  public static void main(String[] args) {
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(9);
    stack.push(1);
    stack.push(5);
    stack.push(12);
    System.out.println(stack.reversed()); // bottom to top
    Deque<Integer> sorted = sort(stack);
    System.out.println(sorted.reversed()); // bottom to top
  }
}