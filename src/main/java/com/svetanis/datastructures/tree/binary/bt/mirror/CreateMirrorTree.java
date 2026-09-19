package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 226. Invert Binary Tree

// the copying version: the original is left untouched and a new tree is
// built with the children crossed over. same method as
// InvertBinaryTreeRecursive.java in this package.

public final class CreateMirrorTree {

  public static Node mirror(Node root) {

    if (isNull(root)) {
      return root;
    }

    Node mirror = newNode(root.data);
    mirror.left = mirror(root.right);
    mirror.right = mirror(root.left);
    return mirror;
  }

  public static void main(String[] args) {
    Node root = newNode(4);
    root.left = newNode(2);
    root.right = newNode(5);
    root.left.left = newNode(1);
    root.left.right = newNode(3);

    inOrder(root);
    System.out.println();

    Node mirror = mirror(root);

    inOrder(mirror);
    System.out.println();
  }
}
