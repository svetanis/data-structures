package com.svetanis.datastructures.tree.binary.bt.cousins;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Are Two Nodes Siblings? (no LeetCode number)
//
// Input: the root of a binary tree and two nodes a and b of it. Returns true when a and b are the
// two children of one parent. A node is never its own sibling, and the root has no sibling.
//
// The one idea: nothing is compared by value. The check is whether some node has a as its left
// child and b as its right child, or the other way round, compared by reference. Every node is
// asked, so the search walks the tree until one answers yes.
//
// Time: O(n) -- one call per node in the worst case.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: CousinsQueue.cousins is the harder question -- same depth, DIFFERENT parents -- and uses this
// check for the second half.

public final class Siblings {

  public static boolean siblings(TreeNode root, TreeNode a, TreeNode b) {

    if (isNull(root)) {
      return false; // BASE: no parent here
    }

    boolean one = root.left == a && root.right == b; // PARENT: a on the left, b on the right
    boolean two = root.left == b && root.right == a; // PARENT: the other way round
    if (one || two) {
      return true; // RETURN: this node is their parent
    }

    boolean left = siblings(root.left, a, b); // WALK: look under the left child
    boolean right = siblings(root.right, a, b); // WALK: look under the right child
    return left || right;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(6);
    root.left = newNode(3);
    root.right = newNode(5);
    root.left.left = newNode(7);
    root.left.right = newNode(8);
    root.right.left = newNode(1);
    root.right.right = newNode(3);

    TreeNode a = root.left.left; // 7
    TreeNode b = root.right.right; // 3

    System.out.println(siblings(root, a, b)); // false -- parents 3 and 5
  }
}
