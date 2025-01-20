package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 101. Symmetric Tree

// Mirror of itself (symmetric around its center)

public final class SymmetricBinaryTree {
	// Time Complexity: O(n)
	// Space Complexity: O(log n)

	public static boolean symmetric(Node root) {
		if (root == null) {
			return true;
		}
		return mirror(root.left, root.right);
	}

	private static boolean mirror(Node root1, Node root2) {
		if (root1 == null && root2 == null) {
			return true;
		}
		if (root1 == null || root2 == null) {
			return false;
		}
		if (root1.data != root2.data) {
			return false;
		}
		boolean left = mirror(root1.left, root2.right);
		boolean right = mirror(root1.right, root2.left);
		return left && right;
	}

	public static void main(String[] args) {
		Node root = newNode(3);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(1);
		root.right.left = newNode(4);
		root.right.right = newNode(6);
		System.out.println(symmetric(root));

		Node root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(2);
		root1.left.left = newNode(3);
		root1.left.right = newNode(4);
		root1.right.left = newNode(4);
		root1.right.right = newNode(3);
		System.out.println(symmetric(root1));
	}
}
