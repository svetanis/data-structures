package com.svetanis.datastructures.tree.binary.bt.leaves;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 366. Find Leaves of Binary Tree
//
// Input: the root of a binary tree. Collect the leaves -- nodes with no children -- remove them,
// then collect the leaves of what is left, and repeat until the tree is empty. Returns the values
// of each round, in order. On LeetCode: 1 <= nodes <= 100, -100 <= Node.val <= 100; within a
// round the values come in left-to-right order.
//
// The one idea: a node is removed in the round that follows the removal of its last child, so its
// round is 1 more than the later of its two children's rounds. A leaf has no children, so it goes
// in round 0 -- which is why a missing child counts as round -1. That number is the height of the
// node measured from the bottom: the longest path down to a leaf. One post-order pass computes it
// for every node and drops the value into the list for that round; no tree is ever changed.
//
// Time: O(n) -- one call per node, and each value is added once.
// Space: O(n) -- the output holds every value; the recursion stack adds O(h).

public final class LeavesOfBinaryTree {

	public static List<List<Integer>> leaves(TreeNode root) {
		List<List<Integer>> list = new ArrayList<>();
		dfs(root, list);
		return list;
	}

	private static int dfs(TreeNode root, List<List<Integer>> list) {
		if (root == null) {
			return -1; // BASE: a missing child, so a leaf gets round 0
		}
		int left = dfs(root.left, list); // WALK: the left subtree's round
		int right = dfs(root.right, list); // WALK: the right subtree's round
		int level = 1 + Math.max(left, right); // ROUND: one after its later child
		while (list.size() <= level) {
			list.add(new ArrayList<>()); // GROW: open the list for a new round
		}
		list.get(level).add(root.val); // RECORD: this value in its round
		return level;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		System.out.println(leaves(root)); // [[4, 5, 3], [2], [1]]
	}
}
