package com.svetanis.datastructures.tree.binary.bst.rank;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.Optional;

// 230. Kth Smallest Element in a BST -- the follow-up, not the submission
//
// Input: insert(data) adds a value to a binary search tree; kthSmallest(k) returns the k-th
// smallest value in it, k = 1 being the smallest, or empty when the tree holds fewer than k
// values. LC 230's constraints: the tree has n nodes, 1 <= k <= n <= 10^4, 0 <= Node.val <= 10^4.
// Its follow-up asks what to do when the BST is modified often and the kth smallest is needed
// frequently.
//
// The one idea: the second way to augment a node, next to KthSmallestOrderStatistic. That file
// stores the size of the whole subtree rooted at a node; this one stores only count, how many
// nodes are in the node's LEFT subtree, which is the number the rank descent actually asks for.
// With left = count read straight off the node: k == left + 1 means this node is the answer; a
// larger k means the answer is the (k - left - 1)-th smallest of the right subtree; a smaller k
// means it is the k-th smallest of the left subtree.
//
// Both ways answer every query identically. They differ in what insert has to do:
//   whole-subtree size -- every node on the search path
//                         gains one descendant. no condition.
//   left-subtree count -- only the nodes whose step went
//                         LEFT gain one. inserting a new
//                         maximum increments nothing at all.
//
// The unconditional rule is the easier one to keep true, which is why the order-statistic form
// is the one to reach for. This file is here so the choice is visible.
//
// Time: O(h) per query, O(h) per insert -- one path down; h is the tree's height, up to n when
// the values arrive in sorted order, because nothing rebalances.
// Space: O(n) for the tree, O(h) for the call stack.
//
// Known limit: insert and kthSmallest recurse as deep as the tree is tall, so 10^4 values
// inserted in sorted order (a chain) overflow the default stack.
// KthSmallestLeftCountIterative has the same node and the same rule with loops.
//
// Siblings: KthSmallestOrderStatistic keeps the whole subtree's size; KthSmallest recounts the
// left subtree at every level.

public final class KthSmallestLeftCount {

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
		if (data < node.data) {
			// the new node lands in MY left subtree
			node.count++; // COUNT: one more node on my left
			node.left = insert(node.left, data); // WALK left
		} else {
			// it lands on my right, so my left count is unchanged
			node.right = insert(node.right, data); // WALK right
		}
		return node;
	}

	private static Optional<Integer> kthSmallest(Node node, int k) {
		if (node == null) {
			return empty(); // BASE: fewer than k values
		}
		// a field read, and no hop to the left child first
		int left = node.count; // COUNT: values smaller than this node's
		if (k == left + 1) {
			// left values come before me, so I am the (left + 1)th
			return of(node.data); // RETURN: exactly k - 1 values are smaller
		} else if (left < k) {
			// discard the left subtree AND me, then renumber
			return kthSmallest(node.right, k - left - 1); // WALK right
		} else {
			// nothing smaller than the left subtree was discarded
			return kthSmallest(node.left, k); // WALK left
		}
	}

	public static void main(String[] args) {
		KthSmallestLeftCount tree = new KthSmallestLeftCount();
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
		private int count;
		private Node left;
		private Node right;

		private Node(int data) {
			this.data = data;
		}
	}
}
