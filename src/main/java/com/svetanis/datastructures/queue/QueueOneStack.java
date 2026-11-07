package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Deque;

// A first-in-first-out queue built on one stack, with recursion in place of a second stack (not a
// LeetCode problem; LC 232 asks for the two-stack version).
//
// Input: enqueue(value) adds at the back; dequeue() removes and returns the front item and peek()
// returns it; both throw IllegalStateException when the queue is empty.
//
// The one idea: one stack entry is one queued item, newest on top, so the front of the queue is
// at the bottom. dequeue pops the top item, calls itself on the rest until one item is left,
// takes that one, and pushes each popped item back as the calls return. The popped items wait in
// the call stack, which does the job of the second stack.
//
// Known limit: the recursion is as deep as the queue is long, so dequeue and peek on a queue of
// about 10,000 items overflow the default stack (StackOverflowError); it only happens on very
// large input. QueueTwoStacks does the same job with loops and has no such limit.
//
// Time: enqueue O(1); dequeue, peek O(n) -- every item above the front is popped and pushed back
// Space: O(n) -- the stack, plus O(n) call depth in dequeue and peek
//
// Siblings: QueueTwoStacks and QueueFromTwoStacks use two stacks and loops, and are O(1) amortized.

public final class QueueOneStack<T> {
  private Deque<T> stack;

  // create an empty queue
  public QueueOneStack() {
    this.stack = new ArrayDeque<>();
  }

  public void enqueue(T value) {
    stack.push(value); // TAKE: onto the top, the newest
  }

  public T dequeue() {
    T res;
    if (stack.isEmpty()) {
      throw new IllegalStateException("queue underflow");
    }
    if (stack.size() == 1) {
      return stack.pop(); // BASE: the bottom item: the front of the queue
    } else {
      T x = stack.pop(); // TAKE: pop an item from the stack
      res = dequeue(); // RECURSE: the front of what is left; the popped items wait in the call stack
      stack.push(x); // RESTORE: put it back as the calls return, so the order is kept
    }
    return res;
  }

  public T peek() {
    T res;
    if (stack.isEmpty()) {
      throw new IllegalStateException("queue underflow");
    }
    if (stack.size() == 1) {
      return stack.peek(); // BASE: the bottom item: the front of the queue
    } else {
      T x = stack.pop(); // TAKE: the top off
      res = peek(); // RECURSE: the front is at the bottom: look, don't remove
      stack.push(x); // RESTORE: put the top back
    }
    return res;
  }

  public static void main(String[] args) {
    QueueOneStack<Integer> queue = new QueueOneStack<>();
    queue.enqueue(5);
    queue.enqueue(6);
    queue.enqueue(7);
    queue.enqueue(8);
    System.out.println("Removed element: " + queue.dequeue()); // Removed element: 5
    System.out.println("Front element: " + queue.peek()); // Front element: 6
    System.out.println();
  }
}
