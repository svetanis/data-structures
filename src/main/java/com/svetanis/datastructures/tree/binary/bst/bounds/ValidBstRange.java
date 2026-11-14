package com.svetanis.datastructures.tree.binary.bst.bounds;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 98. Validate Binary Search Tree
//
// Input: root of a binary tree. On LeetCode: 1 <= number of nodes <= 10^4, -2^31 <= node.val <=
// 2^31 - 1. Returns true when the tree is a BST: every value in a node's left subtree is strictly
// smaller than the node, every value in its right subtree strictly larger, for every node.
//
// The one idea: comparing a node with its parent is not enough -- a value deep in the right
// subtree must also be above the root. So each node carries the open range (min, max) its value
// must lie in, and that range is what is passed down. Going left, the node's own value becomes the
// new max; going right, it becomes the new min. A null bound means "no limit on this side", so no
// real value (Integer.MIN_VALUE and MAX_VALUE included) is used as a stand-in for infinity.
// The commented-out line in isValidBst is that Integer.MIN_VALUE/MAX_VALUE version, which wrongly
// rejects a tree holding either of them.
//
// Time: O(n) -- each node is compared once.
// Space: O(h) -- the recursion is as deep as the tree is tall, h <= n.
//
// Siblings: ValidBstInOrder, ValidBstInOrderIterative and ValidBstInOrderRecursive (package inorder)
// walk the tree in order and check the values come out strictly increasing, instead of passing a
// range down. This file evaluates both subtrees before combining the answers; it does not stop
// at the first false.

public final class ValidBstRange {

	public static boolean isValidBst(TreeNode root) {
		// return isValidBst(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return isValidBst(root, null, null);
	}

	private static boolean isValidBst(TreeNode root, Integer min, Integer max) {
		if (root == null) {
			return true; // BASE: an empty tree is a BST
		}
		// CHECK: false if this node violates the range its ancestors set
		if (min != null && root.val <= min || max != null && root.val >= max) {
			return false;
		}
		// WALK: check the subtrees, tightening the range on one side each
		boolean left = isValidBst(root.left, min, root.val);
		boolean right = isValidBst(root.right, root.val, max);
		return left && right;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(4);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(1);
		root.left.right = newNode(3);
		System.out.println(isValidBst(root)); // true

		TreeNode root2 = newNode(1);
		root2.left = newNode(2);
		root2.right = newNode(3);
		root2.left.left = newNode(4);
		root2.left.right = newNode(5);
		root2.right.right = newNode(6);
		System.out.println(isValidBst(root2)); // false

		// 5 / (1, 4 / (3, 6)): every node beats its parent, but 3 is below the root
		TreeNode root3 = newNode(5);
		root3.left = newNode(1);
		root3.right = newNode(4);
		root3.right.left = newNode(3);
		root3.right.right = newNode(6);
		System.out.println(isValidBst(root3)); // false
		System.out.println(isValidBst(newNode(Integer.MIN_VALUE))); // true
	}
}
