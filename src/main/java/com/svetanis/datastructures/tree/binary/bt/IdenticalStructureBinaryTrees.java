package com.svetanis.datastructures.tree.binary.bt;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class IdenticalStructureBinaryTrees {

  public static boolean identical(Node root1, Node root2) {
    if (isNull(root1) && isNull(root2)) {
      return true;
    }

    if (isNull(root1) || isNull(root2)) {
      return false;
    }

    boolean left = identical(root1.left, root2.left);
    boolean right = identical(root1.right, root2.right);
    return left && right;
  }

  public static void main(String[] args) {

    Node node1 = newNode(10);
    node1.left = newNode(7);
    node1.right = newNode(15);
    node1.right.left = newNode(11);
    node1.left.right = newNode(9);

    Node node2 = newNode(10);
    node2.left = newNode(7);
    node2.right = newNode(15);
    node2.right.left = newNode(11);
    node2.left.right = newNode(9);

    System.out.println(identical(node1, node2));

    Node node3 = newNode(10);
    node3.left = newNode(7);
    node3.right = newNode(15);
    node3.left.left = newNode(5);
    node3.right.left = newNode(11);

    Node node4 = newNode(10);
    node4.left = newNode(7);
    node4.right = newNode(15);
    node4.left.left = newNode(9);
    node4.left.right = newNode(10);
    node4.right.left = newNode(12);

    System.out.println(identical(node3, node4));
  }
}
