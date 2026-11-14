package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Root-to-Leaf Path Equal to a Given Sequence -- the same problem as 1430. Check If a String Is a
// Valid Sequence from Root to Leaves Path in a Binary Tree (there the values are digits)
//
// Input: the root of a binary tree and an array a. Returns true if some path from the root down
// to a LEAF has exactly the values of a, in order. A sequence that stops at a node that still
// has children is not a match. For an empty tree the answer is true only for an empty array.
// (LeetCode 1430: 1 <= arr.length <= 5000, 0 <= val <= 9, 1 <= n <= 5000.)
//
// The one idea: line the array up against the path, one position per level. At depth i the
// node's value must equal a[i]; if it does not, or the array has run out, this branch is dead.
// When the node is a leaf and a[i] is the LAST entry, the whole array has matched a complete
// path. Otherwise try both children with the next position; either may succeed.
//
// Time: O(n) -- each node is visited once and compared once.
// Space: O(h) -- the recursion stack (h = height, n on a chain).
//
// Siblings: none.
public final class RootToLeafPathGivenSequence {

  public static boolean path(TreeNode root, int[] a) {
    if (isNull(root)) { // BASE: only an empty sequence matches an empty tree
      return a.length == 0;
    }
    return path(root, a, 0);
  }

  private static boolean path(TreeNode root, int[] a, int i) {
    if (isNull(root)) { // BASE: the sequence wants more nodes than this branch has
      return false;
    }
    if (i >= a.length || root.val != a[i]) { // MISMATCH: this branch cannot spell the sequence
      return false;
    }
    if (isLeaf(root) && i == a.length - 1) { // FOUND: a leaf, and the array is used up
      return true;
    }
    return path(root.left, a, i + 1) || path(root.right, a, i + 1); // WALK: either child
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(0);
    root.right = newNode(1);
    root.left.left = newNode(1);
    root.right.left = newNode(6);
    root.right.right = newNode(5);
    int[] a1 = {1, 0, 7};
    System.out.println(path(root, a1)); // false
    int[] a2 = {1, 1, 6};
    System.out.println(path(root, a2)); // true
    int[] a3 = {1, 0}; // stops at a node that has a child
    System.out.println(path(root, a3)); // false

    // LeetCode example 1
    TreeNode root2 = newNode(0);
    root2.left = newNode(1);
    root2.right = newNode(0);
    root2.left.left = newNode(0);
    root2.left.right = newNode(1);
    root2.right.left = newNode(0);
    root2.left.left.right = newNode(1);
    root2.left.right.left = newNode(0);
    root2.left.right.right = newNode(0);
    int[] b1 = {0, 1, 0, 1};
    System.out.println(path(root2, b1)); // true
  }
}
