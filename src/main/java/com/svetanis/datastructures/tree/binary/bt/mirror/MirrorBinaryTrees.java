package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Are Two Binary Trees Mirror Images of Each Other (not a LeetCode problem)
//
// Input: root1, root2 -- the roots of two binary trees, possibly null. Returns true if the second
// tree is the first one with the left and right children swapped at every node. Two empty trees
// are mirrors; an empty tree and a non-empty one are not.
//
// The one idea: two trees are mirrors when their roots hold the same value, the left subtree of the
// first mirrors the right subtree of the second, and the right subtree of the first mirrors the left
// subtree of the second. The same test applies to each pair, so it is one recursion on a pair of
// nodes that moves down the two trees in opposite directions.
//
// Time: O(n) -- each pair of nodes is compared at most once, and the walk stops at the first
// mismatch.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: SymmetricBinaryTree runs the same pair test on the two children of one root (LC 101).
// FlipEquivalent lets each node choose to swap or not, where this file requires every node to swap.

public final class MirrorBinaryTrees {

  public static boolean mirror(TreeNode root1, TreeNode root2) {
    if (isNull(root1) && isNull(root2)) { // BASE: both empty
      return true;
    }
    if (isNull(root1) || isNull(root2)) { // BASE: only one is empty
      return false;
    }
    if (root1.val != root2.val) { // BASE: different values
      return false;
    }
    boolean left = mirror(root1.left, root2.right); // CROSS: first's left against second's right
    boolean right = mirror(root1.right, root2.left); // CROSS: first's right against second's left
    return left && right;
  }

  public static void main(String[] args) {

    TreeNode root1 = newNode(1);
    root1.left = newNode(2);
    root1.right = newNode(3);
    root1.left.left = newNode(4);
    root1.left.right = newNode(5);
    inOrder(root1);
    System.out.println(); // 4 2 5 1 3

    TreeNode root2 = newNode(1);
    root2.left = newNode(3);
    root2.right = newNode(2);
    root2.right.left = newNode(5);
    root2.right.right = newNode(4);
    inOrder(root2);
    System.out.println(); // 3 1 5 2 4

    System.out.println(mirror(root1, root2)); // true
  }
}
