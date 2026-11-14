package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 145. Binary Tree Postorder Traversal
//
// Input: node -- the root of a binary tree, possibly null. Prints the values in postorder: the
// whole left subtree, the whole right subtree, then the node. On LeetCode: 0 <= nodes <= 100,
// -100 <= Node.val <= 100 (LeetCode wants the values returned as a list; this file prints them).
//
// The one idea: a node is reported last, after both of its sides are finished, so by the time a
// node is reported everything below it has already been reported. That is the order for jobs where
// a node needs its children's results first (heights, sizes, deleting a tree).
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the call stack holds one root-to-node chain; h is lg n on a balanced tree and n on
// a skewed one.
//
// Siblings: PostOrderTwoStacks returns a list using two explicit stacks; PostOrderDfs returns the
// nodes by repeatedly walking down from the root to the first node not yet reported.

public final class PostOrderRecursive {

  public static void postorder(TreeNode node) {
    if (isNull(node)) { // BASE: nothing here
      return;
    }
    postorder(node.left); // LEFT: the whole left side first
    postorder(node.right); // RIGHT: then the whole right side
    System.out.print(node + " "); // REPORT: the node after both sides
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);
    postorder(root);
    System.out.println(); // 4 5 2 6 3 1
  }
}
