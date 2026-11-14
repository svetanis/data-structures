package com.svetanis.datastructures.tree.binary.bst.rank;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.size;
import static java.util.Optional.empty;

import java.util.Optional;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// The k-th node of an inorder walk of a binary tree (not a LeetCode problem; LC 230 asks the
// same on a binary search tree, where the k-th node of the inorder walk is the k-th smallest)
//
// Input: root -- the root of any binary tree, not necessarily a search tree; k -- a rank, 1
// meaning the first node of the inorder walk. Returns that node, or absent when k < 1 or the
// tree holds fewer than k nodes.
//
// The one idea: an inorder walk visits the whole left subtree, then the node, then the whole
// right subtree. So the left subtree's size, left, decides where the node stands: it is number
// left + 1. If left == k - 1, this node is the k-th. If left > k - 1, the k-th node is the k-th
// of the left subtree. If left < k - 1, the k-th node is number k - (left + 1) of the right
// subtree. The descent never compares a value with anything, so it needs no search-tree order,
// only the sizes. Here left is counted by walking the whole left subtree at every level, so this
// is the unaugmented baseline, like KthSmallest.
//
// Time: O(n^2) worst case -- size(root.left) re-walks the subtree at EVERY level of the descent,
// the same shape as KthSmallest.java. The descent itself is O(h); it is the repeated size() that
// is not.
// Space: O(h) -- the descent is a loop and keeps nothing; size()'s recursion is as deep as the
// left subtree is tall.
//
// Siblings: KthSmallest does the same descent on a search tree, by recursion, and returns the
// value instead of the node.

public final class KthNode {

	public static Optional<TreeNode> kthNode(TreeNode root, int k) {
		while (isNotNull(root) && k > 0) {
			int left = isNull(root.left) ? 0 : size(root.left); // COUNT: nodes before this one in the walk
			if (left < k - 1) {
				k = k - (left + 1); // WALK right: skip the left side and this node, renumber
				root = root.right;
			} else if (left == k - 1) {
				return Optional.of(root); // RETURN: exactly k - 1 nodes come before this one
			} else {
				// leftSize > k - 1
				root = root.left; // WALK left: the k-th node is among the nodes before this one
			}
		}
		return empty(); // RETURN: k < 1, or fewer than k nodes
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.right = new TreeNode(6);

		inOrder(root); // 4 2 5 1 3 6
		System.out.println();
		System.out.println();
		for (int k = 1; k <= 6; k++) {
			System.out.println(kthNode(root, k)); // Optional[4], [2], [5], [1], [3], [6] in turn
		}
	}
}