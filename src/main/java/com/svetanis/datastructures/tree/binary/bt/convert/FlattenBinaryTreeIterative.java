package com.svetanis.datastructures.tree.binary.bt.convert;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 114. Flatten Binary Tree to Linked List

public final class FlattenBinaryTreeIterative {
	// Time Complexity: O(n)
	// Space Complexity: O(log n)

	public static void flatten(Node root) {
		while (root != null) {
			if (root.left != null) {
				Node rightmost = root.left;
				while (rightmost.right != null) {
					rightmost = rightmost.right;
				}
				rightmost.right = root.right;
				root.right = root.left;
				root.left = null;
			}
			root = root.right;
		}
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(3);
		root.left.right = newNode(4);
		root.right.right = newNode(6);
		flatten(root); // 1 2 3 4 5 6
		Nodes.preOrder(root);
		System.out.println();
		Node root1 = newNode(0);
		flatten(root1); // 0
		Nodes.preOrder(root1);
		System.out.println();
	}
}
