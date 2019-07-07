package com.svetanis.datastructures.linkedlist.dll.random;

public final class Node {

  public int data;
  public Node next;
  public Node rand;

  public Node() {
    this(0);
  }

  public Node(int data) {
    this.data = data;
    this.next = null;
    this.rand = null;
  }

  @Override
  public String toString() {
    return Integer.toString(data);
  }
}