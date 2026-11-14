package com.svetanis.datastructures.tree.binary.bst.rank;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.size;

import java.util.Optional;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Kth largest element in a BST (not a LeetCode problem; LC 230 asks for the kth smallest, and
// this file is its exact mirror)
//
// Input: root -- the root of a binary search tree; k -- a rank, 1 meaning the largest. Returns
// the k-th largest value, or absent when the tree holds fewer than k values.
//
// RANK DESCENT, WITHOUT THE AUGMENTATION THAT MAKES IT WORK, like KthSmallest: kept as a
// baseline, on purpose. For an answer to submit, use KthLargestInorder.java (package bst.paused).
//
// The one idea: the number of nodes in a node's right subtree is the number of values larger than
// the node's own. Call it right. If k == right + 1, the node is the k-th largest. If k is larger,
// the right subtree and the node are all larger than the answer, so the answer is the
// (k - right - 1)-th largest of the left subtree. Otherwise it is the k-th largest of the right
// subtree. Here right is counted by walking the whole right subtree, size(root.right), at every
// level; that walk is what makes this a baseline and not an answer.
//
// Time: O(n^2) worst case -- size(root.right) re-walks the subtree at EVERY level of the descent;
// on a right-leaning chain the walks add up to n^2 / 2 nodes. The descent itself is O(h); an
// order-statistic tree, where each node caches its subtree size (see
// KthSmallestOrderStatistic.java), makes the whole query O(h).
// Space: O(h) -- the recursion and size()'s recursion; the tree itself is O(n).
//
// Known limit: both recursions are as deep as the tree is tall, so a right-leaning chain of about
// 10^4 nodes overflows the default stack.
//
// Siblings: KthSmallest is the exact mirror of this file.

public final class KthLargest {

	public static Optional<Integer> kthLargest(TreeNode root, int k) {
		if (isNull(root)) {
			return empty(); // BASE: fewer than k values
		}
		int right = size(root.right); // COUNT: values larger than this node's
		if (k == right + 1) {
			return of(root.val); // RETURN: this node has exactly k - 1 larger values
		} else if (right < k) {
			return kthLargest(root.left, k - right - 1); // WALK left: discard the right side and this node, renumber
		} else {
			return kthLargest(root.right, k); // WALK right: the answer is among the larger values
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
		System.out.println(kthLargest(root, 5)); // Optional[10]
	}
}