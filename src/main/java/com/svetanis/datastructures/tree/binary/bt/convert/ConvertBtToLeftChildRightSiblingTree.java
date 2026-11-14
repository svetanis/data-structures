package com.svetanis.datastructures.tree.binary.bt.convert;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Given normal BT, convert it to Left-Child Right-Sibling
// (LC-RS) Binary Tree
//
// (No LeetCode problem; it is a classic exercise.)
//
// Input: the root of a binary tree. In the left-child right-sibling form a node's left pointer
// leads to its first child and its right pointer to its next sibling (the next child of the same
// parent). Here the children of a node are its non-empty left and right children, in that order.
// Returns: nothing -- the tree is rewired in place, and afterwards the root has no right pointer.
//
// The one idea: a node with two children has to make them a chain: left child first, then the
// right child as the left child's sibling. So the node's right pointer is moved onto its left
// child's right pointer, and the node's own right pointer is cleared. A node with no left child
// just moves its right child to the left pointer: it becomes the first child. Doing the children
// first matters: when a child is rewired, its own right pointer is cleared, so the slot
// "left child's right pointer" is free to take the sibling.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion, as deep as the tree is tall.

public final class ConvertBtToLeftChildRightSiblingTree {

  public static void convert(TreeNode root) {
    if (isNull(root)) { // BASE: empty subtree
      return;
    }
    convert(root.left); // FIX: children first, so their right pointers are free
    convert(root.right);

    if (isNull(root.left)) { // ONLY RIGHT: it becomes the first child
      root.left = root.right;
      root.right = null;
    } else { // BOTH (or only left): the right child becomes the left child's sibling
      root.left.right = root.right;
      root.right = null;
    }
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
    convert(root);
    inOrder(root); // 5 3 1 6 7 4 2 0
    System.out.println();
  }
}