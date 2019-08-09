package com.svetanis.datastructures.tree.binary.bt;

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
    Node root = new Node(1);
    root.left = new Node(2);
    root.right = new Node(3);
    root.left.left = new Node(4);
    root.left.right = new Node(5);
    root.right.right = new Node(6);
    System.out.println(isComplete(root));

    Node root2 = new Node(1);
    root2.left = new Node(2);
    root2.left.left = new Node(4);
    System.out.println(isComplete(root2));

    Node root3 = new Node(1);
    root3.left = new Node(2);
    root3.right = new Node(3);
    root3.left.left = new Node(4);
    root3.left.right = new Node(5);
    System.out.println(isComplete(root3));
  }
}
