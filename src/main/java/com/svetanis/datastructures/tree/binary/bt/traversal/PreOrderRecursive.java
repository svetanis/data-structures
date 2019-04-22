package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class PreOrderRecursive {

  public static void preOrder(Node root) {
    if (isNull(root)) {
      return;
    }
    System.out.print(root + " ");
    preOrder(root.left);
    preOrder(root.right);
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);
    preOrder(root);
  }
}
