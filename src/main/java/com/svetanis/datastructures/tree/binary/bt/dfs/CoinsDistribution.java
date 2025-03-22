package com.svetanis.datastructures.tree.binary.bt.dfs;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 979. Distribute Coins in Binary Tree

public final class CoinsDistribution {
	// Time Complexity: O(n)

	private int moves;

	public int distributeCoins(Node root) {
		moves = 0;
		dfs(root);
		return moves;
	}

	private int dfs(Node root) {
		if (root == null) {
			return 0;
		}
		int left = dfs(root.left);
		int right = dfs(root.right);
		moves += Math.abs(left) + Math.abs(right);
		return root.data + left + right - 1;
	}

	public static void main(String[] args) {
		Node root = new Node(3);
		root.left = new Node(0);
		root.right = new Node(0);
		CoinsDistribution cd = new CoinsDistribution();
		System.out.println(cd.distributeCoins(root)); // 2

		CoinsDistribution cd1 = new CoinsDistribution();
		Node root1 = new Node(0);
		root1.left = new Node(3);
		root1.right = new Node(0);
		System.out.println(cd1.distributeCoins(root1)); // 3
	}
}
