package com.svetanis.datastructures.tree.binary.bt.convert;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 114. Flatten Binary Tree to Linked List

public final class FlattenBinaryTreeRecursive {
  // Time Complexity: O(n^2) -- after flattening the left subtree it walks
  // that whole flattened list again to find its tail, once per node. on a
  // left-skewed tree that walk is n-1 steps at the root, n-2 below it, and
  // so on: n(n-1)/2 in total
  // Space Complexity: O(h) -- the recursion stack

  public static void flatten(Node root) {
    if (root == null) {
      return;
    }
    Node left = root.left;
    Node right = root.right;
    root.left = null;

    flatten(left);
    flatten(right);

    root.right = left;
    Node node = root;
    while (node.right != null) {
      node = node.right;
    }
    node.right = right;
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(5);
    root.left.left = newNode(3);
    root.left.right = newNode(4);
    root.right.right = newNode(6);
    flatten(root); // 1 2 3 4 5 6
    Nodes.preOrder(root);
    System.out.println();
    Node root1 = newNode(0);
    flatten(root1); // 0
    Nodes.preOrder(root1);
    System.out.println();
  }
}
