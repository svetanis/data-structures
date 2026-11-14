package com.svetanis.datastructures.tree.binary.bt.compare;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Identical Structure of Two Binary Trees (no LeetCode number)
//
// Input: the roots of two binary trees; either may be empty. Returns true when the two trees have
// the same shape -- a node at a position in one tree if and only if there is a node at that
// position in the other. The values are not looked at.
//
// The one idea: two trees have the same shape when both are empty, or when neither is empty and
// the left subtrees have the same shape and the right subtrees have the same shape. It is the
// check of LeetCode 100 with the value comparison taken out.
//
// Time: O(n) -- one call per pair of positions; n is the size of the smaller tree, since the
// recursion stops where either tree has no node.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: IdenticalBinaryTreesRecursive and IdenticalBinaryTreesIterative also compare the
// value at every position.

public final class IdenticalStructureBinaryTrees {

  public static boolean identical(TreeNode root1, TreeNode root2) {
    if (isNull(root1) && isNull(root2)) {
      return true; // BASE: both empty
    }

    if (isNull(root1) || isNull(root2)) {
      return false; // BASE: one empty, one not
    }

    boolean left = identical(root1.left, root2.left); // WALK: the left pair
    boolean right = identical(root1.right, root2.right); // WALK: the right pair
    return left && right; // RETURN: both sides must match
  }

  public static void main(String[] args) {

    TreeNode node1 = newNode(10);
    node1.left = newNode(7);
    node1.right = newNode(15);
    node1.right.left = newNode(11);
    node1.left.right = newNode(9);

    TreeNode node2 = newNode(10);
    node2.left = newNode(7);
    node2.right = newNode(15);
    node2.right.left = newNode(11);
    node2.left.right = newNode(9);

    System.out.println(identical(node1, node2)); // true

    TreeNode node3 = newNode(10);
    node3.left = newNode(7);
    node3.right = newNode(15);
    node3.left.left = newNode(5);
    node3.right.left = newNode(11);

    TreeNode node4 = newNode(10);
    node4.left = newNode(7);
    node4.right = newNode(15);
    node4.left.left = newNode(9);
    node4.left.right = newNode(10);
    node4.right.left = newNode(12);

    System.out.println(identical(node3, node4)); // false
  }
}
