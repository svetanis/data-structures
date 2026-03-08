package com.svetanis.datastructures.tree.binary.bt.dfs;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 549. Binary Tree Longest Consecutive Sequence II

public final class LongestConsecutiveSequenceII {
	// Time Complexity: O(n)
	// Space Complexity: O(h)

	private int maxLen;

	public int longestConsecutive(Node root) {
		this.maxLen = 0;
		dfs(root);
		return maxLen;
	}

	private int[] dfs(Node root) {
		if (root == null) {
			return new int[] { 0, 0 };
		}
		int inc = 1, dec = 1;
		int[] left = dfs(root.left);
		int[] right = dfs(root.right);
		if (root.left != null) {
			if (root.left.data + 1 == root.data) {
				inc = left[0] + 1;
			}
			if (root.left.data - 1 == root.data) {
				dec = left[1] + 1;
			}
		}
		if (root.right != null) {
			if (root.right.data + 1 == root.data) {
				inc = Math.max(inc, right[0] + 1);
			}
			if (root.right.data - 1 == root.data) {
				dec = Math.max(dec, right[1] + 1);
			}
		}
		maxLen = Math.max(maxLen, inc + dec - 1);
		return new int[] { inc, dec };
	}

	public static void main(String[] args) {
		LongestConsecutiveSequenceII lcs = new LongestConsecutiveSequenceII();
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		System.out.println(lcs.longestConsecutive(root)); // 2

		Node root1 = new Node(2);
		root1.left = new Node(1);
		root1.right = new Node(3);
		System.out.println(lcs.longestConsecutive(root1)); // 3
	}
}
