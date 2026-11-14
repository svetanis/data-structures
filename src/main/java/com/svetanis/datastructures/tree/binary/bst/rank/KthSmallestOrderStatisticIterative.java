package com.svetanis.datastructures.tree.binary.bst.rank;

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
// smallest; otherwise it is in the left subtree, still the k-th. Both walks go down one path and
// never come back up, so a loop does them with no recursion.
//
// Time: O(h) per query, O(h) per insert -- one path down; h is the tree's height.
// Space: O(n) for the tree, O(1) extra.
//
// Siblings: KthSmallestOrderStatistic -- the same node and rule by recursion; it overflows the
// default stack on 10^4 values inserted in sorted order.

public final class KthSmallestOrderStatisticIterative {

	private Node root;

	public void insert(int data) {
		Node fresh = new Node(data);
		if (root == null) {
			root = fresh; // BASE: the first node is the root
			return;
		}
		Node node = root;
		Node parent = null;
		boolean wentLeft = false;
		while (node != null) {
			node.size++; // COUNT: the new node lands below, one more descendant
			parent = node;
			wentLeft = data < node.data;
			node = wentLeft ? node.left : node.right; // WALK: smaller goes left, the rest right
		}
		if (wentLeft) {
			parent.left = fresh; // PLACE: the empty spot on the left
		} else {
			parent.right = fresh; // PLACE: the empty spot on the right
		}
	}

	public Optional<Integer> kthSmallest(int k) {
		Node node = root;
		while (node != null) {
			int left = size(node.left); // COUNT: values smaller than this node's, a field read
			if (k == left + 1) {
				return Optional.of(node.data); // RETURN: exactly k - 1 values are smaller
			} else if (left < k) {
				k = k - left - 1; // WALK right: discard the left side and this node, renumber
				node = node.right;
			} else {
				node = node.left; // WALK left: the answer is among the smaller values
			}
		}
		return Optional.empty(); // RETURN: fewer than k values
	}

	private static int size(Node node) {
		return node == null ? 0 : node.size;
	}

	public static void main(String[] args) {
		KthSmallestOrderStatisticIterative tree = new KthSmallestOrderStatisticIterative();
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
