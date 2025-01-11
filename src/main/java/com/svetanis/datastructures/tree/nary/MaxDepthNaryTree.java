package com.svetanis.datastructures.tree.nary;

import java.util.List;

// 559. Maximum Depth of N-ary Tree

public final class MaxDepthNaryTree {
	// Time Complexity: O(n)

	public static int maxDepth(Node root) {
		if (root == null || root.children == null) {
			return 0;
		}
		int max = 0;
		for (Node child : root.children) {
			max = Math.max(max, maxDepth(child));
		}
		return 1 + max;
	}

	public static void main(String[] args) {}

	public static class Node {
		private int val;
		private List<Node> children;

		public Node() {}

		public Node(int val) {
			this.val = val;
		}

		public Node(int val, List<Node> children) {
			this.val = val;
			this.children = children;
		}
	}
}
