package com.svetanis.datastructures.queue;

import static com.svetanis.java.base.Exceptions.illegalState;

import java.util.Stack;

public final class QueueOneStack<T> {

  private Stack<T> stack;

  // create an empty queue
  public QueueOneStack() {
    this.stack = new Stack<>();
  }

  public void enqueue(T value) {
    stack.push(value);
  }

  public T dequeue() {
    T res;
    if (stack.empty()) {
      throw illegalState("queue underflow");
    }
    if (stack.size() == 1) {
      return stack.pop();
    } else {
      T x = stack.pop(); // pop an item from stack
      res = dequeue(); // store the last dequeued item
      stack.push(x);
    }
    return res;
  }

  public T peek() {
    T res;
    if (stack.empty()) {
      throw illegalState("queue underflow");
    }
    if (stack.size() == 1) {
      return stack.peek();
    } else {
      T x = stack.peek(); // pop an item from stack
      res = dequeue(); // store the last dequeued item
      stack.push(x);
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
