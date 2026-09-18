package com.svetanis.datastructures.tree.binary.bst.inorder;

import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 530. Minimum Absolute Difference in BST

public final class MinAbsDiffBstPreOrder {
	// Time Complexity: O(n)
	// PRECONDITION: no two adjacent values in sorted order may differ by more
	// than Integer.MAX_VALUE. The gap is computed with int arithmetic and the
	// result is an int, so a wider gap overflows and comes back plausible --
	// {Integer.MIN_VALUE, Integer.MAX_VALUE} reports its smallest gap as 1.
	// LC 530 and 783 constrain values to [0, 100000], so it cannot arise there

	private static final int INF = Integer.MAX_VALUE;

	public static int minDiff(Node root) {
		AtomicInteger min = new AtomicInteger(INF);
		// null for "no bound on this side", not Integer.MAX_VALUE: a node
		// holding MAX_VALUE would have its own bound mistaken for absence,
		// and the pair either side of it would never be compared
		preOrder(root, null, null, min);
		return min.get();
	}

	private static void preOrder(Node root, Integer low, Integer high, AtomicInteger min) {
		if (root == null) {
			return;
		}
		if (low != null) {
			int diff = Math.abs(root.data - low);
			min.set(Math.min(min.get(), diff));
		}
		if (high != null) {
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

		// the larger of the only two nodes holds the old marker. with
		// Integer.MAX_VALUE standing in for "unbounded" this printed
		// 2147483647 -- the pair was skipped and min was never set
		Node root4 = new Node(Integer.MAX_VALUE);
		root4.left = new Node(5);
		System.out.println(minDiff(root4)); // 2147483642
	}
}