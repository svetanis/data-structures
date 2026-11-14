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
// The one idea: in sorted order the closest pair of values is always two NEIGHBOURS -- if two
// values had another value between them, either of them is closer to that one. The in-order
// walk visits the values in sorted order, so keep the value visited just before (prev); at
// each node the candidate is its gap to prev, and the answer is the smallest candidate. The
// first node has no prev and gives no candidate.
//
// Assumption: no two neighbours differ by more than Integer.MAX_VALUE. The gap is computed
// in int arithmetic, so a wider gap overflows and comes back as a plausible small number:
// {Integer.MIN_VALUE, Integer.MAX_VALUE} reports its smallest gap as 1. LC's range
// [0, 100000] cannot reach it.
//
// Time: O(n) -- each node is visited once and compared with prev once.
// Space: O(h) -- the recursion stack; prev and the minimum are two numbers.
//
// Siblings: MinAbsDiffBstIterative is the same walk with an explicit stack;
// MinAbsDiffBstPreOrder compares each node with its nearest smaller and larger ancestor, in
// pre-order, with no prev.

public final class MinAbsDiffBstInOrder {

	private static final int INF = Integer.MAX_VALUE; // "none yet" for both prev and the minimum

	public static int minDiff(TreeNode root) {
		AtomicInteger min = new AtomicInteger(INF);
		AtomicInteger prev = new AtomicInteger(INF);
		inOrder(root, prev, min);
		return min.get();
	}

	private static void inOrder(TreeNode root, AtomicInteger prev, AtomicInteger min) {
		if (root == null) { // BASE: empty subtree
			return;
		}
		inOrder(root.left, prev, min); // WALK: smaller values first
		if (prev.get() != INF) { // the first node has no neighbour below it
			int diff = Math.abs(root.val - prev.get()); // GAP: to the neighbour just below
			min.set(Math.min(min.get(), diff)); // KEEP: the smallest gap so far
		}
		prev.set(root.val); // ADVANCE: this node is the neighbour for the next one
		inOrder(root.right, prev, min);
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
	}
}