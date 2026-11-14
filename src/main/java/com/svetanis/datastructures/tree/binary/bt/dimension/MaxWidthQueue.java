package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static java.lang.Math.max;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Maximum Width of a Binary Tree, counting only real nodes (662 counts the empty positions
// between the end nodes too; this file does not).
//
// Input: the root of a binary tree. Returns the largest number of nodes on any one level
// (0 for an empty tree).
//
// The one idea: a queue holds exactly one level at a time, so its size at the start of a round is
// the width of that level. Record it, then pop that many nodes and push their children, which
// makes the queue hold the next level. Repeat until the queue is empty. No recursion, so a chain
// of any length is safe.
//
// Time: O(n) -- each node is pushed and popped once.
// Space: O(w) -- the queue holds one level, up to about n / 2 nodes.
//
// Siblings: MaxWidthPreOrder counts every level in one recursive pass with a map;
// MaxWidthLevelOrder re-walks the tree once per level; MaxWidth662 counts empty positions too.

public final class MaxWidthQueue {

  public static int maxWidth(TreeNode root) {
    if (isNull(root)) {
      return 0; // BASE: no levels, no width
    }
    int max = 0;
    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.add(root);

    while (!queue.isEmpty()) {
      int width = queue.size(); // LEVEL: exactly the nodes of the current level
      max = max(max, width); // RECORD: the widest level so far
      for (int i = 0; i < width; i++) {
        TreeNode node = queue.poll();
        if (isNotNull(node.left)) {
          queue.offer(node.left); // PUSH: the next level
        }
        if (isNotNull(node.right)) {
          queue.offer(node.right);
        }
      }
    }
    return max;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(8);
    root.right.right.left = newNode(6);
    root.right.right.right = newNode(7);
    System.out.println(maxWidth(root)); // 3
    System.out.println(maxWidth(null)); // 0
  }
}
