package com.svetanis.datastructures.tree.binary.bt;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class DeleteRecursive {

  // RECURSIVE: POST-ORDER TRAVERSAL
  public static Node delete(Node root) {
    if (isNotNull(root)) {
      delete(root.left);
      delete(root.right);
      root.left = null;
      root.right = null;
      root = null;
    }
    return root;
  }

  public static void main(String[] args) {

    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);

    inOrder(root);
    System.out.println();
    root = delete(root);
    inOrder(root);
    System.out.println();

    Node tree = newNode(15);
    tree.left = newNode(10);
    tree.right = newNode(20);
    tree.left.left = newNode(8);
    tree.left.right = newNode(12);
    tree.right.left = newNode(16);
    tree.right.right = newNode(25);

    inOrder(tree);
    System.out.println();
    tree = delete(tree);
    inOrder(tree);
    System.out.println();
  }
}