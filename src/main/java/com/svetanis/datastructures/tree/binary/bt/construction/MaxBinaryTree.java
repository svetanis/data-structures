package com.svetanis.datastructures.tree.binary.bt.construction;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 654. Maximum Binary Tree

public final class MaxBinaryTree {
	// Time Complexity: O(n^2)
	// Space Complexity: O(n)

	public static Node maxBinaryTree(int[] a) {
		return dfs(a, 0, a.length - 1);
	}

	private static Node dfs(int[] a, int left, int right) {
		if (left > right) {
			return null;
		}
		int max = left;
		for (int index = left; index <= right; index++) {
			if (a[max] < a[index]) {
				max = index;
			}
		}
		Node node = new Node(a[max]);
		node.left = dfs(a, left, max - 1);
		node.right = dfs(a, max + 1, right);
		return node;
	}

	public static void main(String[] args) {
		int[] a = { 3, 2, 1, 6, 0, 5 };
		Nodes.preOrder(maxBinaryTree(a)); 
		System.out.println();
	}
}
