package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import java.util.LinkedList;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1161. Maximum Level Sum of a Binary Tree
//
// Input: root of a binary tree, the root being level 1. Returns the smallest level whose values
// add up to the largest level sum. On LeetCode: 1 <= number of nodes <= 10^4,
// -10^5 <= node value <= 10^5 (so a level sum is at most 10^9 in size and fits in an int).
//
// The one idea: one queue entry is one node waiting to be read. At the start of each round the
// queue holds exactly one whole level, so its size is that level's node count: take exactly that
// many nodes off, add their values and queue their children, which are the next level. After each
// round compare the level's sum with the best so far. The comparison is strictly greater, so on a
// tie the earlier (smaller) level keeps the answer, as the problem asks. An empty tree (outside
// the constraints) returns 0.
//
// Time: O(n) -- each node is queued and taken off once.
// Space: O(w) -- the queue holds one level at a time, w = the widest level (up to n / 2).

public final class MaxLevelSum1161 {

  public static int maxLevelSum(TreeNode root) {
    if (root == null) {
      return 0;
    }
    int level = 0;
    int maxLevel = 0;
    int max = Integer.MIN_VALUE;
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
      int sum = 0;
      level++;
      int size = queue.size(); // SIZE: the queue holds exactly one level
      for (int i = 0; i < size; i++) {
        TreeNode node = queue.poll(); // TAKE: one node of this level
        sum += node.val; // SUM: this level
        if (node.left != null) {
          queue.offer(node.left); // QUEUE: the next level
        }
        if (node.right != null) {
          queue.offer(node.right);
        }
      }
      if (max < sum) { // BEST: strictly greater, so a tie keeps the smaller level
        max = sum;
        maxLevel = level;
      }

    }
    return maxLevel;
  }

  public static void main(String[] args) {
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(7);
    root.right = new TreeNode(0);
    root.left.left = new TreeNode(7);
    root.left.right = new TreeNode(-8);
    System.out.println(maxLevelSum(root)); // 2
  }
}
