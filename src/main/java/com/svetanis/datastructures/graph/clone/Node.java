package com.svetanis.datastructures.graph.clone;

import static com.google.common.base.Objects.equal;
import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.Objects.notEqual;
import static java.util.Objects.hash;

import java.util.List;

public final class Node {

  protected int data;
  protected List<Node> children;
  private int hash;

  public Node() {
    this(-1);
  }

  public Node(int data) {
    this.data = data;
    this.children = newArrayList();
    this.hash = hash(data, children);
  }

  public void addChild(Node node) {
    this.children.add(node);
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
      boolean three = equal(children, other.children);
      return one && two && three;
    }

  }

}