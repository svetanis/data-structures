package com.svetanis.datastructures.tree.nary;

import java.util.List;

// 559. Maximum Depth of N-ary Tree

public final class MaxDepthNaryTree {
	// Time Complexity: O(n)

	public static int maxDepth(Node root) {
		// no node and no children are different things. Folding them together
		// makes a leaf report 0, and then every ancestor is one short: the
		// three-level tree below came out 2, and a single node came out 0.
		if (root == null) {
			return 0;
		}
		int max = 0;
		if (root.children != null) {
			for (Node child : root.children) {
				max = Math.max(max, maxDepth(child));
			}
		}
		return 1 + max;
	}

	public static void main(String[] args) {
		// 1
		// / | \
		// 3 2 4
		// / \
		// 5 6
		Node five = new Node(5);
		Node six = new Node(6);
		Node three = new Node(3, List.of(five, six));
		Node root = new Node(1, List.of(three, new Node(2), new Node(4)));
		System.out.println(maxDepth(root)); // 3

		System.out.println(maxDepth(new Node(1))); // 1 -- children is null
		System.out.println(maxDepth(new Node(1, List.of()))); // 1 -- children is empty
		System.out.println(maxDepth(null)); // 0
	}

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
