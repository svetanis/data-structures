package com.svetanis.datastructures.stack.design;

import java.util.ArrayDeque;
import java.util.Deque;

// 155. Min Stack
//
// Design a stack that supports push, pop, top, and
// retrieving the minimum element in constant time.
//
// The same stored-answer idea as MinStack, with the answers on a SECOND STACK
// instead of paired with each value -- and pushed only when a new minimum arrives,
// so aux holds the minima rather than one entry per element.
//
// THAT SAVING IS WHAT COSTS THE TWO COMPARISONS BELOW, and one of them is a trap.
// MinStackSubmit pushes to aux on every push instead: it spends one int per entry
// and both comparisons disappear. Prefer that one under pressure; this file is
// worth reading for the trap.
//
// Siblings, same problem:
//   MinStack                 one entry per push carrying (value, minimum at or below it)
//   MinStackSubmit           aux pushed on EVERY push, so no comparison on push or pop
//   MinStackMemoryOptimized  one long per entry, a new minimum encoded as 2*x - min

public final class MinStackAux {
  // Time: O(1) for push, pop, peek and min -- each touches only the tops of the two stacks
  // Space: O(n) -- the stack, plus O(n) worst case for aux: a strictly
  // decreasing sequence of pushes makes every element a new minimum

  private Deque<Integer> stack;
  private Deque<Integer> aux;

  public MinStackAux() {
    this.stack = new ArrayDeque<>();
    this.aux = new ArrayDeque<>();
  }

  public int size() {
    return stack.size();
  }

  public boolean empty() {
    return stack.isEmpty();
  }

  public void push(int x) {
    stack.push(x); // PUSH: every value goes on the main stack
    // THE >= IS LOAD-BEARING, and > is the classic bug here. With >=, pushing 3
    // twice puts two 3s on aux, so popping one leaves the other and min() is still
    // 3. With >, only one 3 goes on, the first pop removes it, and min() reports a
    // minimum from further down: push(5) push(3) push(3) pop() then answers 5.
    // Every sequence of DISTINCT values is fine, which is why it survives testing.
    //
    // Whenever a structure holds "the winners so far", duplicates are a DECISION,
    // not a typo. Same decision in QueueWithMin's eviction and in the histogram's.
    if (aux.isEmpty() || aux.peek() >= x) { // TIE: >= so a repeated minimum is kept twice
      aux.push(x);
    }
  }

  public int pop() {
    if (stack.isEmpty()) {
      throw new IllegalStateException("stack underflow");
    }
    int top = stack.pop(); // POP: from the main stack
    if (top == aux.peek()) {
      aux.pop(); // FOLLOW: this element WAS a minimum, so its aux copy goes too
    }
    return top;
  }

  public int peek() {
    if (stack.isEmpty()) {
      throw new IllegalStateException("stack underflow");
    }
    return stack.peek();
  }

  public int min() {
    if (aux.isEmpty()) {
      throw new IllegalStateException("stack underflow");
    } else {
      return aux.peek(); // MIN: the latest minimum still held
    }
  }

  public static void main(String[] args) {
    MinStackAux stack = new MinStackAux();
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

    // the duplicate case the >= protects, and the one every distinct-value test
    // above misses
    MinStackAux dup = new MinStackAux();
    dup.push(5);
    dup.push(3);
    dup.push(3);
    dup.pop();
    System.out.println(dup.min()); // 3 -- with > on push this prints 5
  }

}