package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class MirrorTree {

  public static void mirror(Node root) {

    if (isNull(root)) {
      return;
    }

    // do the subtrees
    mirror(root.left);
    mirror(root.right);

    swap(root);
  }
  
  private static void swap(Node root) {
    // swap the nodes
    Node temp = root.left;
    root.left = root.right;
    root.right = temp;
  }

  public static void main(String[] args) {
    Node root = newNode(4);
    root.left = newNode(2);
    root.right = newNode(5);
    root.left.left = newNode(1);
    root.left.right = newNode(3);

    inOrder(root);
    System.out.println();

    mirror(root);

    inOrder(root);
    System.out.println();
  }
}
