package com.svetanis.datastructures.tree.binary.bt.sumtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Check if a Binary Tree is a Sum Tree (not a LeetCode problem)
//
// Input: root of a binary tree of ints. A sum tree is one where every non-leaf node holds the sum
// of all the values in its left subtree and its right subtree; an empty tree and a leaf count as
// sum trees. Returns true when the tree is one. No limits are stated; the sums are an int.
//
// The one idea: the check at a node needs the TOTAL of each child's subtree, and a total takes a
// walk of its own -- unless the child is already known to be a sum tree. In a sum tree a non-leaf
// node holds the sum of everything below it, so its whole subtree adds to twice its value; a leaf
// adds to its value; an empty subtree adds to 0. So check both children first, and when both are
// sum trees read each child's total off its value with getValue, no second walk needed.
//
// Time: O(n) -- every node is visited once, with constant work per node.
// Space: O(h) -- the recursion stack.

public final class SumBinaryTree {

  public static boolean sumTree(TreeNode root) {
    if (isNull(root) || isLeaf(root)) {
      return true; // BASE: an empty tree or a leaf is a sum tree
    }

    boolean isLeft = sumTree(root.left); // WALK: children first
    boolean isRight = sumTree(root.right);

    if (isLeft && isRight) {
      int left = getValue(root.left); // TOTAL: of the left subtree, read off its root

      int right = getValue(root.right); // TOTAL: of the right subtree

      return root.val == left + right; // RETURN: this node holds the sum of both
    }
    return false; // RETURN: a subtree is not a sum tree, so neither is this one
  }

  private static int getValue(TreeNode node) {
    if (isNull(node)) {
      return 0;
    }
    return isLeaf(node) ? node.val : 2 * node.val; // a leaf adds to itself; a sum-tree root to twice its value
  }

  public static void main(String[] args) {
    TreeNode root = newNode(26);
    root.left = newNode(10);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(6);
    root.right.right = newNode(3);
    System.out.println(sumTree(root)); // true
  }
}
