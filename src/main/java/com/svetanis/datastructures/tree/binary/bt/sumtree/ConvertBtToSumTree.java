package com.svetanis.datastructures.tree.binary.bt.sumtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Convert a Binary Tree to its Sum Tree (not a LeetCode problem)
//
// Input: root of a binary tree of ints. Changes it in place, shape unchanged, so that every
// node holds the sum of the original values in its left and right subtrees; an empty subtree
// counts as 0, so a leaf becomes 0. Returns the sum of all the original values in the tree.
// No limits are stated; the sums are an int.
//
// The one idea: convert(node) does two jobs at once -- it rewrites the subtree and it returns
// the sum of that subtree's ORIGINAL values. The node needs both children's original sums to
// rewrite itself, so convert the children first. Then keep the node's old value, overwrite it
// with left + right, and hand the parent the old value plus both children's sums.
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the recursion stack.

public final class ConvertBtToSumTree {

  public static int convert(TreeNode root) {
    if (isNull(root)) {
      return 0; // BASE: an empty subtree sums to 0
    }

    int left = convert(root.left); // WALK: children first -- the sums of their original values
    int right = convert(root.right);

    int old = root.val; // KEEP: the original value, the parent still needs it

    root.val = left + right; // REWRITE: the sum of everything below

    return root.val + old; // RETURN: original sum of this whole subtree
  }

  public static void main(String[] args) {

    TreeNode root = newNode(10);
    root.left = newNode(-2);
    root.right = newNode(6);
    root.left.left = newNode(8);
    root.left.right = newNode(-4);
    root.right.left = newNode(7);
    root.right.right = newNode(5);

    inOrder(root); // 8 -2 -4 10 7 6 5
    System.out.println();

    convert(root);

    inOrder(root); // 0 4 0 20 0 12 0
    System.out.println();
  }
}
