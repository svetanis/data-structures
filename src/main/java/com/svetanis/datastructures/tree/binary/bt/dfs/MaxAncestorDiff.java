package com.svetanis.datastructures.tree.binary.bt.dfs;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1026. Maximum Difference Between Node and Ancestor
//
// Input: root of a binary tree. Returns the largest |a.val - b.val| over all pairs where a is an
// ancestor of b. On LeetCode: 2 <= number of nodes <= 5000, 0 <= node.val <= 10^5.
//
// The one idea: for one node b, the ancestor that is farthest from b in value is either the
// smallest or the largest value on the path above it. So carry two numbers down, the minimum and
// the maximum on the path so far; at each node compare its value with both and record the
// larger gap. Then fold the node into the minimum and the maximum for its children.
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the recursion stack, h the height; n on a tree that is one long chain.
//
// Assumptions: maxDiff is a field that maxAncestorDiff does not reset, so one object answers one
// tree; use a new MaxAncestorDiff for the next. The subtraction is an int, so it assumes the
// LeetCode value range; values near Integer.MIN_VALUE and Integer.MAX_VALUE would wrap.
//
// Siblings: MaxAncestorDiffSimple passes the minimum and maximum down and returns max - min at
// each leaf, with no field and nothing to reset.

public final class MaxAncestorDiff {

	private int maxDiff = 0; // BEST: the largest gap seen so far

	public int maxAncestorDiff(TreeNode root) {
		if (root == null) {
			return 0;
		}
		dfs(root, root.val, root.val); // START: the root alone is the whole path
		return maxDiff;
	}

	private void dfs(TreeNode root, int min, int max) {
		if (root == null) {
			return;
		}
		int diff1 = Math.abs(min - root.val); // GAP: to the smallest ancestor
		int diff2 = Math.abs(max - root.val); // GAP: to the largest ancestor
		int currentDiff = Math.max(diff1, diff2);
		maxDiff = Math.max(maxDiff, currentDiff); // RECORD: the best gap so far
		min = Math.min(min, root.val); // FOLD: this node joins the path for its children
		max = Math.max(max, root.val);
		dfs(root.left, min, max); // WALK: left subtree
		dfs(root.right, min, max);
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
		MaxAncestorDiff mad = new MaxAncestorDiff();
		System.out.println(mad.maxAncestorDiff(root)); // 7

		TreeNode root1 = new TreeNode(1);
		root1.right = new TreeNode(2);
		root1.right.right = new TreeNode(0);
		root1.right.right.left = new TreeNode(3);
		MaxAncestorDiff mad1 = new MaxAncestorDiff();
		System.out.println(mad1.maxAncestorDiff(root1)); // 3
	}
}
