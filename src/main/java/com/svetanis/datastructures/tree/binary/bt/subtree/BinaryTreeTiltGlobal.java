package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 563. Binary Tree Tilt

public final class BinaryTreeTiltGlobal {

	private int sum;

	public int tilt(Node root) {
		this.sum = 0;
		dfs(root);
		return sum;
	}

	private int dfs(Node root) {
		if (root == null) {
			return 0;
		}
		int left = dfs(root.left);
		int right = dfs(root.right);
		sum += Math.abs(left - right);
		return root.data + left + right;
	}

	public static void main(String[] args) {
		BinaryTreeTiltGlobal btt = new BinaryTreeTiltGlobal();
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		System.out.println(btt.tilt(root)); // 1

		Node root1 = newNode(4);
		root1.left = newNode(2);
		root1.right = newNode(9);
		root1.left.left = newNode(3);
		root1.left.right = newNode(5);
		root1.right.right = newNode(7);
		System.out.println(btt.tilt(root1)); // 15
	}
}
