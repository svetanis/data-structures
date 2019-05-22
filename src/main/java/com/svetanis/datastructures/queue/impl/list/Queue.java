package com.svetanis.datastructures.queue.impl.list;

import static com.svetanis.java.base.Exceptions.illegalState;

public final class Queue {

  private int count;
  private Node head;
  private Node tail;

  public Queue() {
    this.count = 0;
    this.head = null;
    this.tail = null;
  }

  private boolean isEmpty() {
    return head == null;
  }

  public int size() {
    return count;
  }

  public void enqueue(int val) {
    if (isEmpty()) {
      tail = new Node(val);
      head = tail;
    } else {
      tail.next = new Node(val);
      tail = tail.next;
    }
  }

  public int dequeue() {
    if (isEmpty()) {
      throw illegalState("queue underflow");
    }
    int val = head.value;
    head = head.next;
    count--;
    if (isEmpty()) {
      tail = null;
    }
    return val;
  }

  public int peek() {
    if (isEmpty()) {
      throw illegalState("queue underflow");
    }
    return head.value;
  }

  private static class Node {
    private int value;
    private Node next;

    public Node(int value) {
      this.value = value;
      this.next = null;
    }
  }

}