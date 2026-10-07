package com.svetanis.datastructures.queue.impl.list;

import static com.svetanis.java.base.Exceptions.illegalState;

// A first-in-first-out queue of ints stored in a singly linked list.
//
// enqueue adds at the back; dequeue removes and returns the front item and peek returns it;
// both throw when the queue is empty.
//
// head is the oldest node and tail the newest. Items leave at head and join at tail, so every
// operation touches one end only and nothing is searched.

public final class Queue {
  // Time Complexity: enqueue, dequeue, peek, size: O(1)
  // Space Complexity: O(n), one node per item

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
    count++;
  }

  public int dequeue() {
    if (isEmpty()) {
      throw illegalState("queue underflow");
    }
    int val = head.value;
    head = head.next;
    count--;
    if (isEmpty()) {
      tail = null; // the last node left: tail must not keep pointing at it
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