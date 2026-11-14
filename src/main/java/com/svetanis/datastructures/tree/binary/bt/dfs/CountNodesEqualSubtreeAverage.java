package com.svetanis.datastructures.tree.binary.bt.dfs;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 2265. Count Nodes Equal to Average of Subtree
//
// Input: root of a binary tree. The average of a subtree is the sum of its values divided by its
// node count, rounded down; the subtree of a node is the node and everything below it. Returns
// the number of nodes whose value equals the average of their own subtree. On LeetCode:
// 1 <= number of nodes <= 1000, 0 <= node.val <= 1000.
//
// The one idea: a node's subtree is its two children's subtrees plus itself, so its sum, its node
// count and the number of matches below it all come from the children's three numbers. dfs
// returns those three numbers (sum, size, count) for each subtree; the node checks itself against
// sum / size, which is already rounded down because every value is non-negative.
//
// Time: O(n) -- every node is visited once, with constant work per node.
// Space: O(h) -- the recursion stack, h the height; one small Result per call on the stack.

public final class CountNodesEqualSubtreeAverage {

	public static int count(TreeNode root) {
		return dfs(root).count;
	}

	private static Result dfs(TreeNode node) {
		if (isNull(node)) {
			return new Result(0, 0, 0); // BASE: no sum, no nodes, no matches
		}
		Result left = dfs(node.left); // WALK: both subtrees first
		Result right = dfs(node.right);
		int sum = node.val + left.sum + right.sum; // SUM: this subtree
		int size = 1 + left.size + right.size; // SIZE: this subtree
		int count = left.count + right.count;
		if (node.val == sum / size) {
			count += 1; // TAKE: the node equals its subtree's average
		}
		return new Result(sum, size, count); // RETURN: the three numbers for the parent
	}

	public static void main(String[] args) {
		TreeNode root = newNode(4);
		root.left = newNode(8);
		root.right = newNode(5);
		root.left.left = newNode(0);
		root.left.right = newNode(1);
		root.right.right = newNode(6);
		System.out.println(count(root)); // 5

		TreeNode root2 = newNode(1);
		System.out.println(count(root2)); // 1
	}

	private static class Result {
		private int sum;
		private int size;
		private int count;

		public Result(int sum, int size, int count) {
			this.sum = sum;
			this.size = size;
			this.count = count;
		}
	}
}