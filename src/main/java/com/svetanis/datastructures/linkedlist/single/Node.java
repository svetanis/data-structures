package com.svetanis.datastructures.linkedlist.single;

public final class Node {

  public int data;
  public Node next;

  public Node() {
    this.data = -1;
    this.next = null;
  }

  public Node(int data) {
    this.data = data;
    this.next = null;
  }

  public Node(int data, Node next) {
    this.data = data;
    this.next = next;
  }

  public Node(Node node) {
    this.data = node.data;
    this.next = node.next;
  }

  public void appendToTail(int data) {
    Node end = new Node(data);
    Node current = this;
    while (current.next != null) {
      current = current.next;
    }
    current.next = end;
  }

  public String toString() {
    return Integer.toString(this.data);
  }
}