package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 199. Binary Tree Right Side View
//
// Input: the root of a binary tree. Returns the nodes visible when the tree is seen from the
// right side: the rightmost node of every row, from the top row down. This file returns
// TreeNode objects; on LeetCode the answer is their values, with 0 <= number of nodes <= 100
// and -100 <= val <= 100.
//
// The one idea: walk depth first, right subtree before left subtree, and number the rows from 1
// at the root. The first node the walk reaches on any row is the rightmost one on it, because
// everything to its right would have been visited earlier. So keep the deepest row number seen
// so far (max); a node on a row deeper than max is the first of its row, and it is written.
//
// Known limit: the walk recurses once per level of the tree, so a chain of 10^4 nodes (ten
// thousand levels) overflows the default stack. LeetCode allows at most 100 nodes, so it never
// happens there; RightViewLevelOrder has no such limit.
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the recursion depth is the tree height h, plus the output.
//
// Siblings: RightViewDfs is the same depth-first walk keyed on list size and returns values;
// RightViewLevelOrder walks row by row with a queue.

public final class RightViewPreOrder {

  public static List<TreeNode> rightView(TreeNode root) {
    AtomicInteger max = new AtomicInteger(0); // MAX: deepest row written so far
    List<TreeNode> list = new ArrayList<>();
    rightView(root, 1, max, list);
    return list;
  }

  private static void rightView(TreeNode root, int level, AtomicInteger max, List<TreeNode> list) {
    if (isNull(root)) {
      return; // BASE: past a leaf
    }

    if (max.get() < level) {
      list.add(root); // RECORD: first node reached on this row
      max.set(level);
    }
    rightView(root.right, level + 1, max, list); // WALK: right first
    rightView(root.left, level + 1, max, list);
  }

  public static void main(String[] args) {
    TreeNode root = newNode(12);
    root.left = newNode(10);
    root.left.left = newNode(25);
    root.left.right = newNode(40);
    System.out.println(rightView(root)); // [12, 10, 40]

    TreeNode root2 = newNode(1);
    root2.left = newNode(2);
    root2.right = newNode(3);
    root2.left.right = newNode(5);
    root2.right.right = newNode(4);
    System.out.println(rightView(root2)); // [1, 3, 4]
  }
}
