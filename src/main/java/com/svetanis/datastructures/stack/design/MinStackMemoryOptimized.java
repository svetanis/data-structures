package com.svetanis.datastructures.stack.design;

import java.util.ArrayDeque;
import java.util.Deque;

// 155. Min Stack
//
// Design a stack that supports push, pop, top, and
// retrieving the minimum element in constant time.
//
// THE TRICK, and read the type before you read the arithmetic. When a new
// minimum arrives, the slot holds 2*x - min instead of x: an impossible value,
// below the minimum, that carries the OLD minimum inside it. Pop decodes it with
// 2*min - top and the previous minimum is back. One number per entry does the
// work of two.

// THE COST is that 2*x - min needs 34 bits, and LC 155 allows the full int
// range: push(0) then push(MIN_VALUE) encoded to 0 in int, and popping returned
// 0 instead of MIN_VALUE. push(1e9) then push(-1e9) returned 1294967296 -- a
// value that was never pushed. And it does not end there: the encoding is the
// ONLY record of the previous minimum, so one overflow corrupts every entry
// beneath it. The stack never recovers and never says anything went wrong.

// So the stack holds LONG, while push, pop and peek stay int. Nothing that goes
// in or comes out needs more than an int -- only the encoding does. The bound is
// flat, not compounding: x and min are always values a caller actually pushed,
// so an encoded slot never exceeds 3 * 2^31 no matter how deep the stack goes.

// AND KNOW THE TRADE. This saves a constant factor and no more, which is a poor
// price for arithmetic that has to be argued about. MinStack.java next door
// keeps a Pair<value, min> per entry: same O(n), obviously correct, nothing to
// prove. Reach for that one unless someone asks for this.
//
// Siblings, same problem:
//   MinStack        a (value, min) record per entry, nothing to decode
//   MinStackAux     the minima on a second stack, pushed only on a new minimum
//   MinStackSubmit  a second stack pushed on every push, the LeetCode submission

public final class MinStackMemoryOptimized {
  // Time Complexity: O(1), each operation reads or writes the top slot and min only
  // Space Complexity: O(n) for the stack itself; O(1) EXTRA, which is the
  // actual claim -- no per-entry minimum is stored alongside the values.

  private int min; // MIN: the true minimum of everything held
  private Deque<Long> stack; // SLOTS: x, or 2*x - min when x was a new minimum

  public MinStackMemoryOptimized() {
    this.stack = new ArrayDeque<>();
  }

  public int size() {
    return stack.size();
  }

  public boolean empty() {
    return stack.isEmpty();
  }

  public void push(int x) {
    if (stack.isEmpty()) {
      stack.push((long) x); // FIRST: nothing to encode, x is the minimum
      min = x;
    } else if (x > min) {
      stack.push((long) x); // PLAIN: not a new minimum, stored as is
    } else {
      stack.push(2L * x - min); // ENCODE: below x when x < min, and carries the old min
      min = x;
    }
  }

  public int pop() {
    if (stack.isEmpty()) {
      throw new IllegalStateException("stack underflow");
    }
    long top = stack.pop();
    int result = 0;
    if (top < min) { // DECODE: only an encoded slot sits below min
      // the slot was encoded, so it is holding the previous minimum.
      // 2 * min - top is a value that was really pushed, so it fits an int
      result = min;
      min = (int) (2L * min - top); // RESTORE: the minimum before this one
    } else {
      result = (int) top; // PLAIN: the slot is the value
    }
    return result;
  }

  public int peek() {
    if (stack.isEmpty()) {
      throw new IllegalStateException("stack underflow");
    }
    long top = stack.peek();
    return top < min ? min : (int) top; // DECODE: an encoded top means the value is min
  }

  public int min() {
    if (stack.isEmpty()) {
      throw new IllegalStateException("stack underflow");
    }
    return min;
  }

  public static void main(String[] args) {
    MinStackMemoryOptimized stack = new MinStackMemoryOptimized();
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

    // the two sequences the int version got wrong
    MinStackMemoryOptimized extremes = new MinStackMemoryOptimized();
    extremes.push(0);
    extremes.push(Integer.MIN_VALUE);
    System.out.println(extremes.min()); // -2147483648
    System.out.println(extremes.pop()); // -2147483648
    System.out.println(extremes.min()); // 0

    MinStackMemoryOptimized wide = new MinStackMemoryOptimized();
    wide.push(1000000000);
    wide.push(-1000000000);
    System.out.println(wide.pop()); // -1000000000
    System.out.println(wide.min()); // 1000000000
  }
}
