package com.svetanis.datastructures.tree.binary.bt.compare;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 100. Same Tree
//
// Input: the roots of two binary trees; either may be empty. On LeetCode: 0 <= nodes <= 100 in each
// tree, -10^4 <= Node.val <= 10^4. Returns true when the two trees have the same shape and the
// same value at every position.
//
// The one idea: two trees are the same when both are empty, or when neither is empty, the two
// roots hold the same value, and the left subtrees are the same and the right subtrees are the
// same. Each call answers that question for one pair of nodes at the same position and asks it
// again for the two pairs of children.
//
// Time: O(n) -- one call per pair of positions; n is the size of the smaller tree, since the
// recursion stops where either tree has no node.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: IdenticalBinaryTreesIterative does the same check with a queue of node pairs.
// IdenticalStructureBinaryTrees compares the shape only, not the values.

public final class IdenticalBinaryTreesRecursive {

  public static boolean identical(TreeNode root1, TreeNode root2) {
    // 1. both empty
    if (isNull(root1) && isNull(root2)) {
      return true; // BASE: both empty
    }
    // 2. one empty, one not
    if (isNull(root1) || isNull(root2)) {
      return false; // BASE: one empty, one not
    }
    // 3. both non-empty, compare them:
    boolean equal = root1.val == root2.val; // COMPARE: the two roots
    boolean left = identical(root1.left, root2.left); // WALK: the left pair
    boolean right = identical(root1.right, root2.right); // WALK: the right pair
    return equal && left && right; // RETURN: all three must hold
  }

  public static void main(String[] args) {
    TreeNode tree1 = newNode(26);
    tree1.right = newNode(3);
    tree1.right.right = newNode(3);
    tree1.left = newNode(10);
    tree1.left.left = newNode(4);
    tree1.left.left.right = newNode(30);
    tree1.left.right = newNode(6);

    TreeNode tree2 = newNode(10);
    tree2.left = newNode(4);
    tree2.right = newNode(6);
    tree2.left.right = newNode(30);

    System.out.println(identical(tree1, tree2)); // false

    TreeNode root1 = newNode(1);
    root1.left = newNode(2);
    root1.right = newNode(3);
    root1.left.left = newNode(4);
    root1.left.right = newNode(5);

    TreeNode root2 = newNode(1);
    root2.left = newNode(2);
    root2.right = newNode(3);
    root2.left.left = newNode(4);
    root2.left.right = newNode(5);

    System.out.println(identical(root1, root2)); // true
  }
}
