package com.svetanis.datastructures.tree.binary.bt.connect;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.bt.connect.Node.newNode;

import java.util.Queue;

// Given a binary tree, connect each node with its level order successor. 
// The last node of each level should point to a null node.

public final class ConnectSameLevelNodes {

  public static Node connect(Node root) {
    Queue<Node> queue = newLinkedList();
    queue.offer(root);
    while (!queue.isEmpty()) {
      Node prev = null;
      int size = queue.size();

      for (int i = 0; i < size; i++) {
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
    }
    return root;
  }

  public static void main(String[] args) {
    Node root = newNode(10);
    root.left = newNode(8);
    root.right = newNode(2);
    root.left.left = newNode(3);
    root.right.right = newNode(90);

    connect(root);

    if (root.next != null) {
      System.out.println(root + ": " + root.next);
    } else {
      System.out.println(root + ": -1");
    }

    if (root.left.next != null) {
      System.out.println(root.left + ": " + root.left.next);
    } else {
      System.out.println(root.left + ": -1");
    }

    if (root.right.next != null) {
      System.out.println(root.right + ": " + root.right.next);
    } else {
      System.out.println(root.right + ": -1");
    }

    if (root.left.left.next != null) {
      System.out.println(root.left.left + ": " + root.left.left.next);
    } else {
      System.out.println(root.left.left + ": -1");
    }
    if (root.right.right.next != null) {
      System.out.println(root.right.right + ": " + root.right.right.next);
    } else {
      System.out.println(root.right.right + ": -1");
    }

  }

}
