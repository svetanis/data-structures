package com.svetanis.datastructures.tree.binary.bt.dfs;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1448. Count Good Nodes in Binary Tree

public final class CountGoodNodes1448 {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int count(Node root) {
		return dfs(root, Integer.MIN_VALUE);
	}

	private static int dfs(Node root, int max) {
		if (root == null) {
			return 0;
		}
		int count = 0;
		if (max <= root.data) {
			count++;
			max = root.data;
		}
		count += dfs(root.left, max);
		count += dfs(root.right, max);
		return count;
	}

	public static void main(String[] args) {
		Node root = newNode(3);
		root.left = newNode(1);
		root.right = newNode(4);
		root.left.left = newNode(3);
		root.right.left = newNode(1);
		root.right.right = newNode(5);
		System.out.println(count(root)); // 4

		Node root1 = newNode(3);
		root1.left = newNode(3);
		root1.left.left = newNode(4);
		root1.left.right = newNode(2);
		System.out.println(count(root1)); // 3

		Node root2 = newNode(1);
		System.out.println(count(root2)); // 1
	}
}
