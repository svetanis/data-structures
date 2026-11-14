package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.preOrder;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Build the Cartesian tree of an array (the LeetCode twin is 654. Maximum Binary Tree).
//
// A Cartesian tree is a heap-ordered binary tree: every node is greater than everything in its
// subtree. The array is its in-order traversal.
//
// Input: an int array a, read as the in-order sequence. Values are expected to be distinct; with
// equal values the leftmost maximum of a slice becomes its root.
// Returns: the root of the Cartesian tree.
//
// The one idea: the root must be greater than everything else, so it is the largest value of the
// whole array. In-order puts the left subtree before the root and the right subtree after it, so
// the values to the left of the maximum form the left subtree and the values to its right form the
// right subtree. Each side is the same problem on a shorter slice.
//
// Time: O(n^2) -- every call scans its whole slice for the maximum; on a sorted array the slices
// shrink by one, so n + (n-1) + ... = n(n-1)/2 comparisons. On random input the slices halve and
// it is O(n log n).
// Space: O(n) -- the n nodes built, plus the recursion (as deep as the tree is tall, up to n).
//
// Siblings: MaxBinaryTree is the same method with a loop inside the recursion.

public final class CartesianBtFromInOrder {

  public static TreeNode construct(int[] a) {
    return construct(a, 0, a.length - 1);
  }

  public static TreeNode construct(int[] a, int start, int end) {
    if (start > end) { // BASE: empty slice, no node
      return null;
    }

    // find index of the max element from BT
    int max = indexOfMax(a, start, end); // ROOT: largest value of the slice
    TreeNode root = newNode(a[max]);

    // if this is the only element in
    // inorder[start ... end],
    // then return it
    if (start == end) {
      return root;
    }
    root.left = construct(a, start, max - 1); // LEFT: values before the maximum
    root.right = construct(a, max + 1, end); // RIGHT: values after the maximum
    return root;
  }

  private static int indexOfMax(int[] a, int start, int end) {
    int index = start;
    for (int i = start + 1; i <= end; i++) { // SCAN: strict '>' keeps the leftmost maximum
      if (a[i] > a[index]) {
        index = i;
      }
    }
    return index;
  }

  public static void main(String[] args) {
    int[] inorder = { 5, 10, 40, 30, 28 };
    TreeNode root = construct(inorder);
    inOrder(root); // 5 10 40 30 28
    System.out.println();
    preOrder(root); // 40 10 5 30 28
    System.out.println();
  }
}
