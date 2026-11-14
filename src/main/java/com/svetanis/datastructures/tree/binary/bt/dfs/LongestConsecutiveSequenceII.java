package com.svetanis.datastructures.tree.binary.bt.dfs;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 549. Binary Tree Longest Consecutive Sequence II
//
// Input: root of a binary tree. A consecutive path is any path in the tree -- it may go up and
// then down through a common ancestor -- whose values go up by 1 at every step or down by 1 at
// every step. Returns the length, in nodes, of the longest one. On LeetCode:
// 1 <= number of nodes <= 3 * 10^4, -3 * 10^4 <= node.val <= 3 * 10^4.
//
// The one idea: for each node, dfs returns two numbers about the chains that start at the node
// and go down: inc, the longest chain where each value is one smaller than the value above it,
// and dec, the longest where each value is one larger. A node alone is 1 for both. A child
// extends inc when child.val + 1 == node.val and dec when child.val - 1 == node.val; with two
// children, take the longer. A path that bends at this node joins an inc chain going down one
// side with a dec chain going down the other side; read from the end of one to the end of the
// other, the values go steadily up or down. The two chains share the node, so the path has
// inc + dec - 1 nodes. The answer is the best of that over every node.
//
// Time: O(n) -- every node is visited once, with constant work per node.
// Space: O(h) -- the recursion stack, h the height; n on a tree that is one long chain.
//
// Known limit: a chain of about 3 * 10^4 nodes (the LeetCode maximum) overflows the default
// stack. LongestConsecutiveSequenceIIIterative is the same computation without recursion.

public final class LongestConsecutiveSequenceII {

	private int maxLen;

	public int longestConsecutive(TreeNode root) {
		this.maxLen = 0; // RESET: the field is shared between calls
		dfs(root);
		return maxLen;
	}

	private int[] dfs(TreeNode root) {
		if (root == null) {
			return new int[] { 0, 0 }; // BASE: no chain
		}
		int inc = 1, dec = 1; // START: the node alone
		int[] left = dfs(root.left); // WALK: children first
		int[] right = dfs(root.right);
		if (root.left != null) {
			if (root.left.val + 1 == root.val) {
				inc = left[0] + 1; // EXTEND: left child is one smaller
			}
			if (root.left.val - 1 == root.val) {
				dec = left[1] + 1; // EXTEND: left child is one larger
			}
		}
		if (root.right != null) {
			if (root.right.val + 1 == root.val) {
				inc = Math.max(inc, right[0] + 1); // EXTEND: right child is one smaller
			}
			if (root.right.val - 1 == root.val) {
				dec = Math.max(dec, right[1] + 1); // EXTEND: right child is one larger
			}
		}
		maxLen = Math.max(maxLen, inc + dec - 1); // RECORD: the path that bends at this node
		return new int[] { inc, dec }; // RETURN: both chains for the parent
	}

	public static void main(String[] args) {
		LongestConsecutiveSequenceII lcs = new LongestConsecutiveSequenceII();
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		System.out.println(lcs.longestConsecutive(root)); // 2

		TreeNode root1 = new TreeNode(2);
		root1.left = new TreeNode(1);
		root1.right = new TreeNode(3);
		System.out.println(lcs.longestConsecutive(root1)); // 3
	}
}
