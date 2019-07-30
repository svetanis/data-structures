package com.svetanis.datastructures.tree.binary.bt.connect;

public class Node {

  public int data;
  public Node left;
  public Node right;
  public Node next;

  public static Node newNode(int data) {
    return new Node(data);
  }

  public Node(int data) {
    this.data = data;
    this.left = null;
    this.right = null;
    this.next = null;
  }

  @Override
  public String toString() {
    return Integer.toString(data);
  }
}