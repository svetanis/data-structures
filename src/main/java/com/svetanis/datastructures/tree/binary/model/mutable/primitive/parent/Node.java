package com.svetanis.datastructures.tree.binary.model.mutable.primitive.parent;

public final class Node {

  public int data;
  public Node left;
  public Node right;
  public Node parent;

  public static Node newNode(int data) {
    return new Node(data);
  }

  public Node(int data) {
    this.data = data;
    this.left = null;
    this.right = null;
    this.parent = null;
  }

  public boolean isLeaf() {
    return left == null && right == null;
  }

  public String toString() {
    return Integer.toString(data);
  }
}