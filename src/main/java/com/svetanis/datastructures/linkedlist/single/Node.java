package com.svetanis.datastructures.linkedlist.single;

public final class Node {

  public int data;
  public Node next;

  public Node() {
    this(-1);
  }

  public Node(int data) {
    this(data, null);
  }

  public Node(int data, Node next) {
    this.data = data;
    this.next = next;
  }

  public Node(Node node) {
    this(node.data, node.next);
  }

  public void appendToTail(int data) {
    Node end = new Node(data);
    Node current = this;
    while (current.next != null) {
      current = current.next;
    }
    current.next = end;
  }

  @Override
  public String toString() {
    return Integer.toString(this.data);
  }
}