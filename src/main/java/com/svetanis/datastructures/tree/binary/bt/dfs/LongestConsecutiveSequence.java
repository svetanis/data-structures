package com.svetanis.datastructures.tree.binary.bt.dfs;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 298. Binary Tree Longest Consecutive Sequence

public final class LongestConsecutiveSequence {
	// Time Complexity: O(n)
	// Space Complexity: O(h)

	private int maxLen;

	public int longestConsecutive(Node root) {
		this.maxLen = 0;
		dfs(root);
		return maxLen;
	}

	private int dfs(Node root) {
		if (root == null) {
			return 0;
		}
		int left = 1 + dfs(root.left);
		int right = 1 + dfs(root.right);
		if (root.left != null && (root.left.data - root.data) != 1) {
			left = 1;
		}
		if (root.right != null && (root.right.data - root.data) != 1) {
			right = 1;
		}
		int len = Math.max(left, right);
		maxLen = Math.max(maxLen, len);
		return len;
	}

	public static void main(String[] args) {
		LongestConsecutiveSequence lcs = new LongestConsecutiveSequence();
		Node root = new Node(1);
		root.right = new Node(3);
		root.right.left = new Node(2);
		root.right.right = new Node(4);
		root.right.right.right = new Node(5);
		System.out.println(lcs.longestConsecutive(root)); // 3

		Node root1 = new Node(2);
		root1.right = new Node(3);
		root1.right.left = new Node(2);
		root1.right.left.left = new Node(1);
		System.out.println(lcs.longestConsecutive(root1)); // 2
	}
}
