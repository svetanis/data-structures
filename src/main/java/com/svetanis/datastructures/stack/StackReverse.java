package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

// Reverses a stack in place, using recursion instead of a second stack.
//
// Input: the values pushed so far. After reverse(), the value pushed first is on top.
//
// reverse() pops the top value, reverses the rest, then puts the popped value at the bottom.
// insertAtBottom works the same way: it pops every value, pushes the new one onto the empty
// stack, and pushes the popped values back as the calls return. The values waiting to go back
// are held in the method calls themselves, one per call.

public final class StackReverse {
  // Time Complexity: O(n^2), each of the n insertAtBottom calls pops and re-pushes up to n values
  // Space Complexity: O(n), at most n reverse calls plus n insertAtBottom calls are open at once

  private Deque<Integer> stack;

  public StackReverse() {
    this.stack = new ArrayDeque<>();
  }

  public void push(int item) {
    stack.push(item);
  }

  public int pop() {
    return stack.pop();
  }

  public void reverse() {
    if (!stack.isEmpty()) {
      // hold all items in function call
      // until we reach end of stack
      int temp = stack.pop();
      reverse();
      // insert all the items
      // (held in function call stack)
      // one by one from the bottom to top.
      // every item is inserted at the bottom
      insertAtBottom(temp);
    }
  }

  private void insertAtBottom(int item) {
    if (stack.isEmpty()) {
      stack.push(item);
    } else {
      // hold all items in function call stack
      // until we reach end of the stack
      // When the stack becomes empty,
      // the isEmpty() becomes true,
      // the above if part is executed and
      // the item is inserted at the bottom
      int temp = stack.pop();
      insertAtBottom(item);
      // once the item is inserted at the bottom,
      // push all the items held in
      // function call stack
      stack.push(temp);
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
