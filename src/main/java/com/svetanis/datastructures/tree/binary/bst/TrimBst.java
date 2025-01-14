package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 669. Trim a Binary Search Tree

public final class TrimBst {
	// Time Complexity: O(n)
	// Space Complexity: O(n)
	
	public static Node trim(Node root, int low, int high) {
		if (root == null) {
			return null;
		}
		if (root.data > high) {
			return trim(root.left, low, high);
		}
		if (root.data < low) {
			return trim(root.right, low, high);
		}
		root.left = trim(root.left, low, high);
		root.right = trim(root.right, low, high);
		return root;
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(0);
		root.right = newNode(2);
		Nodes.preOrder(trim(root, 1, 2));
		System.out.println();
		
		Node root2 = newNode(3);
		root2.left = newNode(0);
		root2.right = newNode(4);
		root2.left.right = newNode(2);
		root2.left.right.left = newNode(1);
		Nodes.preOrder(trim(root2, 1, 3));
		System.out.println();
	}
}
