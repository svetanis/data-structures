package com.svetanis.datastructures.stack.design;

import java.util.ArrayDeque;
import java.util.Deque;

// 155. Min Stack
//
// Design a stack that supports push, pop, top, and
// retrieving the minimum element in constant time.
//
// THE ONE IDEA: min() cannot be computed from a stack's top -- there is no way to
// look past it -- so it has to be STORED. Here every entry carries the minimum of
// the stack from the bottom up to and including itself, which makes the top entry
// hold the minimum of everything by definition.
//
// WHY POP NEEDS NO WORK AT ALL, which is the part that feels like cheating.
// Popping normally forces the question "what is the new minimum?" It does not here,
// because the entry underneath ALREADY recorded the answer, back when it was pushed
// and the popped element did not yet exist. Each entry answers "what would the
// minimum be if I were on top?", so popping just exposes an answer computed long
// ago.
//
// THE TRADE, and say it out loud in an interview because it is what is graded: one
// extra int per entry -- O(n) extra space -- buys the deletion of an entire class
// of bookkeeping. This is the version to prefer: obviously correct, nothing to
// prove. MinStackMemoryOptimized next door gets to O(1) extra by encoding the old
// minimum into the value, and pays for it with arithmetic that has to be argued
// about and a long to stop it overflowing.
//
// Siblings, same problem:
//   MinStackAux              the minima on a second stack, pushed only on a new minimum:
//                            needs >= on push and == on pop
//   MinStackSubmit           a second stack pushed on EVERY push, so the two stay level;
//                            self-contained, the version pasted into LeetCode
//   MinStackMemoryOptimized  one long per entry, a new minimum encoded as 2*x - min

public final class MinStack {
  // Time: O(1) for push, pop, peek and min -- each reads or writes only the top entry
  // Space: O(n) -- a second int per entry

  // ENTRY: one value and the minimum at or below it
  private record Pair(int value, int min) {
  }

  private Deque<Pair> stack;

  public MinStack() {
    this.stack = new ArrayDeque<>();
  }

  public int size() {
    return stack.size();
  }

  public boolean empty() {
    return stack.isEmpty();
  }

  public void push(int x) {
    int min = empty() ? x : Math.min(x, stack.peek().min()); // MIN: x against the minimum beneath
    stack.push(new Pair(x, min)); // PUSH: (value, minimum at or below this entry)
  }

  public int pop() {
    if (empty()) {
      throw new IllegalStateException("stack underflow");
    }
    return stack.pop().value(); // POP: the entry beneath already holds its own minimum
  }

  public int peek() {
    if (empty()) {
      throw new IllegalStateException("stack underflow");
    }
    return stack.peek().value(); // TOP: the value half
  }

  public int min() {
    if (empty()) {
      throw new IllegalStateException("stack underflow");
    } else {
      return stack.peek().min(); // MIN: the top entry's minimum covers the whole stack
    }
  }

  public static void main(String[] args) {
    MinStack stack = new MinStack();
    stack.push(6);
    System.out.println(stack.min()); // 6

    stack.push(7);
    System.out.println(stack.min()); // 6

    stack.push(8);
    System.out.println(stack.min()); // 6

    stack.push(5);
    System.out.println(stack.min()); // 5

    stack.push(3);
    System.out.println(stack.min()); // 3

    stack.pop();
    System.out.println(stack.min()); // 5

    stack.push(10);
    System.out.println(stack.min()); // 5

    stack.pop();
    System.out.println(stack.min()); // 5

    stack.pop();
    System.out.println(stack.min()); // 6
  }

}
