package com.svetanis.datastructures.tree.binary.bt;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class FoldableBinaryTree {

  public static boolean foldable(Node root) {
    if (isNull(root)) {
      return true;
    }
    return foldable(root.left, root.right);
  }

  private static boolean foldable(Node root1, Node root2) {
    if (isNull(root1) && isNull(root2)) {
      return true;
    }
    if (isNull(root1) || isNull(root2)) {
      return false;
    }
    // otherwise, check if left and right subtrees
    // are mirrors of their counterparts
    boolean left = foldable(root1.left, root2.right);
    boolean right = foldable(root1.right, root2.left);
    return left && right;
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.right.left = newNode(4);
    root.left.right = newNode(5);

    System.out.println(foldable(root));

    Node root1 = newNode(10);
    root1.left = newNode(7);
    root1.right = newNode(15);
    root1.left.left = newNode(5);
    root1.right.left = newNode(11);

    System.out.println(foldable(root1));
  }
}
