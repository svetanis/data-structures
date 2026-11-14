package com.svetanis.datastructures.tree.binary.bt.sumtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 663. Equal Tree Partition
//
// Input: root of a binary tree. Returns true if removing exactly one edge can split it into two
// trees with equal sums. On LeetCode: 1 <= number of nodes <= 10^4, -10^5 <= node.val <= 10^5.
//
// The one idea: removing the edge above a node splits off that node's whole subtree, so the
// two trees have sums s and total - s, and they are equal when s == total / 2. So list the sum of
// every subtree, and ask whether one of them (other than the whole tree) is half the total. An
// odd total can never split in two equal integers. The whole tree's sum is added last by the
// post-order walk, so it is the entry to drop: the root has no edge above it, and without
// dropping it a tree with total 0 would "split" off itself.
//
// Time: O(n) -- one walk, then one pass over the n sums.
// Space: O(n) -- the list of subtree sums, and the recursion stack.
//
// Siblings: EqualTreePartition2 walks the tree twice -- once for the total and once to look for a
// subtree of half the total -- and keeps no list.

public final class EqualTreePartition {

	private List<Integer> list;

	public boolean checkEqualTree(TreeNode root) {
		this.list = new ArrayList<>(); // RESET: the field is shared between calls
		int totalSum = dfs(root);
		if (totalSum % 2 != 0) {
			return false; // ODD: cannot split into two equal integer sums
		}
		list.remove(list.size() - 1); // DROP: the whole tree's sum, added last -- no edge above the root
		return new HashSet<>(list).contains(totalSum / 2); // RETURN: some subtree holds half the total
	}

	private int dfs(TreeNode root) {
		if (root == null) {
			return 0; // BASE: an empty subtree sums to 0
		}
		int left = dfs(root.left); // WALK: children first
		int right = dfs(root.right);
		int sum = root.val + left + right; // SUM: this whole subtree
		list.add(sum); // RECORD: one entry per node, in post-order
		return sum;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(5);
		root.left = newNode(10);
		root.right = newNode(10);
		root.right.left = newNode(2);
		root.right.right = newNode(3);
		EqualTreePartition mst = new EqualTreePartition();
		System.out.println(mst.checkEqualTree(root)); // true

		TreeNode root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(10);
		root1.right.left = newNode(2);
		root1.right.right = newNode(20);
		EqualTreePartition mst2 = new EqualTreePartition();
		System.out.println(mst2.checkEqualTree(root1)); // false

	}
}
