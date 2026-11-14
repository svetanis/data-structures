package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 144. Binary Tree Preorder Traversal
//
// Input: root -- the root of a binary tree, possibly null. Prints the values in preorder: the node,
// then the whole left subtree, then the whole right subtree. On LeetCode: 0 <= nodes <= 100,
// -100 <= Node.val <= 100 (LeetCode wants the values returned as a list; this file prints them).
//
// The one idea: a node is reported before anything below it, so every node is reported before its
// descendants. That is the order for jobs where a node must be handled before its children (copying
// a tree, writing it out so it can be rebuilt).
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the call stack holds one root-to-node chain; h is lg n on a balanced tree and n on
// a skewed one.
//
// Siblings: PreOrderStack returns the same order as a list, with an explicit stack in place of the
// recursion.

public final class PreOrderRecursive {

  public static void preOrder(TreeNode root) {
    if (isNull(root)) { // BASE: nothing here
      return;
    }
    System.out.print(root + " "); // REPORT: the node before either side
    preOrder(root.left); // LEFT: the whole left side first
    preOrder(root.right); // RIGHT: then the whole right side
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);
    preOrder(root); // prints 1 2 4 5 3 6, no newline
  }
}
