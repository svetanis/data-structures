package com.svetanis.datastructures.queue.impl.list;

// A first-in-first-out queue of ints stored in a singly linked list (not a LeetCode problem).
//
// Input: enqueue(val) adds at the back; dequeue() removes and returns the front item and peek()
// returns it; both throw IllegalStateException when the queue is empty.
//
// The one idea: one node is one queued item. head is the oldest node and tail the newest. Items
// leave at head and join at tail, so every operation touches one end only and nothing is
// searched.
//
// Time: enqueue, dequeue, peek, size O(1) -- one end only
// Space: O(n) -- one node per item
//
// Siblings: impl/array/Queue keeps the items in a fixed ring instead.

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
      tail = new Node(val); // BASE: the first node is both ends
      head = tail;
    } else {
      tail.next = new Node(val); // LINK: behind the newest
      tail = tail.next;
    }
    count++;
  }

  public int dequeue() {
    if (isEmpty()) {
      throw new IllegalStateException("queue underflow");
    }
    int val = head.value;
    head = head.next; // ADVANCE: the old head is dropped
    count--;
    if (isEmpty()) {
      tail = null; // the last node left: tail must not keep pointing at it
    }
    return val; // RETURN: the oldest
  }

  public int peek() {
    if (isEmpty()) {
      throw new IllegalStateException("queue underflow");
    }
    return head.value; // RETURN: the oldest
  }

  public static void main(String[] args) {
    Queue queue = new Queue();
    queue.enqueue(1);
    queue.enqueue(2);
    System.out.println(queue.peek()); // 1
    System.out.println(queue.dequeue()); // 1
    System.out.println(queue.dequeue()); // 2
    System.out.println(queue.size()); // 0
    queue.enqueue(3);
    System.out.println(queue.peek()); // 3
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