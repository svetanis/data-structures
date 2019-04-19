package com.svetanis.datastructures.tree.nary;

public final class Nodes {

  public static <T extends Comparable<? super T>> Node.Builder<T> addChild(Node.Builder<T> node, Node.Builder<T> child) {
    node.getChildren().add(child);
    return node;
  }

}
