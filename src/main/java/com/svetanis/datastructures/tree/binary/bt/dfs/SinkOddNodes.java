package com.svetanis.datastructures.tree.binary.bt.dfs;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Sink Odd Nodes of a Binary Tree (not a LeetCode problem)
//
// Input: root of a binary tree of ints. Moves values between nodes, in place, with the shape
// unchanged, so that no odd-valued node is the parent of an even-valued node. Returns nothing.
// No limits are stated; the values may be negative.
//
// The one idea: fix the children first (post-order), so each subtree already obeys the rule when
// its root is looked at. Then only the root can break it, and only when it is odd and has an even
// child. Swap the odd value down into that even child's place: the root now holds an even value
// and is safe, and the odd value is the new problem one level lower, handled the same way by
// sink. A value moves down until its children are all odd or missing. Swapping never changes
// which values exist, only where they sit.
//
// Time: O(n * h) -- a sink call can visit every node below its start, and the subtree sizes add
// up to at most n * h.
// Space: O(h) -- the recursion stack.

public final class SinkOddNodes {

  public static void sinkOddNodes(TreeNode root) {
    if (isNull(root) || isLeaf(root)) {
      return; // BASE: nothing below to break the rule
    }
    sinkOddNodes(root.left); // WALK: fix both subtrees first
    sinkOddNodes(root.right);
    if (isOdd(root.val)) {
      sink(root); // SINK: only an odd root can now break the rule
    }
  }

  private static void sink(TreeNode root) {
    if (isNull(root) || isLeaf(root)) {
      return; // BASE: a leaf has no child to be even
    }
    if (isNotNull(root.left) && isEven(root.left.val)) {
      int temp = root.val; // SWAP: the odd value goes down, the even child's value comes up
      root.val = root.left.val;
      root.left.val = temp;
      sink(root.left); // SINK: the odd value may still have an even child below
    }
    if (isNotNull(root.right) && isEven(root.right.val)) {
      int temp = root.val; // SWAP: same on the right
      root.val = root.right.val;
      root.right.val = temp;
      sink(root.right);
    }
  }

  private static boolean isEven(int x) {
    return (x & 1) == 0;
  }

  private static boolean isOdd(int x) {
    return (x & 1) == 1; // also true for a negative odd value
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(5);
    root.right = newNode(8);
    root.left.left = newNode(2);
    root.left.right = newNode(4);
    root.right.left = newNode(9);
    root.right.right = newNode(10);
    sinkOddNodes(root);
    inOrder(root); // 5 2 1 8 9 10 4
  }
}
