package com.svetanis.datastructures.stack;

import java.util.Stack;

public final class StackReverse {

  private Stack<Integer> stack;

  public StackReverse() {
    this.stack = new Stack<>();
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
}
