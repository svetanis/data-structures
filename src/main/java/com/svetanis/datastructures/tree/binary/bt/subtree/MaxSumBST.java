package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1373. Maximum Sum BST in Binary Tree
//
// Input: the root of a binary tree. A subtree (a node with everything below it) that is a binary
// search tree has every value in its left part smaller than the node and every value in its right
// part larger, with no equal values. Returns the largest sum of values over all subtrees that are
// binary search trees; an empty one counts, so the answer is never negative. On LeetCode:
// 1 <= number of nodes <= 4 * 10^4, -4 * 10^4 <= Node.val <= 4 * 10^4.
//
// The one idea: one call reports four facts about one node's subtree: is it a search tree, its
// smallest value, its largest value, and its sum. A node's subtree is a search tree when both
// children's subtrees are, the node is larger than the left part's largest value, and smaller than
// the right part's smallest. Then its smallest is the left part's smallest (or the node itself when
// the left is empty), its largest likewise on the right, and its sum is the node's value plus both
// sums. An empty subtree reports "is a search tree, smallest = INF, largest = -INF, sum 0", which
// lets every comparison pass without special cases. A subtree that fails reports "no", and the
// facts it carries are never read, because its parent fails too.
//
// This is the same post-order report as bst/bounds/LargestBstInBt.java (333) with a fourth field,
// the sum. That file uses null for "no bound on this side" because 333 allows every int as a
// value; here the values are -4e4..4e4, so INF is 26,000x outside the range and can be an ordinary
// number.
//
// The sum cannot overflow either: 4e4 nodes at 4e4 each is 1.6e9, against Integer.MAX_VALUE of
// 2.1e9. That headroom is only 1.3x, so a long is what to reach for if the constraints ever widen.
//
// An empty search tree scores 0, which is why maxSum starts at 0 and is only ever raised: a tree of
// all negative values returns 0, not its largest single node.
//
// Time: O(n) -- one call per node, constant work in each.
// Space: O(h) -- the recursion stack; h is the height of the tree, n for a chain.
//
// Known limit: the recursion goes one call deeper per level, so a chain of about 4 * 10^4 nodes
// (each node the only child of the one before) overflows the default Java stack. It happens only
// on a very deep input; MaxSumBSTIterative is the same method with an explicit stack.
//
// Siblings: MaxSumBSTIterative -- the same four facts without recursion.

public final class MaxSumBST {

	private static final int INF = 1 << 30;

	private int maxSum; // BEST: the largest search-tree sum so far

	public int maxSum(TreeNode root) {
		this.maxSum = 0; // RESET: the empty search tree scores 0
		dfs(root); // WALK: every search-tree subtree offers its sum
		return maxSum; // RETURN: the largest sum
	}

	// returns { 1 if a search tree else 0, smallest value, largest value, sum }
	private int[] dfs(TreeNode node) {
		if (node == null) {
			return new int[] { 1, INF, -INF, 0 }; // BASE: empty is a search tree that blocks nothing
		}
		int curr = node.val;
		int[] left = dfs(node.left); // WALK: facts about the left subtree
		int[] right = dfs(node.right); // WALK: facts about the right subtree
		boolean valid = curr > left[2] && curr < right[1]; // CHECK: strictly between the two parts
		if (valid && left[0] == 1 && right[0] == 1) {
			int sum = curr + left[3] + right[3];
			maxSum = Math.max(maxSum, sum); // RECORD: keep the largest
			int minVal = Math.min(left[1], curr); // SMALLEST: from the left part, else this node
			int maxVal = Math.max(right[2], curr); // LARGEST: from the right part, else this node
			return new int[] { 1, minVal, maxVal, sum };
		}
		return new int[] { 0, 0, 0, 0 }; // SKIP: not a search tree, so neither is any ancestor
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(4);
		root.right = newNode(3);
		root.left.left = newNode(2);
		root.left.right = newNode(4);
		root.right.left = newNode(2);
		root.right.right = newNode(5);
		root.right.right.left = newNode(4);
		root.right.right.right = newNode(6);
		MaxSumBST mst = new MaxSumBST();
		System.out.println(mst.maxSum(root)); // 20

		TreeNode root2 = newNode(4);
		root2.left = newNode(3);
		root2.left.left = newNode(1);
		root2.left.right = newNode(2);
		MaxSumBST mst2 = new MaxSumBST();
		System.out.println(mst2.maxSum(root2)); // 2
	}
}
