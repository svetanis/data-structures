package com.svetanis.datastructures.tree.binary.bt.dfs;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1469. Find All The Lonely Nodes
//
// Input: root of a binary tree. A node is lonely when its parent has no other child, so it is
// the only child. Returns the values of the lonely nodes, in any order. The root is never
// lonely. On LeetCode: 1 <= number of nodes <= 1000, 1 <= node.val <= 10^6.
//
// The one idea: look at every node as a parent. A node with exactly one child has one lonely
// child, and that child's value goes in the answer. A leaf has no children and a node with two
// children has no lonely child, so neither adds anything. Visiting every node once finds every
// lonely node once.
//
// Time: O(n) -- every node is visited once, with constant work per node.
// Space: O(h) -- the recursion stack, h the height; the answer list can hold up to n / 2 values.

public final class AllLonelyNodes {

	public static List<Integer> lonelyNodes(TreeNode root) {
		List<Integer> list = new ArrayList<>();
		dfs(root, list); // WALK: preorder, every node as a parent
		return list;
	}

	private static void dfs(TreeNode node, List<Integer> list) {
		if (node == null) {
			return; // BASE: nothing below an empty spot
		}
		if (node.left == null && node.right == null) {
			return; // SKIP: a leaf has no child to be lonely
		}
		if (node.left == null) {
			list.add(node.right.val); // RECORD: the right child is an only child
		}
		if (node.right == null) {
			list.add(node.left.val); // RECORD: the left child is an only child
		}
		dfs(node.left, list); // WALK: left subtree
		dfs(node.right, list); // WALK: right subtree
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.right.left = new TreeNode(5);
		root.right.left.left = new TreeNode(6);
		System.out.println(lonelyNodes(root)); // [4, 5, 6]
	}
}
