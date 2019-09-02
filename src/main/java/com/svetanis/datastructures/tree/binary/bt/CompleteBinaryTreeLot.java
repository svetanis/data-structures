package com.svetanis.datastructures.tree.binary.bt;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isFull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class CompleteBinaryTreeLot {

  public static boolean isComplete(Node root) {
    // Time complexity: O(n)

    if (root == null) {
      return true;
    }
    Queue<Node> queue = new ArrayDeque<>();
    queue.offer(root);

    // set true when a non-full node is seen
    boolean nonFull = false;
    while (!queue.isEmpty()) {
      Node node = queue.poll();

      if (isFull(node)) {
        if (nonFull) {
          return false;
        }
        queue.offer(node.left);
        queue.offer(node.right);
      }

      if (isNotNull(node.left) && isNull(node.right)) {
        if (nonFull) {
          return false;
        }
        nonFull = true;
        queue.add(node.left);
      }

      if (isNull(node.left) && isNotNull(node.right)) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);
    System.out.println(isComplete(root));

    Node root2 = newNode(1);
    root2.left = newNode(2);
    root2.left.left = newNode(4);
    System.out.println(isComplete(root2));

    Node root3 = newNode(1);
    root3.left = newNode(2);
    root3.right = newNode(3);
    root3.left.left = newNode(4);
    root3.left.right = newNode(5);
    System.out.println(isComplete(root3));
  }
}
