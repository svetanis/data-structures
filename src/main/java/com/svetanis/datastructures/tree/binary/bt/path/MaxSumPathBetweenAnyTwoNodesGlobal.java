package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 124. Binary Tree Maximum Path Sum

public final class MaxSumPathBetweenAnyTwoNodesGlobal {
	// Time Complexity: O(n)

	private int max;

	public int maxSum(Node root) {
		this.max = Integer.MIN_VALUE;
		dfs(root);
		return max;
	}

	// this function calculates two values:
	// 1. max path sum between two nodes which is stored in max
	// 2. the max root-to-leaf path sum which is returned
	private int dfs(Node root) {
		if (root == null) {
			return 0;
		}
		// max root-to-leaf sum in left and right subtree
		// ignore paths with negative sums
		int left = Math.max(0, dfs(root.left));
		int right = Math.max(0, dfs(root.right));

		// max sum path passing through current node
		int sum = root.data + left + right;
		// max sum path found so far
		max = (Math.max(max, sum));
		// max sum node-to-leaf path starting from current node
		return root.data + Math.max(left, right);
	}

	public static void main(String[] args) {
		// Max sum is 27 = 3 + 6 + 9 + 0 - 1 + 10
		Node root = newNode(-15);
		root.left = newNode(5);
		root.right = newNode(6);
		root.left.left = newNode(-8);
		root.left.right = newNode(1);
		root.right.left = newNode(3);
		root.right.right = newNode(9);
		root.left.left.left = newNode(2);
		root.left.left.right = newNode(6);
		root.right.right.right = newNode(0);
		root.right.right.right.left = newNode(4);
		root.right.right.right.right = newNode(-1);
		root.right.right.right.right.left = newNode(10);

		MaxSumPathBetweenAnyTwoNodesGlobal msp = new MaxSumPathBetweenAnyTwoNodesGlobal();
		System.out.println(msp.maxSum(root));

		// Max sum is 42 = 20 + 2 + 10 + 10
		Node root2 = newNode(10);
		root2.left = newNode(2);
		root2.right = newNode(10);
		root2.left.left = newNode(20);
		root2.left.right = newNode(1);
		root2.right.right = newNode(-25);
		root2.right.right.left = newNode(3);
		root2.right.right.right = newNode(4);
		MaxSumPathBetweenAnyTwoNodesGlobal msp2 = new MaxSumPathBetweenAnyTwoNodesGlobal();
		System.out.println(msp2.maxSum(root2));
	}
}
