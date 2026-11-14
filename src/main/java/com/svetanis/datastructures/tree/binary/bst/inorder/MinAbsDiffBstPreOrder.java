package com.svetanis.datastructures.tree.binary.bst.inorder;

import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 530. Minimum Absolute Difference in BST
//
// Input: the root of a binary search tree with at least two nodes. Returns the smallest
// absolute difference between the values of any two different nodes. On LeetCode: 2 to 10^4
// nodes, 0 <= node.val <= 10^5. (LC 783, Minimum Distance Between BST Nodes, is the same
// problem.)
//
// The one idea: in sorted order the closest pair of values is always two NEIGHBOURS. Every
// node's neighbours are the nearest ancestor smaller than it (low) and the nearest ancestor
// larger than it (high), or, if the node has a left or right subtree, the largest value of
// the left subtree and the smallest of the right subtree. The pair with a subtree value is
// counted when that subtree's node is visited, with this node as its low or high. So every
// node is compared with the low and the high that the walk carries down: going left, the node
// becomes the new high; going right, it becomes the new low. A side with no ancestor has no
// bound, which is null (not Integer.MAX_VALUE: a node holding that value would be taken for
// "no bound" and its pairs never compared). Visiting order does not matter, so the walk is
// pre-order and needs no prev.
//
// Assumption: no two neighbours differ by more than Integer.MAX_VALUE. The gap is computed
// in int arithmetic, so a wider gap overflows and comes back as a plausible small number:
// {Integer.MIN_VALUE, Integer.MAX_VALUE} reports its smallest gap as 1. LC's range
// [0, 100000] cannot reach it.
//
// Known limit: the recursion is as deep as the tree, so a one-sided chain of about six
// thousand nodes or more overflows the default stack (LC allows 10^4 nodes).
// MinAbsDiffBstPreOrderIterative is the same walk with an explicit stack.
//
// Time: O(n) -- each node is visited once and compared with at most two bounds.
// Space: O(h) -- the recursion stack.
//
// Siblings: MinAbsDiffBstPreOrderIterative is this walk with an explicit stack;
// MinAbsDiffBstInOrder and MinAbsDiffBstIterative walk in order and compare each node with
// the one visited just before it.

public final class MinAbsDiffBstPreOrder {

	private static final int INF = Integer.MAX_VALUE; // "no gap found yet"

	public static int minDiff(TreeNode root) {
		AtomicInteger min = new AtomicInteger(INF);
		preOrder(root, null, null, min); // no ancestor yet on either side
		return min.get();
	}

	private static void preOrder(TreeNode root, Integer low, Integer high, AtomicInteger min) {
		if (root == null) { // BASE: empty subtree
			return;
		}
		if (low != null) {
			int diff = Math.abs(root.val - low); // GAP: to the nearest smaller ancestor
			min.set(Math.min(min.get(), diff));
		}
		if (high != null) {
			int diff = Math.abs(root.val - high); // GAP: to the nearest larger ancestor
			min.set(Math.min(min.get(), diff));
		}
		preOrder(root.left, low, root.val, min); // GO LEFT: this node is the new high
		preOrder(root.right, root.val, high, min); // GO RIGHT: this node is the new low
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(4);
		root.left = new TreeNode(2);
		root.right = new TreeNode(6);
		root.left.left = new TreeNode(1);
		root.left.right = new TreeNode(3);
		System.out.println(minDiff(root)); // 1

		TreeNode root2 = new TreeNode(1);
		root2.left = new TreeNode(0);
		root2.right = new TreeNode(48);
		root2.right.left = new TreeNode(12);
		root2.right.right = new TreeNode(49);
		System.out.println(minDiff(root2)); // 1

		// [236,104,701,null,227,null,911]
		TreeNode root3 = new TreeNode(236);
		root3.left = new TreeNode(104);
		root3.right = new TreeNode(701);
		root3.left.right = new TreeNode(227);
		root3.right.right = new TreeNode(911);
		System.out.println(minDiff(root3)); // 9

		// the larger of the two nodes holds Integer.MAX_VALUE, which is also INF; its pair
		// with 5 is still compared because a missing bound is null, not INF
		TreeNode root4 = new TreeNode(Integer.MAX_VALUE);
		root4.left = new TreeNode(5);
		System.out.println(minDiff(root4)); // 2147483642
	}
}