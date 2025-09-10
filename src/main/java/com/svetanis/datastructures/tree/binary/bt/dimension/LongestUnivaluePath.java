package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 687. Longest Univalue Path

public final class LongestUnivaluePath {
	// Time complexity: O(n)

	private int maxLen;

	public int longestUnivaluePath(Node root) {
		this.maxLen = 0;
		dfs(root);
		return maxLen;
	}

	private int dfs(Node root) {
		if (root == null) {
			return 0;
		}
		int left = dfs(root.left);
		int right = dfs(root.right);
		boolean lup = root.left != null && root.left.data == root.data;
		left = lup ? left + 1 : 0;
		boolean rup = root.right != null && root.right.data == root.data;
		right = rup ? right + 1 : 0;
		int pathThroughNode = left + right;
		maxLen = Math.max(maxLen, pathThroughNode);
		return Math.max(left, right);
	}

	public static void main(String[] args) {
		LongestUnivaluePath dop = new LongestUnivaluePath();
		Node root = newNode(5);
		root.left = newNode(4);
		root.right = newNode(5);
		root.left.left = newNode(1);
		root.left.right = newNode(1);
		root.right.right = newNode(5);
		System.out.println(dop.longestUnivaluePath(root)); // 2

		Node root2 = newNode(1);
		root2.left = newNode(4);
		root2.right = newNode(5);
		root2.left.left = newNode(4);
		root2.left.right = newNode(4);
		root2.right.right = newNode(5);
		System.out.println(dop.longestUnivaluePath(root2)); // 2
	}
}
