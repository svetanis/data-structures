package com.svetanis.datastructures.tree.binary.bt.dfs;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 298. Binary Tree Longest Consecutive Sequence
//
// Input: root of a binary tree. A consecutive path goes from a node down to one of its
// descendants, and each value is exactly 1 more than the value before it. Returns the length, in
// nodes, of the longest such path; it may start at any node. On LeetCode: 1 <= number of nodes
// <= 3 * 10^4, -3 * 10^4 <= node.val <= 3 * 10^4.
//
// The one idea: dfs(node) returns the length of the longest consecutive path that starts at
// node and goes down. It is 1 for the node alone, plus the child's answer for each child whose
// value is node.val + 1 -- a child that breaks the run restarts the count at 1. Take the longer
// of the two children. Every node is the start of its own path, so the answer is the largest
// value any call returns, recorded in a field on the way.
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the recursion stack, h the height; n on a tree that is one long chain.
//
// Known limit: a chain of about 3 * 10^4 nodes (the LeetCode maximum) overflows the default
// stack. LongestConsecutiveSequenceIterative is the same computation without recursion.
//
// Siblings: LongestConsecutiveSequenceLen carries the current run length down as a parameter
// instead of returning it up. LongestConsecutiveSequenceIterative is this method without
// recursion.

public final class LongestConsecutiveSequence {

	private int maxLen;

	public int longestConsecutive(TreeNode root) {
		this.maxLen = 0; // RESET: the field is shared between calls
		dfs(root);
		return maxLen;
	}

	private int dfs(TreeNode root) {
		if (root == null) {
			return 0; // BASE: an empty spot has no path
		}
		int left = 1 + dfs(root.left); // WALK: the run extended through the left child
		int right = 1 + dfs(root.right);
		if (root.left != null && (root.left.val - root.val) != 1) {
			left = 1; // RESTART: the left child is not value + 1
		}
		if (root.right != null && (root.right.val - root.val) != 1) {
			right = 1; // RESTART: the right child is not value + 1
		}
		int len = Math.max(left, right);
		maxLen = Math.max(maxLen, len); // RECORD: the longest path starting here
		return len; // RETURN: the longest path starting here, for the parent
	}

	public static void main(String[] args) {
		LongestConsecutiveSequence lcs = new LongestConsecutiveSequence();
		TreeNode root = new TreeNode(1);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(2);
		root.right.right = new TreeNode(4);
		root.right.right.right = new TreeNode(5);
		System.out.println(lcs.longestConsecutive(root)); // 3

		TreeNode root1 = new TreeNode(2);
		root1.right = new TreeNode(3);
		root1.right.left = new TreeNode(2);
		root1.right.left.left = new TreeNode(1);
		System.out.println(lcs.longestConsecutive(root1)); // 2
	}
}
