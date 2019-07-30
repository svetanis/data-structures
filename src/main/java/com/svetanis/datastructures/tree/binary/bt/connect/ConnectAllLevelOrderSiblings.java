package com.svetanis.datastructures.tree.binary.bt.connect;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.bt.connect.Node.newNode;

import java.util.Queue;

// Given a binary tree, connect each node with its level order successor. 
// The last node of each level should point to the first node of the next level.

public final class ConnectAllLevelOrderSiblings {

  public static Node connect(Node root) {
    Queue<Node> queue = newLinkedList();
    queue.offer(root);
    Node prev = null;
    while (!queue.isEmpty()) {
      Node node = queue.poll();
      if (prev != null) {
        prev.next = node;
      }
      prev = node;

      if (node.left != null) {
        queue.offer(node.left);
      }
      if (node.right != null) {
        queue.offer(node.right);
      }
    }
    return root;
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);
    connect(root);
  }
}
