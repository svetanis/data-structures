package com.svetanis.datastructures.linkedlist.single.flatten;

public final class Node {

  public int data;
  public Node next;
  public Node down;

  public Node() {
    this(0);
  }

  public Node(int data) {
    this.data = data;
    this.next = null;
    this.down = null;
  }

  @Override
  public String toString() {
    return Integer.toString(data);
  }
}