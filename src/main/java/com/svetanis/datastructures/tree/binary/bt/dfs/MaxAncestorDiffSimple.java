package com.svetanis.datastructures.tree.binary.bt.dfs;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1026. Maximum Difference Between Node and Ancestor
//
// Input: root of a binary tree. Returns the largest |a.val - b.val| over all pairs where a is an
// ancestor of b. On LeetCode: 2 <= number of nodes <= 5000, 0 <= node.val <= 10^5.
//
// The one idea: carry the minimum and the maximum of the path from the root down. At the end of
// a path -- past a leaf -- max - min is the biggest gap between any two nodes on that path, and
// any two nodes on one path are an ancestor and a descendant. So the answer is the best of
// max - min over all root-to-leaf paths, returned up by taking the larger of the two children.
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the recursion stack, h the height; n on a tree that is one long chain.
//
// Siblings: MaxAncestorDiff compares each node with the minimum and maximum above it and keeps
// the best in a field; this one has no field, so one object can answer many trees.

public final class MaxAncestorDiffSimple {

	public int maxAncestorDiff(TreeNode root) {
		if (root == null) {
			return 0;
		}
		return dfs(root, root.val, root.val); // START: the root alone is the whole path
	}

	private int dfs(TreeNode root, int min, int max) {
		if (root == null) {
			return max - min; // BASE: the path ended; its widest gap is max - min
		}
		min = Math.min(min, root.val); // FOLD: this node joins the path
		max = Math.max(max, root.val);
		int left = dfs(root.left, min, max); // WALK: left subtree
		int right = dfs(root.right, min, max);
		return Math.max(left, right); // RETURN: the widest path below
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(8);
		root.left = new TreeNode(3);
		root.right = new TreeNode(10);
		root.left.left = new TreeNode(1);
		root.left.right = new TreeNode(6);
		root.left.right.left = new TreeNode(4);
		root.left.right.right = new TreeNode(7);
		root.right.right = new TreeNode(14);
		root.right.right.left = new TreeNode(13);
		MaxAncestorDiffSimple mad = new MaxAncestorDiffSimple();
		System.out.println(mad.maxAncestorDiff(root)); // 7

		TreeNode root1 = new TreeNode(1);
		root1.right = new TreeNode(2);
		root1.right.right = new TreeNode(0);
		root1.right.right.left = new TreeNode(3);
		MaxAncestorDiffSimple mad1 = new MaxAncestorDiffSimple();
		System.out.println(mad1.maxAncestorDiff(root1)); // 3
	}
}
