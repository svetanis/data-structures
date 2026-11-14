package com.svetanis.datastructures.tree.binary.bt.special;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 958. Check Completeness of a Binary Tree
//
// Input: the root of a binary tree. A tree is complete when every level except possibly the last is
// full, and the last level has its nodes as far left as possible. Returns true when the tree is
// complete. On LeetCode: 1 <= number of nodes <= 100, 1 <= Node.val <= 1000.
//
// The one idea: give every node the position it would have if the tree were packed into an array
// level by level: the root is position 0, and the children of the node at position i are at
// 2i + 1 and 2i + 2. A complete tree with n nodes fills exactly the positions 0 to n - 1 with no
// gap. So count the nodes, give every node its position, and the tree is complete exactly when no
// node gets a position of n or more -- a gap pushes some node past the end.
//
// Time: O(n) -- one pass to count, one pass to number.
// Space: O(h) -- the recursion stack; h is the height of the tree, n for a chain. Positions stay
// below n once checked, so they cannot overflow an int.
//
// Siblings: CompleteBinaryTreeRecursive -- the same numbering, counting with the repo's size helper.
// BinaryTreeCompletenessBfs and CompleteBinaryTreeLot decide by reading the levels with a queue.

public final class BinaryTreeCompletenessDfs {

	public static boolean isComplete(TreeNode root) {
		if (root == null) {
			return true; // BASE: the empty tree is complete
		}
		int total = count(root); // COUNT: n, the first position that must stay empty
		return complete(root, 0, total); // WALK: number every node from position 0
	}

	private static boolean complete(TreeNode root, int index, int total) {
		if (root == null) {
			return true; // BASE: an empty child is fine
		}
		if (index >= total) {
			return false; // MISMATCH: a node past position n - 1 means a gap before it
		}
		boolean left = complete(root.left, 2 * index + 1, total); // WALK: left child's position
		boolean right = complete(root.right, 2 * index + 2, total); // WALK: right child's position
		return left && right;
	}

	private static int count(TreeNode root) {
		if (root == null) {
			return 0;
		}
		return 1 + count(root.left) + count(root.right);
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);
		System.out.println(isComplete(root)); // true

		TreeNode root1 = new TreeNode(1);
		root1.left = new TreeNode(2);
		root1.right = new TreeNode(3);
		root1.left.left = new TreeNode(4);
		root1.left.right = new TreeNode(5);
		root1.right.right = new TreeNode(7);
		System.out.println(isComplete(root1)); // false
	}
}
