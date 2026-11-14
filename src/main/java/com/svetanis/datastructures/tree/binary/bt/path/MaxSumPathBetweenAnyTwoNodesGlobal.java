package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 124. Binary Tree Maximum Path Sum
//
// Input: the root of a binary tree. A path is a chain of nodes joined by edges, visiting each
// node at most once; it may start and end anywhere and need not pass through the root.
// Returns the largest sum of values on any path with at least one node. On LeetCode:
// 1 <= n <= 3 * 10^4, -1000 <= val <= 1000.
//
// The one idea: the best path has a highest node, the one nearest the root; the path goes down
// from it into at most two subtrees. Each node returns the best sum of a path that starts at it
// and goes DOWN one way only (0 if every such path is negative: the path may stop and skip a
// harmful subtree). At a node, value + best down left + best down right is the best path whose
// highest node is this one; the field keeps the largest of those over all nodes.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack (h = height, n on a chain).
//
// Known limit: a chain about 3 * 10^4 nodes deep (the most the constraints allow) overflows the
// default thread stack, because the recursion is as deep as the tree. It only happens on very
// large input.
//
// Siblings: MaxSumPathBetweenAnyTwoNodes is the same method with the best passed down in an
// AtomicInteger, which keeps it static; here it is a field, so a call needs an instance and the
// field is reset on every call. MaxSumPathBetweenAnyTwoNodesIterative is the walk without
// recursion.
public final class MaxSumPathBetweenAnyTwoNodesGlobal {

	private int max;

	public int maxSum(TreeNode root) {
		this.max = Integer.MIN_VALUE; // BEST: smaller than any sum; reset for every call
		dfs(root);
		return max;
	}

	// returns the best sum of a downward path starting at root (at least 0, meaning "skip it");
	// records into max the best path whose highest node is root
	private int dfs(TreeNode root) {
		if (root == null) { // BASE: an empty subtree adds nothing
			return 0;
		}
		int left = Math.max(0, dfs(root.left)); // DOWN LEFT: ignore a negative side
		int right = Math.max(0, dfs(root.right)); // DOWN RIGHT: ignore a negative side
		int sum = root.val + left + right; // FORK: the best path with root as its highest node
		max = (Math.max(max, sum)); // RECORD: the best so far
		return root.val + Math.max(left, right); // RETURN: a parent can use only one side
	}

	public static void main(String[] args) {
		// Max sum is 27 = 3 + 6 + 9 + 0 - 1 + 10
		TreeNode root = newNode(-15);
		root.left = newNode(5);
		root.right = newNode(6);
		root.left.left = newNode(-8);
		root.left.right = newNode(1);
		root.right.left = newNode(3);
		root.right.right = newNode(9);
		root.left.left.left = newNode(2);
		root.left.left.right = newNode(6);
		root.right.right.right = newNode(0);
		root.right.right.right.left = newNode(4);
		root.right.right.right.right = newNode(-1);
		root.right.right.right.right.left = newNode(10);
		MaxSumPathBetweenAnyTwoNodesGlobal msp = new MaxSumPathBetweenAnyTwoNodesGlobal();
		System.out.println(msp.maxSum(root)); // 27

		// Max sum is 42 = 20 + 2 + 10 + 10
		TreeNode root2 = newNode(10);
		root2.left = newNode(2);
		root2.right = newNode(10);
		root2.left.left = newNode(20);
		root2.left.right = newNode(1);
		root2.right.right = newNode(-25);
		root2.right.right.left = newNode(3);
		root2.right.right.right = newNode(4);
		MaxSumPathBetweenAnyTwoNodesGlobal msp2 = new MaxSumPathBetweenAnyTwoNodesGlobal();
		System.out.println(msp2.maxSum(root2)); // 42
	}
}
