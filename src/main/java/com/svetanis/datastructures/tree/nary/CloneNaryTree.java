package com.svetanis.datastructures.tree.nary;

import java.util.ArrayList;
import java.util.List;

// 1490. Clone N-ary Tree

public final class CloneNaryTree {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public Node clone(Node root) {
		if (root == null) {
			return null;
		}
		List<Node> cloned = new ArrayList<>();
		for (Node child : root.children) {
			cloned.add(clone(child));
		}
		return new Node(root.val, cloned);
	}

	public Node cloneTree(Node root) {
		return dfs(root);
	}

	private Node dfs(Node node) {
		if (node == null) {
			return null;
		}
		Node clone = new Node(node.val);
		for (Node child : node.children) {
			clone.children.add(dfs(child));
		}
		return clone;
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
