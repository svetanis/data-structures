package com.svetanis.datastructures.tree.binary.bst.rank;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.Optional;

// 230. Kth Smallest Element in a BST -- the follow-up, not the submission
//
// Input: insert(data) adds a value to a binary search tree; kthSmallest(k) returns the k-th
// smallest value in it, k = 1 being the smallest, or empty when the tree holds fewer than k
// values. LC 230's constraints: the tree has n nodes, 1 <= k <= n <= 10^4, 0 <= Node.val <= 10^4.
// Its follow-up asks: "what if the BST is modified often and you need to find the kth smallest
// frequently?"
//
// The one idea: every node stores size, the number of nodes in the subtree rooted at it. insert
// keeps it right on the way down, since the new node lands below every node on its path. The
// search then reads the left subtree's size in one step: if k is one more than it, this node is
// the answer; if k is larger, the answer is in the right subtree, as its (k - left - 1)-th
// smallest; otherwise it is in the left subtree, still the k-th.
//
// LC 230 hands you a tree you did not build, so there is no size field and this cannot be
// submitted. The siblings in this package (* is this file):
//
//   file                        how it finds the k-th smallest
// * KthSmallestOrderStatistic   the same descent, the whole subtree's size kept on every node
//   KthSmallest                 the same descent, recounting the left subtree at every level
//   KthSmallestLeftCount        the same descent, each node keeping only its left subtree's size
//
// KthSmallestIterative, in the bst.paused package, is the O(h + k) answer that gets submitted.
//
// Time: O(h) per query, O(h) per insert -- one path down; h is the tree's height, up to n when
// the values arrive in sorted order, because nothing rebalances.
// Space: O(n) for the tree, O(h) for the call stack.
//
// Known limit: insert and kthSmallest recurse as deep as the tree is tall, so 10^4 values
// inserted in sorted order (a chain) overflow the default stack.
// KthSmallestOrderStatisticIterative has the same node and the same rule with loops.

public final class KthSmallestOrderStatistic {

	private Node root;

	public void insert(int data) {
		root = insert(root, data);
	}

	public Optional<Integer> kthSmallest(int k) {
		return kthSmallest(root, k);
	}

	private static Node insert(Node node, int data) {
		if (node == null) {
			return new Node(data); // BASE: the empty spot gets the new leaf
		}
		// the node being added lands somewhere below,
		// so every node on the path gains one descendant
		node.size++; // COUNT: one more node in this subtree
		if (data < node.data) {
			node.left = insert(node.left, data); // WALK left
		} else {
			node.right = insert(node.right, data); // WALK right
		}
		return node;
	}

	private static Optional<Integer> kthSmallest(Node node, int k) {
		if (node == null) {
			return empty(); // BASE: fewer than k values
		}
		int left = size(node.left); // COUNT: values smaller than this node's, a field read
		if (k == left + 1) {
			return of(node.data); // RETURN: exactly k - 1 values are smaller
		} else if (left < k) {
			return kthSmallest(node.right, k - left - 1); // WALK right: discard the left side and this node, renumber
		} else {
			return kthSmallest(node.left, k); // WALK left: the answer is among the smaller values
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
		System.out.println(tree.kthSmallest(3)); // Optional[10]
		System.out.println(tree.kthSmallest(1)); // Optional[4]
		System.out.println(tree.kthSmallest(7)); // Optional[22]
		System.out.println(tree.kthSmallest(8)); // Optional.empty: only seven values
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
