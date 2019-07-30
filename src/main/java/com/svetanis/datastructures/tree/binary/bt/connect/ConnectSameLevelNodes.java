package com.svetanis.datastructures.tree.binary.bt;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.bt.ConnectSameLevelNodes.Node.newNode;

import java.util.Queue;

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
          prev.nextRight = node;
        }
        if (node.left != null) {
          queue.offer(node.left);
        }
        if (node.right != null) {
          queue.offer(node.right);
        }
        prev = node;
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

    if (root.nextRight != null) {
      System.out.println(root + ": " + root.nextRight);
    } else {
      System.out.println(root + ": -1");
    }

    if (root.left.nextRight != null) {
      System.out.println(root.left + ": " + root.left.nextRight);
    } else {
      System.out.println(root.left + ": -1");
    }

    if (root.right.nextRight != null) {
      System.out.println(root.right + ": " + root.right.nextRight);
    } else {
      System.out.println(root.right + ": -1");
    }

    if (root.left.left.nextRight != null) {
      System.out.println(root.left.left + ": " + root.left.left.nextRight);
    } else {
      System.out.println(root.left.left + ": -1");
    }
    if (root.right.right.nextRight != null) {
      System.out.println(root.right.right + ": " + root.right.right.nextRight);
    } else {
      System.out.println(root.right.right + ": -1");
    }

  }

  public static class Node {

    public int data;
    public Node left;
    public Node right;
    public Node nextRight;

    public static Node newNode(int data) {
      return new Node(data);
    }

    public Node(int data) {
      this.data = data;
      this.left = null;
      this.right = null;
      this.nextRight = null;
    }

    @Override
    public String toString() {
      return Integer.toString(data);
    }
  }

}
