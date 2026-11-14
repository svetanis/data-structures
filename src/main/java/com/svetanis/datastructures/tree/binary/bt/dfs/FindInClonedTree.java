package com.svetanis.datastructures.tree.binary.bt.dfs;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1379. Find a Corresponding Node of a Binary Tree in a Clone of That Tree
//
// Input: the root of a binary tree (original), the root of an exact copy of it (cloned), and a
// target node of the original. Returns the node of the cloned tree at the same place as the
// target. On LeetCode: 1 <= number of nodes <= 10^4, node values are unique in the original
// (the follow-up allows repeated values), and the target is a node of the original.
//
// The one idea: the two trees have the same shape, so walking them together with one pair of
// pointers keeps each original node next to its clone. When the original pointer is the target,
// the cloned pointer is the answer. The target is matched by identity (==), never by value, so
// repeated values cannot confuse it.
//
// Time: O(n) -- at worst every node is visited once.
// Space: O(h) -- the recursion stack, h the height.

public final class FindInClonedTree {

	private TreeNode target;

	public final TreeNode targetCopy(TreeNode original, TreeNode cloned, TreeNode target) {
		this.target = target; // KEEP: the node to look for, by identity
		return dfs(original, cloned);
	}

	private TreeNode dfs(TreeNode original, TreeNode cloned) {
		if (original == null) {
			return null; // BASE: the target is not under an empty spot
		}
		if (original == target) {
			return cloned; // RETURN: the clone standing in the same place
		}

		TreeNode left = dfs(original.left, cloned.left); // WALK: both trees, one step left
		if (left != null) {
			return left; // RETURN: found on the left, stop
		}
		return dfs(original.right, cloned.right); // WALK: both trees, one step right
	}

	public static void main(String[] args) {
		TreeNode target = new TreeNode(3);
		FindInClonedTree fct = new FindInClonedTree();
		TreeNode root = new TreeNode(7);
		root.left = new TreeNode(4);
		root.right = target;
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(19);

		TreeNode root1 = new TreeNode(7);
		root1.left = new TreeNode(4);
		root1.right = new TreeNode(3);
		root1.right.left = new TreeNode(6);
		root1.right.right = new TreeNode(19);

		System.out.println(fct.targetCopy(root, root1, target)); // 3
		System.out.println(fct.targetCopy(root, root1, target) == root1.right); // true
	}
}
