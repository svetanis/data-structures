package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.max;
import static java.lang.Math.min;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 333. Largest BST Subtree

// Given a binary tree, find the largest subtree which is a Binary Search Tree (BST), 
// where largest means subtree with largest number of nodes in it.

public final class LargestBstInBt {
	// Time Complexity: O(n)

	public static int largestBstSubTree(Node root) {
		return dfs(root).size;
	}

	public static Result dfs(Node root) {
		Result result = new Result();
		if (root == null) {
			result.bst = true;
			return result;
		}
		Result left = dfs(root.left);
		Result right = dfs(root.right);
		result.min = min(root.data, left.min);
		result.max = max(root.data, right.max);
		boolean isBst = left.max <= root.data && right.min >= root.data;
		if (left.bst && right.bst && isBst) {
			result.bst = true;
			result.size = 1 + left.size + right.size;
		} else {
			result.bst = false;
			result.size = max(left.size, right.size);
		}
		return result;
	}

	public static void main(String[] args) {
		Node root = newNode(50);
		root.left = newNode(30);
		root.right = newNode(60);
		root.left.left = newNode(5);
		root.left.right = newNode(20);
		root.right.left = newNode(45);
		root.right.right = newNode(70);
		root.right.right.left = newNode(65);
		root.right.right.right = newNode(80);
		System.out.println(largestBstSubTree(root));
	}

	private static class Result {
		private int size; // size of largest bst which is subtree of curr node
		private int min; // min val in subtree
		private int max; // max val in subtree
		private boolean bst; // is valid bst

		public Result() {
			this.size = 0;
			this.min = MAX_VALUE;
			this.max = MIN_VALUE;
			this.bst = false;
		}
	}
}
