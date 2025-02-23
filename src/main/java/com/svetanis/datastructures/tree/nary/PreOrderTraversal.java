package com.svetanis.datastructures.tree.nary;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

// 590. N-ary Tree Postorder Traversal

public final class PreOrderTraversal {
	// Time Complexity: O(n)

	public static List<Integer> postOrder(Node root) {
		if (root == null) {
			return new ArrayList<>();
		}
		Deque<Node> dq = new ArrayDeque<>();
		LinkedList<Integer> list = new LinkedList<>();
		dq.offerLast(root);
		while (!dq.isEmpty()) {
			Node curr = dq.pollLast();
			list.addFirst(curr.val);
			for (Node child : curr.children) {
				dq.offerLast(child);
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
