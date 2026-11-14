package com.svetanis.datastructures.tree.binary.bt.dfs;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Sink Zero Nodes of a Binary Tree (not a LeetCode problem)
//
// Input: root of a binary tree of ints. Moves values between nodes, in place, with the shape
// unchanged, so that every zero sinks to the bottom of the subtree rooted at it: no zero-valued
// node has a non-zero value anywhere below it. Returns nothing. No limits are stated.
//
// The one idea: fix the children first (post-order), so each subtree already has its zeros at
// the bottom. Then only the root can be out of place, and only when it is zero and a child is
// not. Swap the zero down into that child (the left one if both qualify, otherwise the right) and
// repeat from there with sink; the child's non-zero value comes up to the root. The zero walks
// down one path until both children are zero or missing, and the values that exist never change,
// only where they sit.
//
// Time: O(n * h) -- a sink call walks one path down, at most h nodes, and there are at most n
// zero roots.
// Space: O(h) -- the recursion stack.

public final class SinkZeroNodes {

  public static void sinkZeroNodes(TreeNode root) {

    if (isNull(root)) {
      return; // BASE: an empty spot
    }
    sinkZeroNodes(root.left); // WALK: fix both subtrees first
    sinkZeroNodes(root.right);
    if (root.val == 0) {
      sink(root); // SINK: only a zero root can now be out of place
    }
  }

  private static void sink(TreeNode root) {
    if (isNull(root)) {
      return; // BASE: an empty spot
    }
    if (isNotNull(root.left) && root.left.val != 0) {
      int temp = root.val; // SWAP: the zero goes down, the non-zero child's value comes up
      root.val = root.left.val;
      root.left.val = temp;
      sink(root.left); // SINK: keep going down the same path
    } else if (isNotNull(root.right) && root.right.val != 0) {
      int temp = root.val; // SWAP: same on the right
      root.val = root.right.val;
      root.right.val = temp;
      sink(root.right);
    }
  }

  public static void main(String[] args) {
    TreeNode root = newNode(0);
    root.left = newNode(1);
    root.right = newNode(0);
    root.right.left = newNode(0);
    root.right.right = newNode(2);
    root.right.left.left = newNode(3);
    root.right.left.right = newNode(4);
    sinkZeroNodes(root);
    inOrder(root); // 0 1 0 4 0 3 2
  }
}
