package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import java.util.LinkedList;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1161. Maximum Level Sum of a Binary Tree

public final class MaxLevelSum1161 {
  // Time Complexity: O(n)
  // Space Complexity: O(n)

  public static int maxLevelSum(Node root) {
    if (root == null) {
      return 0;
    }
    int level = 0;
    int maxLevel = 0;
    int max = Integer.MIN_VALUE;
    Queue<Node> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
      int sum = 0;
      level++;
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        Node node = queue.poll();
        sum += node.data;
        if (node.left != null) {
          queue.offer(node.left);
        }
        if (node.right != null) {
          queue.offer(node.right);
        }
      }
      if (max < sum) {
        max = sum;
        maxLevel = level;
      }

    }
    return maxLevel;
  }

  public static void main(String[] args) {
    Node root = new Node(1);
    root.left = new Node(7);
    root.right = new Node(0);
    root.left.left = new Node(7);
    root.left.right = new Node(-8);
    System.out.println(maxLevelSum(root)); // 2
  }
}
