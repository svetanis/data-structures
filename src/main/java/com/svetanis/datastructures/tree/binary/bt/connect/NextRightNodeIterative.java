package com.svetanis.datastructures.tree.binary.bt.connect;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.Queue;

import com.google.common.base.Optional;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a binary tree and a node in it, write an efficient algorithm
// to find its next node in same level as given node

public final class NextRightNodeIterative {

  public static Optional<Node> nextRight(Node root, int k) {
    // Time complexity: O(n)

    if (isNull(root)) {
      return absent();
    }

    Queue<Node> queue = newLinkedList();
    queue.offer(root);

    while (!queue.isEmpty()) {
      int size = queue.size();

      while (size > 0) {
        Node node = queue.poll();
        size--;

        if (node.data == k) {
          if (size == 0) {
            return absent();
          }
          return of(queue.peek());
        }

        if (isNotNull(node.left)) {
          queue.offer(node.left);
        }

        if (isNotNull(node.right)) {
          queue.offer(node.right);
        }
      }
    }
    return absent();
  }

  public static void main(String[] args) {
    Node root = newNode(10);
    root.left = newNode(2);
    root.right = newNode(6);
    root.left.left = newNode(8);
    root.left.right = newNode(4);
    root.right.right = newNode(5);

    System.out.println(nextRight(root, 10)); // absent
    System.out.println(nextRight(root, 2));  // 6
    System.out.println(nextRight(root, 6));  // absent
    System.out.println(nextRight(root, 5));  // absent
    System.out.println(nextRight(root, 8));  // 4
    System.out.println(nextRight(root, 4));  // 5
  }
}
