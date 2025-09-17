package com.svetanis.datastructures.tree.binary.bt.construction;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 998. Maximum Binary Tree II

public final class MaxBinaryTreeII {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static Node insert(Node root, int val) {
		if (root == null || root.data < val) {
			Node newRoot = new Node(val);
			newRoot.left = root;
			return newRoot;
		}
		root.right = insert(root.right, val);
		return root;
	}

	public static void main(String[] args) {
		Node root = new Node(4);
		root.left = new Node(1);
		root.right = new Node(3);
		root.right.left = new Node(2);
		Nodes.preOrder(insert(root, 5));
		System.out.println();

		Node root1 = new Node(5);
		root1.left = new Node(2);
		root1.right = new Node(4);
		root1.left.right = new Node(1);
		Nodes.preOrder(insert(root1, 3));
		System.out.println();

		Node root2 = new Node(5);
		root2.left = new Node(2);
		root2.right = new Node(3);
		root2.left.right = new Node(1);
		Nodes.preOrder(insert(root2, 4));
	}
}
