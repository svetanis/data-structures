package com.svetanis.datastructures.tree.binary.bst.rank;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.size;

import java.util.Optional;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 230. Kth Smallest Element in a BST
//
// Input: root -- the root of a binary search tree; k -- a rank, 1 meaning the smallest. Returns
// the k-th smallest value, or absent when the tree holds fewer than k values. On LeetCode: the
// tree has n nodes, 1 <= k <= n <= 10^4, 0 <= Node.val <= 10^4.
//
// RANK DESCENT, WITHOUT THE AUGMENTATION THAT MAKES IT WORK.
// kept as the baseline to read against
// KthSmallestOrderStatistic.java, which runs this exact
// descent over nodes that cache their own subtree size.
// for an answer to submit, use KthSmallestIterative.java (package bst.paused).
//
// The one idea: the number of nodes in a node's left subtree is the number of values smaller than
// the node's own. Call it left. If k == left + 1, the node is the k-th smallest. If k is larger,
// the left subtree and the node are all smaller than the answer, so the answer is the
// (k - left - 1)-th smallest of the right subtree. Otherwise it is the k-th smallest of the left
// subtree. Here left is counted by walking the whole left subtree, size(root.left), at every
// level; that walk is what makes this a baseline and not an answer.
//
// Time: O(n^2) worst case -- size(root.left) re-walks the subtree at EVERY level of the descent;
// on a left-leaning chain the walks add up to n^2 / 2 nodes. The descent itself is O(h); an
// order-statistic tree, where each node caches its subtree size, makes the whole query O(h).
// Space: O(h) -- the recursion and size()'s recursion; the tree itself is O(n).
//
// Known limit: both recursions are as deep as the tree is tall, so a left-leaning chain of
// 10^4 nodes (LeetCode's largest) overflows the default stack.
//
// Siblings: KthSmallestOrderStatistic -- the same descent with the whole subtree's size cached
// on every node. KthSmallestLeftCount -- the same descent with only the left subtree's size
// cached. KthLargest is the exact mirror of this file.

public final class KthSmallest {

	public static Optional<Integer> kthSmallest(TreeNode root, int k) {
		if (isNull(root)) {
			return empty(); // BASE: fewer than k values
		}
		int left = size(root.left); // COUNT: values smaller than this node's
		if (k == left + 1) {
			return of(root.val); // RETURN: this node has exactly k - 1 smaller values
		} else if (left < k) {
			return kthSmallest(root.right, k - left - 1); // WALK right: discard the left side and this node, renumber
		} else {
			return kthSmallest(root.left, k); // WALK left: the answer is among the smaller values
		}
	}

	public static void main(String[] args) {
		TreeNode root = newNode(20);
		root.left = newNode(8);
		root.right = newNode(22);
		root.left.left = newNode(4);
		root.left.right = newNode(12);
		root.left.right.left = newNode(10);
		root.left.right.right = newNode(14);
		System.out.println(kthSmallest(root, 3)); // Optional[10]
	}
}