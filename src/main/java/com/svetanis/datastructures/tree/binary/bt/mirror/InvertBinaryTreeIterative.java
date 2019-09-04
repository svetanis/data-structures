package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Mirror of a Binary Tree T is another Binary Tree M(T) 
// with left and right children of all non-leaf nodes interchanged.

public final class InvertBinaryTreeIterative {

  public static void invert(Node root) {
    if (isNull(root)) {
      return;
    }

    Queue<Node> queue = new ArrayDeque<>();
    queue.add(root);
    while (!queue.isEmpty()) {
      Node node = queue.poll();
      swap(node);
      if (isNotNull(root.left)) {
        queue.add(root.left);
      }
      if (isNotNull(root.right)) {
        queue.add(root.right);
      }
    }
  }

  private static void swap(Node root) {
    // swap the nodes
    Node temp = root.left;
    root.left = root.right;
    root.right = temp;
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    inOrder(root); // 4 2 5 1 6 3 7
    System.out.println();

    invert(root);

    inOrder(root); // 7 3 6 1 5 2 4
    System.out.println();
  }
}
