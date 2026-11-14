package com.svetanis.datastructures.tree.binary.bt.dfs;

import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 298. Binary Tree Longest Consecutive Sequence
//
// Input: root of a binary tree. A consecutive path goes from a node down to one of its
// descendants, and each value is exactly 1 more than the value before it. Returns the length, in
// nodes, of the longest such path; it may start at any node. On LeetCode: 1 <= number of nodes
// <= 3 * 10^4, -3 * 10^4 <= node.val <= 3 * 10^4.
//
// The one idea: carry the length of the run that ends at the current node down as a parameter.
// A node continues the run when it is one more than its parent -- the run is one longer -- and
// otherwise it starts a new run of length 1. Each call returns the best run length found in its
// subtree: its own run length, or the better of its two children's answers.
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: LongestConsecutiveSequence returns the run that starts at each node up from the
// children instead of carrying it down, and keeps the best in a field.

public final class LongestConsecutiveSequenceLen {

	public static int maxLen(TreeNode root) {
		return maxLen(root, null, 0); // START: no parent, no run yet
	}

	private static int maxLen(TreeNode root, TreeNode prev, int len) {
		if (root == null) {
			return 0; // BASE: an empty spot has no run
		}
		boolean consecutive = prev != null && root.val == prev.val + 1;
		len = consecutive ? len + 1 : 1; // EXTEND the run, or RESTART it at 1
		int left = maxLen(root.left, root, len); // WALK: left subtree with this run length
		int right = maxLen(root.right, root, len);
		return max(len, max(left, right)); // RETURN: best run in this subtree
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(2);
		root.right.right = new TreeNode(4);
		root.right.right.right = new TreeNode(5);
		System.out.println(maxLen(root)); // 3
	}
}
