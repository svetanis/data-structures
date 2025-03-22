package com.svetanis.datastructures.tree.binary.bt.dfs;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1026. Maximum Difference Between Node and Ancestor

public final class MaxAncestorDiffSimple {

	public int maxAncestorDiff(Node root) {
		if (root == null) {
			return 0;
		}
		return dfs(root, root.data, root.data);
	}

	private int dfs(Node root, int min, int max) {
		if (root == null) {
			return max - min;
		}
		min = Math.min(min, root.data);
		max = Math.max(max, root.data);
		int left = dfs(root.left, min, max);
		int right = dfs(root.right, min, max);
		return Math.max(left, right);
	}

	public static void main(String[] args) {
		Node root = new Node(8);
		root.left = new Node(3);
		root.right = new Node(10);
		root.left.left = new Node(1);
		root.left.right = new Node(6);
		root.left.right.left = new Node(4);
		root.left.right.right = new Node(7);
		root.right.right = new Node(14);
		root.right.right.left = new Node(13);
		MaxAncestorDiffSimple mad = new MaxAncestorDiffSimple();
		System.out.println(mad.maxAncestorDiff(root)); // 7

		Node root1 = new Node(1);
		root1.right = new Node(2);
		root1.right.right = new Node(0);
		root1.right.right.left = new Node(3);
		MaxAncestorDiffSimple mad1 = new MaxAncestorDiffSimple();
		System.out.println(mad1.maxAncestorDiff(root1)); // 3
	}
}
