package com.svetanis.datastructures.tree.binary.model.mutable.primitive;

import static com.google.common.base.Objects.equal;
import static com.svetanis.java.base.Objects.notEqual;
import static java.util.Objects.hash;

public final class Node {

  public int data;
  public Node left;
  public Node right;
  private int hash;

  public static Node newNode() {
    return new Node();
  }

  public static Node newNode(int data) {
    return new Node(data);
  }

  public Node() {
    this(0);
  }

  public Node(int data) {
    this(data, null, null);
  }

  public Node(int data, Node left, Node right) {
    this.data = data;
    this.left = left;
    this.right = right;
    this.hash = hash(data, left, right);
  }

  public boolean isLeaf() {
    return left == null && right == null;
  }

  @Override
  public int hashCode() {
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    } else if (notEqual(this, object)) {
      return false;
    } else {
      Node other = (Node) object;
      boolean one = hash == other.hash;
      boolean two = equal(data, other.data);
      boolean three = equal(left, other.left);
      boolean four = equal(right, other.right);
      return one && two && three && four;
    }

  }

  @Override
  public String toString() {
    return Integer.toString(data);
  }

}