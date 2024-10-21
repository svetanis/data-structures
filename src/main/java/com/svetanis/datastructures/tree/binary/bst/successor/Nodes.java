package com.svetanis.datastructures.tree.binary.bst.successor;

public final class Nodes {

  public static boolean isNull(Node node) {
    return node == null;
  }

  public static boolean isNotNull(Node node) {
    return !isNull(node);
  }

  public static void inOrder(Node node) {
    // prints elements in sorted order
    if (node == null) {
      return;
    }
    inOrder(node.left);
    System.out.print(node + " ");
    inOrder(node.right);
  }

  public static Node insert(Node node, int data) {
    // 1. if the tree is empty, return a new, single node
    if (node == null) {
      return new Node(data);
    }
    // 2. otherwise, recur down the tree
    if (data <= node.data) {
      Node left = insert(node.left, data);
      node.left = left;
      left.parent = node;
    } else {
      Node right = insert(node.right, data);
      node.right = right;
      right.parent = node;
    }
    // return the unchanged node pointer
    return node;
  }

  public static Node min(Node root) {
    if (root == null) {
      return null;
    }
    Node current = root;
    // loop down to find the leftmost leaf
    while (current.left != null) {
      current = current.left;
    }
    return current;
  }

  public static int height(Node root, Node node) {
    if (root == null || node == null) {
      return 0;
    }
    int depth = 0;
    while (root != node) {
      depth++;
      node = node.parent;
    }
    return depth;
  }

}
