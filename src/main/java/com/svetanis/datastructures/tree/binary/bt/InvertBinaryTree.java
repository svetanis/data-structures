package com.svetanis.datastructures.tree.binary.bt;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Mirror of a Binary Tree T is another Binary Tree M(T) 
// with left and right children of all non-leaf nodes interchanged.

public final class InvertBinaryTree {

  public static Node invert(Node root) {
    if (isNull(root)) {
      return root;
    }

    // do the subtrees
    invert(root.left);
    invert(root.right);

    // swap the nodes
    Node temp = root.left;
    root.left = root.right;
    root.right = temp;
    return root;
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    inOrder(root);  // 4 2 5 1 6 3 7 

    Node inverted = invert(root);
    System.out.println();

    inOrder(inverted); // 7 3 6 1 5 2 4

  }
}



