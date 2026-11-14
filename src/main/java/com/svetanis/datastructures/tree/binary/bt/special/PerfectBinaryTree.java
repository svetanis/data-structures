package com.svetanis.datastructures.tree.binary.bt.special;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.height;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Perfect Binary Tree
//
// Input: the root of a binary tree; it may be empty. A binary tree is perfect when
// 1. all internal nodes have two children
// 2. all leaves are at the same level.
// Returns true when the tree is perfect (the empty tree is). This is not a LeetCode problem, so it
// has no stated constraints.
//
// The one idea: measure the height h of the whole tree first, counting the root's level as 1. Then
// walk down with the current level. A node with one child fails rule 1 at once. A leaf must sit at
// the last level, h, which is rule 2: since h is the height of the deepest leaf, a leaf at any
// other level is a shallower leaf. Both rules hold at every node exactly when the tree is perfect.
//
// Time: O(n) -- one pass to measure the height, one pass to check every node.
// Space: O(h) -- the recursion stack; h is the height of the tree, n for a chain.

public final class PerfectBinaryTree {

  public static boolean perfectBt(TreeNode root) {
    int height = height(root); // MEASURE: the level every leaf must be on
    return perfectBT(root, height, 0);
  }

  private static boolean perfectBT(TreeNode root, int height, int level) {
    if (isNull(root)) {
      return true; // BASE: an empty tree is perfect
    }
    if (isLeaf(root)) {
      return height == level + 1; // CHECK: a leaf must be on the last level
    }
    if (isNull(root.left) || isNull(root.right)) {
      return false; // MISMATCH: exactly one child
    }
    boolean left = perfectBT(root.left, height, level + 1); // WALK: the left side, one level down
    boolean right = perfectBT(root.right, height, level + 1); // WALK: the right side, one level down
    return left && right;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(10);
    root.left = newNode(20);
    root.right = newNode(30);
    root.left.left = newNode(40);
    root.left.right = newNode(50);
    root.right.left = newNode(60);
    root.right.right = newNode(70);
    System.out.println(perfectBt(root)); // true

    TreeNode root1 = newNode(10);
    root1.left = newNode(20);
    root1.right = newNode(30);
    root1.left.left = newNode(40);
    System.out.println(perfectBt(root1)); // false
  }
}
