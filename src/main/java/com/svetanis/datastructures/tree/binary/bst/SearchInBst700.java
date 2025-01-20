package com.svetanis.datastructures.tree.binary.bst;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 700. Search in a BST

public final class SearchInBst700 {
  // Time Complexity: O(n)
  // Space Complexity: O(log n)

  public static Node search(Node root, int target) {
    while (root != null) {
      if (root.data == target) {
        return root;
      } else if (root.data > target) {
        root = root.left;
      } else { // root.data < key
        root = root.right;
      }
    }
    return null;
  }

  public static Node dfs(Node root, int target) {
    if (root == null || root.data == target) {
      return root;
    }
    if (root.data < target) {
      return dfs(root.right, target);
    } else {
      return dfs(root.left, target);
    }
  }

  public static void main(String[] args) {
    Node root = new Node(4);
    root.left = new Node(2);
    root.right = new Node(7);
    root.left.left = new Node(1);
    root.left.right = new Node(3);
    Nodes.preOrder(dfs(root, 2)); // 2 1 3
    System.out.println();
    Nodes.preOrder(search(root, 5));
    System.out.println();
  }
}
