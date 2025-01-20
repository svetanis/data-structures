package com.svetanis.datastructures.tree.binary.bt.path;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1372. Longest ZigZag Path in a Binary Tree

public final class LongestZigZagPath1372 {
  // Time Complexity: O(n)
  // Space Complexity: O(log n)

  public static int lzzp(Node root) {
    return dfs(root, 0, 0, 0);
  }

  private static int dfs(Node root, int left, int right, int max) {
    if (root == null) {
      return max;
    }
    max = Math.max(max, Math.max(left, right));
    int leftLen = dfs(root.left, right + 1, 0, max);
    int rightLen = dfs(root.right, 0, left + 1, max);
    return Math.max(max, Math.max(leftLen, rightLen));
  }

  public static void main(String[] args) {
    Node root1 = new Node(1);
    root1.right = new Node(1);
    root1.right.left = new Node(1);
    root1.right.right = new Node(1);
    root1.right.right.left = new Node(1);
    root1.right.right.right = new Node(1);
    root1.right.right.left.right = new Node(1);
    root1.right.right.left.right.right = new Node(1);
    System.out.println(lzzp(root1)); // 3

    Node root2 = new Node(1);
    root2.left = new Node(1);
    root2.right = new Node(1);
    root2.left.right = new Node(1);
    root2.left.right.left = new Node(1);
    root2.left.right.right = new Node(1);
    root2.left.right.left.right = new Node(1);
    System.out.println(lzzp(root2)); // 4

    Node root3 = new Node(1);
    System.out.println(lzzp(root3)); // 0
  }
}
