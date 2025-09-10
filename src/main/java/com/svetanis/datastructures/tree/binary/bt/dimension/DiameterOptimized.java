package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 543. Diameter of Binary Tree

// Given a binary tree, find the length of its diameter. 
// The diameter of a tree is the number of edges on 
// the longest path between any two leaf nodes. 
// The diameter of a tree may or may not pass through the root.

public final class DiameterOptimized {
	// Time complexity: O(n)

	private int maxDiameter;

	public int diameter(Node root) {
		this.maxDiameter = 0;
		dfs(root);
		return maxDiameter;
	}

	private int dfs(Node root) {
		if (root == null) {
			return 0;
		}
		int left = dfs(root.left);
		int right = dfs(root.right);
		int diameterThroughNode = left + right;
		maxDiameter = Math.max(maxDiameter, diameterThroughNode);
		return 1 + Math.max(left, right);
	}

	public static void main(String[] args) {
		DiameterOptimized dop = new DiameterOptimized();
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		System.out.println(dop.diameter(root)); // 3
	}
}
