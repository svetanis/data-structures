package com.svetanis.datastructures.tree.binary.bt.convert;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isFull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given BT, convert it to full tree by
// removing half nodes (nodes having one child)

public final class ConvertBtToFullTree {

  public static Node truncate(Node root) {
    if (isNull(root)) {
      return null;
    }
    root.left = truncate(root.left);
    root.right = truncate(root.right);

    if (isFull(root) || isLeaf(root)) {
      return root;
    }
    return isNotNull(root.left) ? root.left : root.right;
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
    root = truncate(root);
    inOrder(root);
    System.out.println();
  }
}