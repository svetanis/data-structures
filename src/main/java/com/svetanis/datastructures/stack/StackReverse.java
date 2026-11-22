package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

// Reverses a stack in place, using recursion instead of a second stack. Not a LeetCode problem.
//
// Input: the values pushed so far. After reverse(), the value pushed first is on top.
//
// reverse() pops the top value, reverses the rest, then puts the popped value at the bottom.
// insertAtBottom works the same way: it pops every value, pushes the new one onto the empty
// stack, and pushes the popped values back as the calls return. The values waiting to go back
// are held in the method calls themselves, one per call.
//
// Siblings, the same hold-it-in-the-call shape:
//   StackSortAscendingRecursive, StackSortDescendingRecursive  insert into sorted position
//     instead of at the bottom, so the stack ends sorted rather than reversed

public final class StackReverse {
  // Time Complexity: O(n^2), each of the n insertAtBottom calls pops and re-pushes up to n values
  // Space Complexity: O(n), at most n reverse calls plus n insertAtBottom calls are open at once

  private Deque<Integer> stack;

  public StackReverse() {
    this.stack = new ArrayDeque<>();
  }

  public void push(int item) {
    stack.push(item); // PUSH: onto the top
  }

  public int pop() {
    return stack.pop(); // POP: from the top
  }

  public void reverse() {
    if (!stack.isEmpty()) { // STOP: an empty stack is its own reverse
      // hold all items in function call
      // until we reach end of stack
      int temp = stack.pop(); // HOLD: the top waits in this call
      reverse(); // REVERSE: the rest
      // insert all the items
      // (held in function call stack)
      // one by one from the bottom to top.
      // every item is inserted at the bottom
      insertAtBottom(temp); // BOTTOM: the old top goes under the reversed rest
    }
  }

  private void insertAtBottom(int item) {
    if (stack.isEmpty()) {
      stack.push(item); // BOTTOM: the stack is empty, so this is the bottom
    } else {
      // hold all items in function call stack
      // until we reach end of the stack
      // When the stack becomes empty,
      // the isEmpty() becomes true,
      // the above if part is executed and
      // the item is inserted at the bottom
      int temp = stack.pop(); // HOLD: this value waits in the call
      insertAtBottom(item); // DIG: one level further down
      // once the item is inserted at the bottom,
      // push all the items held in
      // function call stack
      stack.push(temp); // RESTORE: back on top of the new bottom
    }
  }

  public static void main(String[] args) {
    StackReverse sr = new StackReverse();
    for (int i = 1; i <= 5; i++) {
      sr.push(i); // 5 is on top
    }
    sr.reverse();
    for (int i = 0; i < 5; i++) {
      System.out.print(sr.pop() + " "); // 1 2 3 4 5: the first pushed now comes off first
    }
    System.out.println();
  }
}
