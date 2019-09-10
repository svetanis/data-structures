package com.svetanis.datastructures.tree.binary.bt.convert;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given normal BT, convert it to Left-Child Right-Subling
// (LC-RS) Binary Tree

public final class ConvertBtToLeftChildRightSublingTree {

  public static void convert(Node root) {
    if (isNull(root)) {
      return;
    }
    convert(root.left);
    convert(root.right);

    if (isNull(root.left)) {
      root.left = root.right;
      root.right = null;
    } else {
      root.left.right = root.right;
      root.right = null;
    }
  }

  public static void main(String[] args) {

    Node root = newNode(0);
    root.left = newNode(1);
    root.right = newNode(2);
    root.left.left = newNode(3);
    root.right.left = newNode(4);
    root.left.left.left = newNode(5);
    root.right.left.left = newNode(6);
    root.right.left.right = newNode(7);

    inOrder(root);
    System.out.println();
    convert(root);
    inOrder(root);
    System.out.println();
  }
}