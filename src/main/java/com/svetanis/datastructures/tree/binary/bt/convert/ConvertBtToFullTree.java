package com.svetanis.datastructures.tree.binary.bt.convert;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isFull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Given BT, convert it to full tree by
// removing half nodes (nodes having one child)
//
// (No LeetCode problem; it is a classic exercise.)
//
// Input: the root of a binary tree. A full tree is one where every node has either two children
// or none. Returns: the root of the tree after every node with exactly one child has been removed
// and replaced by that child (the old root may change). The tree is changed in place.
//
// The one idea: handle the children first, then decide about the node itself. After both subtrees
// have been fixed, a node has one of three shapes: two children or none -- already fine, keep it;
// exactly one child -- remove it and hand its single (already fixed) child up to the parent, so the
// parent links straight to that child. Doing the children first means a chain of one-child nodes
// collapses all the way down in one pass.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion, as deep as the tree is tall.

public final class ConvertBtToFullTree {

  public static TreeNode truncate(TreeNode root) {
    if (isNull(root)) { // BASE: empty subtree
      return null;
    }
    root.left = truncate(root.left); // FIX: left subtree first
    root.right = truncate(root.right); // FIX: right subtree

    if (isFull(root) || isLeaf(root)) { // KEEP: two children or none
      return root;
    }
    return isNotNull(root.left) ? root.left : root.right; // SKIP: one child, return it in place of this node
  }

  public static void main(String[] args) {

    TreeNode root = newNode(0);
    root.left = newNode(1);
    root.right = newNode(2);
    root.left.left = newNode(3);
    root.right.left = newNode(4);
    root.left.left.left = newNode(5);
    root.right.left.left = newNode(6);
    root.right.left.right = newNode(7);

    inOrder(root); // 5 3 1 0 6 4 7 2
    System.out.println();
    root = truncate(root);
    inOrder(root); // 5 0 6 4 7
    System.out.println();
  }
}