package com.svetanis.datastructures.tree.dp;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 337. House Robber III

// The houses form a binary tree. 
// If the root is robbed, 
// its left and right can not be robbed. 

public final class HouseThiefRecursive {
	// Time Complexity: O(2^n)

	public static int maxProfit(Node root) {
		if (root == null) {
			return 0;
		}
		return dfs(root);
	}

	private static int dfs(Node node) {
		if (node == null) {
			return 0;
		}
		// 1. rot this house + grandchildren
		int profit = node.data;
		if (node.left != null) {
			profit += dfs(node.left.left) + dfs(node.left.right);
		}
		if (node.right != null) {
			profit += dfs(node.right.left) + dfs(node.right.right);
		}
		// 2. don't rob this house, go to his children
		int noRobbing = dfs(node.left) + dfs(node.right);
		return Math.max(profit, noRobbing);
	}

	public static void main(String[] args) {
		Node root1 = newNode(3);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.right = newNode(3);
		root1.right.right = newNode(1);
		System.out.println(maxProfit(root1)); // 7

		Node root2 = newNode(3);
		root2.left = newNode(4);
		root2.right = newNode(5);
		root2.left.left = newNode(1);
		root2.left.right = newNode(3);
		root2.right = newNode(5);
		root2.right.right = newNode(1);
		System.out.println(maxProfit(root2)); // 9
	}
}
