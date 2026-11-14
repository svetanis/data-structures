package com.svetanis.datastructures.tree.binary.bt.special;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Math.abs;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 110. Balanced Binary Tree
//
// Input: the root of a binary tree. A tree is balanced when, at every node, the heights of the left
// and right subtrees differ by at most 1 (the height of an empty tree is 0). Returns true when the
// tree is balanced. On LeetCode: 0 <= number of nodes <= 5000, -10^4 <= Node.val <= 10^4.
//
// The one idea: one call returns the height of one node's subtree, or -1 when something inside it is
// already out of balance. A node's height is 1 plus the taller of its two children's heights, and
// the node is out of balance when those two heights differ by more than 1. A -1 from either child
// is passed straight up, so the first problem found reaches the root without any more work.
// The tree is balanced exactly when the root's answer is not -1.
//
// Time: O(n) -- one call per node, constant work in each.
// Space: O(h) -- the recursion stack; h is the height of the tree, n for a chain.
//
// Siblings: BalancedBinaryTree -- the same check, setting a shared flag instead of returning -1.

public final class IsBalancedBinaryTree {

	public static boolean balanced(TreeNode root) {
		return height(root) != -1; // RETURN: -1 is how an out-of-balance subtree reports itself
	}

	private static int height(TreeNode root) {
		if (isNull(root)) {
			return 0; // BASE: an empty tree has height 0
		}
		int left = height(root.left); // WALK: height of the left subtree
		int right = height(root.right); // WALK: height of the right subtree
		if (left == -1 || right == -1) {
			return -1; // SKIP: a side is already out of balance, pass it up
		}
		if (abs(left - right) > 1) {
			return -1; // MISMATCH: this node is out of balance
		}
		return 1 + max(left, right); // RETURN: height of this subtree
	}

	public static void main(String[] args) {
		TreeNode root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.left = newNode(4);
		root1.left.right = newNode(5);
		root1.right.left = newNode(6);
		root1.left.left.left = newNode(7);
		System.out.println(balanced(root1)); // true

		TreeNode root2 = newNode(1);
		root2.left = newNode(2);
		root2.left.left = newNode(3);
		System.out.println(balanced(root2)); // false
	}
}
