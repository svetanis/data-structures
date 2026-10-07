package com.svetanis.datastructures.queue;

import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.ArrayDeque;
import java.util.Deque;

// A first-in-first-out queue built on one stack, with recursion in place of a second stack.
//
// enqueue adds at the back; dequeue removes and returns the front item and peek returns it;
// both throw when the queue is empty.
//
// The stack holds the items newest on top, so the front of the queue is at the bottom.
// dequeue pops the top item, calls itself on the rest until one item is left, takes that one,
// and pushes each popped item back as the calls return. The popped items wait in the call
// stack, which does the job of the second stack.

public final class QueueOneStack<T> {
  // Time Complexity: enqueue: O(1); dequeue, peek: O(n), every item above the front is
  //   popped and pushed back
  // Space Complexity: O(n) for the stack, plus O(n) call depth in dequeue and peek

  private Deque<T> stack;

  // create an empty queue
  public QueueOneStack() {
    this.stack = new ArrayDeque<>();
  }

  public void enqueue(T value) {
    stack.push(value);
  }

  public T dequeue() {
    T res;
    if (stack.isEmpty()) {
      throw illegalState("queue underflow");
    }
    if (stack.size() == 1) {
      return stack.pop(); // the bottom item: the front of the queue
    } else {
      T x = stack.pop(); // pop an item from stack
      res = dequeue(); // store the last dequeued item
      stack.push(x); // put it back as the calls return, so the order is kept
    }
    return res;
  }

  public T peek() {
    T res;
    if (stack.isEmpty()) {
      throw illegalState("queue underflow");
    }
    if (stack.size() == 1) {
      return stack.peek();
    } else {
      T x = stack.pop(); // take the top off
      res = peek(); // the front is at the bottom: look, don't remove
      stack.push(x); // put the top back
    }
    return res;
  }

  public static void main(String[] args) {
    QueueOneStack<Integer> queue = new QueueOneStack<>();
    queue.enqueue(5);
    queue.enqueue(6);
    queue.enqueue(7);
    queue.enqueue(8);
    System.out.println("Removed element: " + queue.dequeue());
    System.out.println("Front element: " + queue.peek());
    System.out.println();
  }
}
