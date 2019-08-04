package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, find the root-to-leaf path with the maximum sum.

public final class RootToLeafMaxSum {

  public static int maxSum(Node root) {
    if (root == null) {
      return 0;
    }
    int left = max(0, maxSum(root.left));
    int right = max(0, maxSum(root.right));
    return root.data + max(left, right);
  }

  public static void main(String[] args) {
    // Max sum Root-to-leaf path
    // sum = 16: 8->-2->10
    // sum = 8: -4->-2->10
    // sum = 17: 7->10
    // max sum = 17

    Node root = newNode(10);
    root.left = newNode(-2);
    root.right = newNode(7);
    root.left.left = newNode(8);
    root.left.right = newNode(-4);
    System.out.println(maxSum(root));

    Node root2 = newNode(1);
    root2.left = newNode(2);
    root2.right = newNode(3);
    root2.left.left = newNode(8);
    root2.left.right = newNode(4);
    root2.right.left = newNode(5);
    root2.right.right = newNode(6);
    root2.left.right.left = newNode(10);
    root2.right.left.left = newNode(7);
    root2.right.left.right = newNode(9);
    root2.right.right.right = newNode(5);
    System.out.println(maxSum(root2));

  }
}
