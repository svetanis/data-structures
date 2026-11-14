package com.svetanis.datastructures.tree.binary.model.mutable.primitive;

// TreeNode -- a binary tree node holding an int. The fields are public on purpose, like
// LeetCode's own TreeNode, so a solution can read and relink them directly; a missing child is
// null. toString prints just val. This is the node class of the tree.binary.bst and bt packages;
// it compares by reference (no equals).

public final class TreeNode {

  public int val;
  public TreeNode left;
  public TreeNode right;

  public static TreeNode newNode() {
    return new TreeNode();
  }

  public static TreeNode newNode(int val) {
    return new TreeNode(val);
  }

  public TreeNode() {
    this(0);
  }

  public TreeNode(int val) {
    this(val, null, null);
  }

  public TreeNode(int val, TreeNode left, TreeNode right) {
    this.val = val;
    this.left = left;
    this.right = right;
  }

  public boolean isLeaf() {
    return left == null && right == null;
  }

  @Override
  public String toString() {
    return Integer.toString(val);
  }

}