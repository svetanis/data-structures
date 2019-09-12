package com.svetanis.datastructures.tree.binary.bt.dimention;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Math.max;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, write a function to get the max width of the given tree. 
// Width of a tree is maximum of widths of all levels. 

public final class MaxWidthQueue {

  public static int maxWidth(Node root) {

    if (isNull(root)) {
      return 0;
    }
    int max = 0;
    Queue<Node> queue = new ArrayDeque<>();
    queue.add(root);

    while (!queue.isEmpty()) {
      int width = queue.size();
      max = max(max, width);
      for (int i = 0; i < width; i++) {
        Node node = queue.poll();
        if (isNotNull(node.left)) {
          queue.offer(node.left);
        }
        if (isNotNull(node.right))
          queue.offer(node.right);
      }
    }
    return max;
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(8);
    root.right.right.left = newNode(6);
    root.right.right.right = newNode(7);
    System.out.println(maxWidth(root));
  }
}
