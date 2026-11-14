package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Left View of a Binary Tree (no LeetCode number; the mirror image of 199. Binary Tree Right
// Side View)
//
// Input: the root of a binary tree. Returns the nodes visible when the tree is seen from the
// left side: the leftmost node of every row, from the top row down.
//
// The one idea: walk depth first, left subtree before right subtree, and number the rows from 1
// at the root. The first node the walk reaches on any row is the leftmost one on it, because
// everything to its left would have been visited earlier. So keep the deepest row number seen
// so far (max); a node on a row deeper than max is the first of its row, and it is written.
//
// Known limit: the walk recurses once per level of the tree, so a chain of 10^4 nodes (one
// child per node, ten thousand levels) overflows the default stack; LeftViewLevelOrder
// has no such limit.
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the recursion depth is the tree height h, plus the output.
//
// Siblings: LeftViewLevelOrder walks row by row with a queue.

public final class LeftViewPreOrder {

  public static List<TreeNode> leftView(TreeNode root) {
    AtomicInteger max = new AtomicInteger(0); // MAX: deepest row written so far
    List<TreeNode> list = new ArrayList<>();
    leftView(root, 1, max, list);
    return list;
  }

  private static void leftView(TreeNode root, int level, AtomicInteger max, List<TreeNode> list) {
    if (isNull(root)) {
      return; // BASE: past a leaf
    }

    if (max.get() < level) {
      list.add(root); // RECORD: first node reached on this row
      max.set(level);
    }

    leftView(root.left, level + 1, max, list); // WALK: left first
    leftView(root.right, level + 1, max, list);
  }

  public static void main(String[] args) {
    TreeNode root = newNode(12);
    root.left = newNode(10);
    root.left.left = newNode(25);
    root.left.right = newNode(40);
    System.out.println(leftView(root)); // [12, 10, 25]
  }
}
