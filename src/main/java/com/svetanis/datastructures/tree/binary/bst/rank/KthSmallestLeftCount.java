package com.svetanis.datastructures.tree.binary.bst.rank;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;

import com.google.common.base.Optional;

// 230. K-th Smallest BST -- the follow-up, not the submission

// the second way to augment a node, next to
// KthSmallestOrderStatistic. that file stores the size of
// the whole subtree rooted at a node; this one stores only
// how many nodes are in the node's LEFT subtree, which is
// the number the rank descent actually asks for.

// both answer every query identically. they differ in what
// insert has to do:
//   whole-subtree size -- every node on the search path
//                         gains one descendant. no condition.
//   left-subtree count -- only the nodes whose step went
//                         LEFT gain one. inserting a new
//                         maximum increments nothing at all.

// the unconditional rule is the easier one to keep true,
// which is why the order-statistic form is the one to reach
// for. this file is here so the choice is visible.

public final class KthSmallestLeftCount {
	// Time Complexity: O(h) per query, O(h) per insert
	// Aux Space Complexity: O(h)
	// Total Space Complexity: O(n)

	private Node root;

	public void insert(int data) {
		root = insert(root, data);
	}

	public Optional<Integer> kthSmallest(int k) {
		return kthSmallest(root, k);
	}

	private static Node insert(Node node, int data) {
		if (node == null) {
			return new Node(data);
		}
		if (data < node.data) {
			// the new node lands in MY left subtree
			node.count++;
			node.left = insert(node.left, data);
		} else {
			// it lands on my right, so my left count is unchanged
			node.right = insert(node.right, data);
		}
		return node;
	}

	private static Optional<Integer> kthSmallest(Node node, int k) {
		if (node == null) {
			return absent();
		}
		// a field read, and no hop to the left child first
		int left = node.count;
		if (k == left + 1) {
			// left values come before me, so I am the (left + 1)th
			return of(node.data);
		} else if (left < k) {
			// discard the left subtree AND me, then renumber
			return kthSmallest(node.right, k - left - 1);
		} else {
			// nothing smaller than the left subtree was discarded
			return kthSmallest(node.left, k);
		}
	}

	public static void main(String[] args) {
		KthSmallestLeftCount tree = new KthSmallestLeftCount();
		for (int data : new int[] { 20, 8, 22, 4, 12, 10, 14 }) {
			tree.insert(data);
		}
		// sorted: 4, 8, 10, 12, 14, 20, 22
		System.out.println(tree.kthSmallest(3)); // Optional.of(10)
		System.out.println(tree.kthSmallest(1)); // Optional.of(4)
		System.out.println(tree.kthSmallest(7)); // Optional.of(22)
		System.out.println(tree.kthSmallest(8)); // Optional.absent()
	}

	private static final class Node {
		private final int data;
		private int count;
		private Node left;
		private Node right;

		private Node(int data) {
			this.data = data;
		}
	}
}
