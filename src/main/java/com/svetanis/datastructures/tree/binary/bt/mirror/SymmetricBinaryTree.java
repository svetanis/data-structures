package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.bt.mirror.MirrorBinaryTrees.mirror;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Mirror of itself (symmetric around its center)

public final class SymmetricBinaryTree {

  public static boolean symmetric(Node root) {
    if (isNull(root)) {
      return true;
    }
    return mirror(root.left, root.right);
  }

  public static void main(String[] args) {
    Node root = newNode(3);
    root.left = newNode(2);
    root.right = newNode(5);
    root.left.left = newNode(1);
    root.right.left = newNode(4);
    root.right.right = newNode(6);

    System.out.println(symmetric(root));

    Node root1 = newNode(1);
    root1.left = newNode(2);
    root1.right = newNode(2);
    root1.left.left = newNode(3);
    root1.left.right = newNode(4);
    root1.right.left = newNode(4);
    root1.right.right = newNode(3);

    System.out.println(symmetric(root1));
  }
}
