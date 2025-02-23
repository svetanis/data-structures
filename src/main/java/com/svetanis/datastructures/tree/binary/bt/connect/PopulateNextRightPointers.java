package com.svetanis.datastructures.tree.binary.bt.connect;

import java.util.ArrayDeque;
import java.util.Deque;

// 116. Populating Next Right Pointers in Each Node

public final class PopulateNextRightPointers {
	// Time complexity: O(n)

	public static Node nextRight(Node root) {
		if (root == null) {
			return root;
		}
		Deque<Node> queue = new ArrayDeque<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node prev = null;
			for (int i = queue.size(); i > 0; i--) {
				Node node = queue.poll();
				if (prev != null) {
					prev.next = node;
				}
				prev = node;
				if (node.left != null) {
					queue.offer(node.left);
				}

				if (node.right != null) {
					queue.offer(node.right);
				}
			}
		}
		return root;
	}

	public static void main(String[] args) {}

	private static class Node {
		private int val;
		private Node left;
		private Node right;
		private Node next;
	}
}
