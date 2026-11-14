package com.svetanis.datastructures.tree.binary.bt.sumtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 663. Equal Tree Partition
//
// Input: root of a binary tree. Returns true if removing exactly one edge can split it into two
// trees with equal sums. On LeetCode: 1 <= number of nodes <= 10^4, -10^5 <= node.val <= 10^5.
//
// The one idea: removing the edge above a node splits off that node's whole subtree, so the
// two trees have sums s and total - s, and they are equal when s == total / 2. First walk finds
// the total; an odd total can never split in two equal integer sums. Second walk computes every
// subtree sum on the way up and flags a node other than the root whose subtree sums to half the
// total. The root is excluded because it has no edge above it: with total 0 it would otherwise
// "split" off itself.
//
// Time: O(n) -- two walks.
// Space: O(h) -- the recursion stack of each walk; n on a tree that is one long chain.
//
// Siblings: EqualTreePartition makes one walk and keeps the list of every subtree sum instead of
// walking twice.

public final class EqualTreePartition2 {

	private boolean equalSplit;

	public boolean checkEqualTree(TreeNode root) {
		equalSplit = false; // RESET: the field is shared between calls
		int totalSum = sum(root); // WALK 1: the total
		if (totalSum % 2 != 0) {
			return false; // ODD: cannot split into two equal integer sums
		}
		dfs(root, totalSum / 2, root); // WALK 2: look for a subtree holding half
		return equalSplit;
	}

	private int sum(TreeNode root) {
		if (root == null) {
			return 0; // BASE: an empty subtree sums to 0
		}
		int sum = root.val;
		sum += sum(root.left); // SUM: left subtree
		sum += sum(root.right);
		return sum;
	}

	private int dfs(TreeNode root, int target, TreeNode node) {
		if (node == null) {
			return 0; // BASE: an empty subtree sums to 0
		}
		int sum = node.val;
		sum += dfs(root, target, node.left); // WALK: children first -- their sums
		sum += dfs(root, target, node.right);
		if (sum == target && node != root) {
			equalSplit = true; // RECORD: cutting above this node leaves half on each side
		}
		return sum; // RETURN: sum of this whole subtree
	}

	public static void main(String[] args) {
		TreeNode root = newNode(5);
		root.left = newNode(10);
		root.right = newNode(10);
		root.right.left = newNode(2);
		root.right.right = newNode(3);
		EqualTreePartition2 mst = new EqualTreePartition2();
		System.out.println(mst.checkEqualTree(root)); // true

		TreeNode root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(10);
		root1.right.left = newNode(2);
		root1.right.right = newNode(20);
		EqualTreePartition2 mst2 = new EqualTreePartition2();
		System.out.println(mst2.checkEqualTree(root1)); // false

	}
}
