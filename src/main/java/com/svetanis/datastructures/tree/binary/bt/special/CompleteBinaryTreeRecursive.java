package com.svetanis.datastructures.tree.binary.bt.special;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.size;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 958. Check Completeness of a Binary Tree
//
// Input: the root of a binary tree. A tree is complete when every level except possibly the last is
// full, and the last level has its nodes as far left as possible. Returns true when the tree is
// complete (the empty tree is). On LeetCode: 1 <= number of nodes <= 100, 1 <= Node.val <= 1000.
//
// The one idea: number the nodes as if the tree were packed into an array -- root 0, children of
// position i at 2i + 1 and 2i + 2. A complete tree with n nodes fills exactly the positions 0 to
// n - 1 with no gap, so the tree is complete exactly when no node gets a position of n or more: a
// gap pushes some node past the end.
//
// Time: O(n) -- one pass to count the nodes, one pass to number them.
// Space: O(h) -- the recursion stack; h is the height of the tree, n for a chain. Positions stay
// below n once checked, so they cannot overflow an int.
//
// Siblings: BinaryTreeCompletenessDfs -- the same numbering with its own node count.
// BinaryTreeCompletenessBfs and CompleteBinaryTreeLot decide by reading the levels with a queue.

public final class CompleteBinaryTreeRecursive {

  public static boolean isComplete(TreeNode root) {
    int index = 0; // POSITION: the root is position 0
    int size = size(root); // COUNT: n, the first position that must stay empty
    return isComplete(root, index, size);
  }

  private static boolean isComplete(TreeNode root, int index, int size) {
    // the empty tree is complete
    if (root == null) {
      return true; // BASE: an empty child is fine
    }
    // if index assigned to current node is more than
    // number of nodes in tree, then tree is not complete
    if (index >= size) {
      return false; // MISMATCH: a node past position n - 1 means a gap before it
    }
    // recur for left and right subtrees
    boolean left = isComplete(root.left, 2 * index + 1, size); // WALK: left child's position
    boolean right = isComplete(root.right, 2 * index + 2, size); // WALK: right child's position
    return left && right;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);
    System.out.println(isComplete(root)); // false

    TreeNode root2 = newNode(1);
    root2.left = newNode(2);
    root2.left.left = newNode(4);
    System.out.println(isComplete(root2)); // false

    TreeNode root3 = newNode(1);
    root3.left = newNode(2);
    root3.right = newNode(3);
    root3.left.left = newNode(4);
    root3.left.right = newNode(5);
    System.out.println(isComplete(root3)); // true
  }
}
