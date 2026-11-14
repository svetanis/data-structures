package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 101. Symmetric Tree
//
// Input: root -- the root of a binary tree, possibly null. Returns true if the tree is a mirror of
// itself around its centre: the left subtree is the right subtree with left and right swapped at
// every node. On LeetCode: 1 <= nodes <= 1000, -100 <= Node.val <= 100.
//
// The one idea: one tree cannot be compared with itself, so compare two nodes at a time. Start with
// the root's two children. Two nodes match when both are missing, or both exist with equal values
// and their children match crosswise -- the first's left against the second's right, and the first's
// right against the second's left. One missing and one present is a mismatch. The tree is
// symmetric when the root's left and right children match. An empty tree is symmetric.
//
// Time: O(n) -- each node is compared once, with its mirror partner.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: SymmetricBinaryTreeQueue runs the same pair test with a queue of pairs instead of
// recursion. MirrorBinaryTrees is the same pair test applied to two separate trees.

public final class SymmetricBinaryTree {

	public static boolean symmetric(TreeNode root) {
		if (root == null) { // BASE: an empty tree is symmetric
			return true;
		}
		return mirror(root.left, root.right); // PAIR: start with the root's two children
	}

	private static boolean mirror(TreeNode root1, TreeNode root2) {
		if (root1 == null && root2 == null) { // BASE: both missing match
			return true;
		}
		if (root1 == null || root2 == null) { // BASE: only one is missing
			return false;
		}
		if (root1.val != root2.val) { // BASE: values differ
			return false;
		}
		boolean left = mirror(root1.left, root2.right); // CROSS: outer pair
		boolean right = mirror(root1.right, root2.left); // CROSS: inner pair
		return left && right;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(3);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(1);
		root.right.left = newNode(4);
		root.right.right = newNode(6);
		System.out.println(symmetric(root)); // false

		TreeNode root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(2);
		root1.left.left = newNode(3);
		root1.left.right = newNode(4);
		root1.right.left = newNode(4);
		root1.right.right = newNode(3);
		System.out.println(symmetric(root1)); // true
	}
}
