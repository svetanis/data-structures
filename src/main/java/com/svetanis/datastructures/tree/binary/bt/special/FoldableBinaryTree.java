package com.svetanis.datastructures.tree.binary.bt.special;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Foldable Binary Tree
//
// Input: the root of a binary tree; it may be empty. The tree is foldable when the left subtree's
// STRUCTURE is the mirror image of the right subtree's: fold the tree down the middle and the nodes
// land on each other. Values are never compared -- that is the whole difference from 101. Symmetric
// Tree in mirror/SymmetricBinaryTree.java. Returns true when the tree is foldable (the empty tree
// is). This is not a LeetCode problem, so it has no stated constraints.
//
// The one idea: two subtrees are mirror images of each other when both are empty, or when both exist
// and the first one's left side mirrors the second one's right side, and the first one's right
// side mirrors the second one's left side. Each call asks that for one pair of nodes and asks it
// again for the two crossed pairs of children. The whole tree is foldable when its left and right
// subtrees are mirrors; the root itself has nothing to match.
//
// Time: O(n) -- each node is in exactly one pair, and each pair is visited once.
// Space: O(h) -- the recursion stack; h is the height of the tree, n for a chain.
//
// Siblings: mirror/SymmetricBinaryTree.java -- the same pairing with the values compared too.

public final class FoldableBinaryTree {

  public static boolean foldable(TreeNode root) {
    if (isNull(root)) {
      return true; // BASE: the empty tree is foldable
    }
    return foldable(root.left, root.right); // RETURN: do the two halves mirror each other?
  }

  private static boolean foldable(TreeNode root1, TreeNode root2) {
    if (isNull(root1) && isNull(root2)) {
      return true; // BASE: both sides ended together
    }
    if (isNull(root1) || isNull(root2)) {
      return false; // MISMATCH: one side ended before the other
    }
    // otherwise, check if left and right subtrees
    // are mirrors of their counterparts
    boolean left = foldable(root1.left, root2.right); // WALK: the outer pair
    boolean right = foldable(root1.right, root2.left); // WALK: the inner pair
    return left && right;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.right.left = newNode(4);
    root.left.right = newNode(5);

    System.out.println(foldable(root)); // true

    TreeNode root1 = newNode(10);
    root1.left = newNode(7);
    root1.right = newNode(15);
    root1.left.left = newNode(5);
    root1.right.left = newNode(11);

    System.out.println(foldable(root1)); // false
  }
}
