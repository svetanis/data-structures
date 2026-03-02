package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 333. Largest BST Subtree

// Given a binary tree, find the largest subtree 
// which is a Binary Search Tree (BST), 
// where largest means subtree with largest number of nodes in it.

// a[0]: min value in the subtree
// a[1]: max value in the subtree
// a[2]: size of BST if valid, 0 otherwise

public final class LargestBstInBt {
	// Time Complexity: O(n)
	// Space Complexity: O(h)

	private static final int MAX = Integer.MAX_VALUE;
	private static final int MIN = Integer.MIN_VALUE;

	private int maxSize;

	public int largestBstSubTree(Node root) {
		this.maxSize = 0;
		dfs(root);
		return maxSize;
	}

	private int[] dfs(Node root) {
		if (root == null) {
			return new int[] { MAX, MIN, 0 };
		}
		int[] left = dfs(root.left);
		int[] right = dfs(root.right);
		if (left[1] < root.data && root.data < right[0]) {
			int size = 1 + left[2] + right[2];
			maxSize = Math.max(maxSize, size);
			int min = Math.min(root.data, left[0]);
			int max = Math.max(root.data, right[1]);
			return new int[] { min, max, size };
		}
		return new int[] { MIN, MAX, 0 };
	}

	public static void main(String[] args) {
		LargestBstInBt lbst = new LargestBstInBt();
		Node root = newNode(50);
		root.left = newNode(30);
		root.right = newNode(60);
		root.left.left = newNode(5);
		root.left.right = newNode(20);
		root.right.left = newNode(45);
		root.right.right = newNode(70);
		root.right.right.left = newNode(65);
		root.right.right.right = newNode(80);
		System.out.println(lbst.largestBstSubTree(root));
	}
}
