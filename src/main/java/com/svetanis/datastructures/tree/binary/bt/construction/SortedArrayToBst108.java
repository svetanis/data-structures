package com.svetanis.datastructures.tree.binary.bt.construction;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 108. Convert Sorted Array to Binary Search Tree
//
// Input: an int array a sorted in strictly increasing order. On LeetCode: 1 <= a.length <= 10^4,
// -10^4 <= a[i] <= 10^4.
// Returns: the root of a height-balanced binary search tree holding exactly the values of a (any
// such tree is accepted; this one takes the lower middle of an even slice).
//
// The one idea: in a search tree the root has everything smaller on its left and everything larger
// on its right. In a sorted slice, that means the root can be any element, with the elements before
// it forming the left subtree and the elements after it the right subtree. Taking the middle
// element makes the two sides the same size (or one apart), and the same rule applied to each side
// keeps every node's two subtrees within one level of each other.
//
// Time: O(n) -- one call per element, each building one node.
// Space: O(n) -- the n nodes built; the recursion itself is only O(log n) deep.
//
// Siblings: SortedListToBst and SortedListToBstArray solve the linked-list form (109) of the same
// problem.

public final class SortedArrayToBst108 {

  public static TreeNode arrayToBst(int[] a) {
    return arrayToBst(a, 0, a.length - 1);
  }

  private static TreeNode arrayToBst(int[] a, int left, int right) {
    if (left > right) { // BASE: empty slice, no node
      return null;
    }
    int mid = left + (right - left) / 2; // ROOT: lower middle, written to avoid overflow
    TreeNode lst = arrayToBst(a, left, mid - 1); // LEFT: the smaller values
    TreeNode rst = arrayToBst(a, mid + 1, right); // RIGHT: the larger values
    return new TreeNode(a[mid], lst, rst);
  }

  public static void main(String[] args) {
    int[] a = { -10, -3, 0, 5, 9 };
    Nodes.preOrder(arrayToBst(a)); // 0 -10 -3 5 9
    System.out.println();
  }
}
