package com.svetanis.datastructures.tree.binary.bst;

import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 530. Minimum Absolute Difference in BST

public final class MinAbsDiffBstPreOrder {
	// Time Complexity: O(n)

	private static final int INF = Integer.MAX_VALUE;

	public static int minDiff(Node root) {
		AtomicInteger min = new AtomicInteger(INF);
		preOrder(root, INF, INF, min);
		return min.get();
	}

	private static void preOrder(Node root, int low, int high, AtomicInteger min) {
		if (root == null) {
			return;
		}
		if (low != INF) {
			int diff = Math.abs(root.data - low);
			min.set(Math.min(min.get(), diff));
		}
		if (high != INF) {
			int diff = Math.abs(root.data - high);
			min.set(Math.min(min.get(), diff));
		}
		preOrder(root.left, low, root.data, min);
		preOrder(root.right, root.data, high, min);
	}

	public static void main(String[] args) {
		Node root = new Node(4);
		root.left = new Node(2);
		root.right = new Node(6);
		root.left.left = new Node(1);
		root.left.right = new Node(3);
		System.out.println(minDiff(root)); // 1

		Node root2 = new Node(1);
		root2.left = new Node(0);
		root2.right = new Node(48);
		root2.right.left = new Node(12);
		root2.right.right = new Node(49);
		System.out.println(minDiff(root2)); // 1

		// [236,104,701,null,227,null,911] expected 9

		Node root3 = new Node(236);
		root3.left = new Node(104);
		root3.right = new Node(701);
		root3.left.right = new Node(227);
		root3.right.right = new Node(911);
		System.out.println(minDiff(root3)); // 9

	}
}