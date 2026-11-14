package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 617. Merge Two Binary Trees

// Given two binary trees and imagine that
// when you put one of them to cover the other,
// some nodes of the two trees are overlapped
// while the others are not.

// You need to merge them into a new binary tree.
// The merge rule is that if two nodes overlap,
// then sum node values up as the new value of the merged node.
// Otherwise, the NOT null node will be used as the node of new tree.
//
// Input: root1 and root2, two binary trees. On LeetCode: each has 0 to 2000 nodes,
// -10^4 <= value <= 10^4.
// Returns: the merged tree. Where only one tree has a node, the result reuses that node and
// everything below it (it is not copied), so the result shares nodes with the inputs.
//
// The one idea: two nodes overlap when they sit at the same position, that is, reached by the same
// sequence of left and right steps from the two roots. So walk both trees together, one step at a
// time. If one side has no node here, the other side's whole subtree is already the answer. If both
// have a node, make a new node holding the sum and merge the left children and the right children
// the same way.
//
// Time: O(min(n1, n2)) -- the walk stops as soon as either side runs out, one step per overlapping
// position.
// Space: O(min(h1, h2)) -- the recursion along the overlapping part.

public final class MergeBinaryTrees {

  public static TreeNode merge(TreeNode root1, TreeNode root2) {
    if (root1 == null) { // BASE: nothing to add, the other side is the answer
      return root2;
    }
    if (root2 == null) {
      return root1;
    }
    TreeNode root = new TreeNode(root1.val + root2.val); // SUM: both nodes overlap here
    root.left = merge(root1.left, root2.left); // LEFT: merge the left children
    root.right = merge(root1.right, root2.right); // RIGHT: merge the right children
    return root;
  }

  public static void main(String[] args) {

    TreeNode root1 = newNode(1);
    root1.left = newNode(2);
    root1.right = newNode(3);
    root1.left.left = newNode(4);
    root1.left.right = newNode(5);
    inOrder(root1); // 4 2 5 1 3
    System.out.println();

    TreeNode root2 = newNode(1);
    root2.left = newNode(3);
    root2.right = newNode(2);
    root2.right.left = newNode(5);
    root2.right.right = newNode(4);
    inOrder(root2); // 3 1 5 2 4
    System.out.println();

    inOrder(merge(root1, root2)); // 4 5 5 2 5 5 4
    System.out.println();
  }
}
