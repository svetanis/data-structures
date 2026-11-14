package com.svetanis.datastructures.tree.binary.bst.inorder;

import java.util.ArrayDeque;
import java.util.Deque;

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
// walk visits the values in sorted order, so keep the node visited just before (prev); at each
// node the candidate is its gap to prev, and the answer is the smallest candidate. The walk is
// done with an explicit stack of the nodes whose left subtree is being explored, so a very
// tall tree cannot overflow the call stack.
//
// Assumption: no two neighbours differ by more than Integer.MAX_VALUE. The gap is computed
// in int arithmetic, so a wider gap overflows and comes back as a plausible small number:
// {Integer.MIN_VALUE, Integer.MAX_VALUE} reports its smallest gap as 1. LC's range
// [0, 100000] cannot reach it.
//
// Time: O(n) -- each node is pushed once, popped once and compared with prev once.
// Space: O(h) -- the stack holds one root-to-node path at most.
//
// Siblings: MinAbsDiffBstInOrder is the same walk, recursive; MinAbsDiffBstPreOrder compares
// each node with its nearest smaller and larger ancestor, in pre-order, with no prev.

public final class MinAbsDiffBstIterative {

	private static final int INF = Integer.MAX_VALUE; // "no gap found yet"

	public static int minDiff(TreeNode root) {
		Deque<TreeNode> stack = new ArrayDeque<>();
		TreeNode curr = root;
		TreeNode prev = null;
		int min = INF;
		while (!stack.isEmpty() || curr != null) {
			while (curr != null) { // DESCEND: push the path down the left side
				stack.push(curr);
				curr = curr.left;
			}
			curr = stack.pop(); // VISIT: the smallest value not yet visited
			if (prev != null) {
				int diff = Math.abs(curr.val - prev.val); // GAP: to the neighbour just below
				min = Math.min(min, diff); // KEEP: the smallest gap so far
			}
			prev = curr; // ADVANCE
			curr = curr.right; // WALK: then the larger values
		}
		return min;
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