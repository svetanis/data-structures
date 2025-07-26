package com.svetanis.datastructures.tree.dp;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 337. House Robber III

// The houses form a binary tree. 
// If the root is robbed, 
// its left and right can not be robbed. 

public final class HouseThiefSimple {
	// Time Complexity: O(n)

	public static int maxProfit(Node root) {
		if (root == null) {
			return 0;
		}
		int[] profit = dfs(root);
		return max(profit[0], profit[1]);
	}

	// [0] - max profit if node is robbed
	// [1] - max profit if node is not robbed
	private static int[] dfs(Node node) {
		if (node == null) {
			return new int[] {0, 0};
		}
		int[] left = dfs(node.left);
		int[] right = dfs(node.right);
		int[] profit = new int[2];
		profit[0] = node.data + left[1] + right[1];
		profit[1] = max(left[0], left[1]) + max(right[0], right[1]);
		return profit;
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
