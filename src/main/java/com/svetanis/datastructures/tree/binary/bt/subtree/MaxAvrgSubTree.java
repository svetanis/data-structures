package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1120. Maximum Average Subtree

public final class MaxAvrgSubTree {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	private double maxAverage;

	public double maxAvgSubtree(Node root) {
		this.maxAverage = 0.0;
		dfs(root);
		return maxAverage;
	}

	private int[] dfs(Node node) {
		if (node == null) {
			return new int[] { 0, 0 };
		}
		int[] left = dfs(node.left);
		int[] right = dfs(node.right);
		int sum = node.data + left[0] + right[0];
		int count = 1 + left[1] + right[1];
		double average = (double) sum / count;
		maxAverage = Math.max(maxAverage, average);
		return new int[] { sum, count };
	}

	public static void main(String[] args) {
		Node root = newNode(5);
		root.left = newNode(6);
		root.right = newNode(1);
		root.left.left = newNode(2);
		MaxAvrgSubTree mst = new MaxAvrgSubTree();
		System.out.println(mst.maxAvgSubtree(root)); // 4.0
	}
}
