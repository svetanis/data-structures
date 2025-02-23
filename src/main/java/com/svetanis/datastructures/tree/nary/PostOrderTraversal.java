package com.svetanis.datastructures.tree.nary;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// 590. N-ary Tree Postorder Traversal

public final class PostOrderTraversal {
	// Time Complexity: O(n)

	public static List<Integer> postOrder(Node root) {
		if (root == null) {
			return new ArrayList<>();
		}
		Deque<Node> dq = new ArrayDeque<>();
		List<Integer> list = new ArrayList<>();
		dq.push(root);
		while (!dq.isEmpty()) {
			Node curr = dq.pop();
			list.add(curr.val);
			List<Node> children = curr.children;
			for (int i = children.size() - 1; i >= 0; i--) {
				dq.push(children.get(i));
			}
		}
		return list;
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
