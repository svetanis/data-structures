package com.svetanis.datastructures.tree.binary.bt.special;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 958. Check Completeness of a Binary Tree

public final class BinaryTreeCompletenessDfs {
	// Time complexity: O(n)

	public static boolean isComplete(Node root) {
		if (root == null) {
			return true;
		}
		int total = count(root);
		return complete(root, 0, total);
	}

	private static boolean complete(Node root, int index, int total) {
		if (root == null) {
			return true;
		}
		if (index >= total) {
			return false;
		}
		boolean left = complete(root.left, 2 * index + 1, total);
		boolean right = complete(root.right, 2 * index + 2, total);
		return left && right;
	}

	private static int count(Node root) {
		if (root == null) {
			return 0;
		}
		return 1 + count(root.left) + count(root.right);
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(4);
		root.left.right = new Node(5);
		root.right.left = new Node(6);
		System.out.println(isComplete(root)); // true

		Node root1 = new Node(1);
		root1.left = new Node(2);
		root1.right = new Node(3);
		root1.left.left = new Node(4);
		root1.left.right = new Node(5);
		root1.right.right = new Node(7);
		System.out.println(isComplete(root1)); // false
	}
}
