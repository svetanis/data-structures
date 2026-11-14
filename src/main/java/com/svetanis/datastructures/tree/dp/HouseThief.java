package com.svetanis.datastructures.tree.dp;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 337. House Robber III
//
// Input: the root of a binary tree; each node is a house holding TreeNode.val money. Robbing two
// houses joined by an edge -- a parent and its child -- trips the alarm. Returns the most money
// that can be robbed. On LeetCode: 1 <= nodes <= 10^4, 0 <= Node.val <= 10^4.
//
// The one idea: a subtree cannot report one best number, because its parent needs to know whether
// that best robbed the subtree's root. So each subtree reports two: incl, the most with its root
// robbed, and excl, the most with its root left alone. If the root is robbed, its children must
// be left alone, so incl = val + left.excl + right.excl. If the root is left alone, each child
// is free to do whichever is better, so excl = max(left.incl, left.excl) + max(right.incl,
// right.excl). A missing child reports (0, 0). The answer is the better of the root's two.
// maxProfit carries the pair in a Profit object; maxProfitArray is the same algorithm with an
// int[2] in place of the Profit object -- [0] is the most with the root robbed, [1] the most with
// the root left alone.
//
// Time: O(n) for both -- one call per node, constant work in each.
// Space: O(h) for both -- the recursion stack. h is lg n only on a balanced tree; a skewed one
// makes it n.
//
// Siblings: HouseThiefRecursive and HouseThiefTopDown take the other view: the best for a node is "rob it
// and skip to the grandchildren" or "skip it and use the children", with no pair to carry.

public final class HouseThief {

	public static int maxProfit(TreeNode root) {
		if (root == null) {
			return 0;
		}
		Profit p = profit(root);
		return max(p.incl, p.excl); // RETURN: the root robbed or not, whichever is better
	}

	private static Profit profit(TreeNode node) {
		if (node == null) {
			return new Profit(0, 0); // BASE: no house, no money either way
		}
		Profit left = profit(node.left);
		Profit right = profit(node.right);
		int incl = node.val + left.excl + right.excl; // ROB: this house, so its children are skipped
		int excl = max(left.excl, left.incl) + max(right.excl, right.incl); // SKIP: each child does its best
		return new Profit(incl, excl);
	}

	public static int maxProfitArray(TreeNode root) {
		if (root == null) {
			return 0;
		}
		int[] profit = dfs(root);
		return max(profit[0], profit[1]); // RETURN: the root robbed or not, whichever is better
	}

	// [0] - max profit if node is robbed
	// [1] - max profit if node is not robbed
	private static int[] dfs(TreeNode node) {
		if (node == null) {
			return new int[] {0, 0}; // BASE: no house, no money either way
		}
		int[] left = dfs(node.left);
		int[] right = dfs(node.right);
		int[] profit = new int[2];
		profit[0] = node.val + left[1] + right[1]; // ROB: this house, so its children are skipped
		profit[1] = max(left[0], left[1]) + max(right[0], right[1]); // SKIP: each child does its best
		return profit;
	}

	public static void main(String[] args) {
		TreeNode root1 = newNode(3);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.right = newNode(3);
		root1.right.right = newNode(1);
		System.out.println(maxProfit(root1)); // 7
		System.out.println(maxProfitArray(root1)); // 7

		TreeNode root2 = newNode(3);
		root2.left = newNode(4);
		root2.right = newNode(5);
		root2.left.left = newNode(1);
		root2.left.right = newNode(3);
		root2.right = newNode(5);
		root2.right.right = newNode(1);
		System.out.println(maxProfit(root2)); // 9
		System.out.println(maxProfitArray(root2)); // 9

	}

	private static class Profit {
		int incl;
		int excl;

		public Profit(int incl, int excl) {
			this.incl = incl;
			this.excl = excl;
		}
	}
}
