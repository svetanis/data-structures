package com.svetanis.datastructures.tree.binary.bst;

import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 530. Minimum Absolute Difference in BST

public final class MinAbsDiffBstInOrder {
	// Time Complexity: O(n)

	private static final int INF = Integer.MAX_VALUE;

	public static int minDiff(Node root) {
		AtomicInteger min = new AtomicInteger(INF);
		AtomicInteger prev = new AtomicInteger(INF);
		inOrder(root, prev, min);
		return min.get();
	}

	private static void inOrder(Node root, AtomicInteger prev, AtomicInteger min) {
		if (root == null) {
			return;
		}
		inOrder(root.left, prev, min);
		if (prev.get() != INF) {
			int diff = Math.abs(root.data - prev.get());
			min.set(Math.min(min.get(), diff));
		}
		prev.set(root.data);
		inOrder(root.right, prev, min);
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