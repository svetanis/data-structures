package com.svetanis.datastructures.tree.dp;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 968. Binary Tree Cameras

// state 0 - node is not covered
// state 1 - node monitored by camera at one of its children
// state 2 - node has camera

public final class BinaryTreeCamerasGreedy {
	// Time Complexity: O(n)

	private int count = 0;

	public int btc(Node root) {
		if (dfs(root) == 0) {
			count++;
		}
		return count;
	}

	private int dfs(Node node) {
		if (node == null) {
			return 1;
		}
		int left = dfs(node.left);
		int right = dfs(node.right);
		// if any child not covered, place camera
		if (left == 0 || right == 0) {
			count++;
			return 2;
		}
		// if any child has camera, this node covered
		if (left == 2 || right == 2) {
			return 1;
		}
		// if both children covered, but don't have camera
		// this node is not covered
		return 0;
	}

	public static void main(String[] args) {
		Node root1 = newNode(0);
		root1.left = newNode(0);
		root1.left.left = newNode(0);
		root1.left.right = newNode(0);
		BinaryTreeCamerasGreedy btc = new BinaryTreeCamerasGreedy();
		System.out.println(btc.btc(root1)); // 1

		Node root2 = newNode(0);
		root2.left = newNode(0);
		root2.left.left = newNode(0);
		root2.left.left.left = newNode(0);
		root2.left.left.left.right = newNode(0);
		BinaryTreeCamerasGreedy btc2 = new BinaryTreeCamerasGreedy();
		System.out.println(btc2.btc(root2)); // 2

		Node root3 = newNode(0);
		BinaryTreeCamerasGreedy btc3 = new BinaryTreeCamerasGreedy();
		System.out.println(btc3.btc(root3)); // 1
	}
}
