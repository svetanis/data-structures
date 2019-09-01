package com.svetanis.datastructures.tree.binary.bt;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class DeleteIterative {

  // ITERATIVE: LEVEL ORDER TRAVERSAL
  public static Node delete(Node root) {
    // Base case
    if (isNull(root)) {
      return null;
    }

    Queue<Node> queue = newLinkedList();
    queue.offer(root);
    while (!queue.isEmpty()) {
      root = queue.poll();
      if (isNotNull(root.left)) {
        queue.offer(root.left);
      }
      if (isNotNull(root.right)) {
        queue.offer(root.right);
      }
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
