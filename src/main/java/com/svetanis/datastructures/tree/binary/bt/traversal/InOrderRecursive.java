package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 94. Binary Tree Inorder Traversal
//
// Input: root -- the root of a binary tree, possibly null. Prints the values in inorder: the whole
// left subtree, then the node, then the whole right subtree. On LeetCode: 0 <= nodes <= 100,
// -100 <= Node.val <= 100 (LeetCode wants the values returned as a list; this file prints them).
//
// The one idea: a call for a node does three things in a fixed order -- finish everything to the
// left of it, report it, finish everything to the right. Because the order is the same at every
// node, the values of a binary search tree come out sorted.
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the call stack holds one root-to-node chain; h is lg n on a balanced tree and n on
// a skewed one.
//
// Siblings: InOrderStack returns the same order as a list, with an explicit stack in place of the
// recursion.

public final class InOrderRecursive {

  public static void inorder(TreeNode root) {
    if (isNull(root)) { // BASE: nothing here
      return;
    }
    inorder(root.left); // LEFT: everything before this node
    System.out.print(root + " "); // REPORT: the node between its two sides
    inorder(root.right); // RIGHT: everything after it
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);
    inorder(root);
    System.out.println(); // 4 2 5 1 3 6
  }
}
