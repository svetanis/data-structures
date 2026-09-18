package com.svetanis.datastructures.tree.binary.bst.rank;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;

import com.google.common.base.Optional;

// 230. K-th Smallest BST -- the follow-up, not the submission

// "what if the BST is modified often and you need
//  to find the k'th smallest frequently?"

// ORDER-STATISTIC TREE
// every node stores the size of the subtree rooted at it.
// the size is maintained on the way down during insert,
// so the rank descent reads it in O(1) and never re-walks
// a subtree. KthSmallest does the same descent WITHOUT the
// augmentation and pays O(n) per level to recompute it.

// LC 230 hands you a tree you did not build, so this
// cannot be submitted -- KthSmallestIterative is the
// O(h + k) answer that gets submitted. This is the answer
// to the follow-up question that comes after it.

public final class KthSmallestOrderStatistic {
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
		// the node being added lands somewhere below,
		// so every node on the path gains one descendant
		node.size++;
		if (data < node.data) {
			node.left = insert(node.left, data);
		} else {
			node.right = insert(node.right, data);
		}
		return node;
	}

	private static Optional<Integer> kthSmallest(Node node, int k) {
		if (node == null) {
			return absent();
		}
		int left = size(node.left);
		if (k == left + 1) {
			return of(node.data);
		} else if (left < k) {
			return kthSmallest(node.right, k - left - 1);
		} else {
			return kthSmallest(node.left, k);
		}
	}

	private static int size(Node node) {
		return node == null ? 0 : node.size;
	}

	public static void main(String[] args) {
		KthSmallestOrderStatistic tree = new KthSmallestOrderStatistic();
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
		private int size;
		private Node left;
		private Node right;

		private Node(int data) {
			this.data = data;
			this.size = 1;
		}
	}
}
