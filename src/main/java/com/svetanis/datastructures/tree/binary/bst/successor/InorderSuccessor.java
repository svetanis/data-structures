package com.svetanis.datastructures.tree.binary.bst.successor;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 285. Inorder Successor in BST

public final class InorderSuccessor {
	// Time complexity: O(h), h is height of tree

	public static Node inOrderSuccessor(Node root, Node target) {
		Node successor = null;
		while (isNotNull(root)) {
			if (root.data > target.data) {
				successor = root;
				root = root.left;
			} else {
				root = root.right;
			}
		}
		return successor;
	}

	public static Node recursive(Node root, Node target) {
		return dfs(root, target, null);
	}

	private static Node dfs(Node root, Node target, Node successor) {
		if (isNull(root)) {
			return successor;
		}
		if (root.data > target.data) {
			successor = root;
			return dfs(root.left, target, successor);
		} else {
			return dfs(root.right, target, successor);
		}
	}

	public static void main(String[] args) {
		Node root = newNode(6);
		root.left = newNode(4);
		root.right = newNode(10);
		root.right.left = newNode(8);
		root.right.right = newNode(12);
		root.right.left.left = newNode(7);
		root.right.left.right = newNode(9);
		System.out.println(recursive(root, root)); // 7
		System.out.println(inOrderSuccessor(root, root)); // 7

		Node root2 = newNode(20);
		root2.left = newNode(8);
		root2.right = newNode(22);
		root2.left.left = newNode(4);
		root2.left.right = newNode(12);
		root2.left.right.left = newNode(10);
		root2.left.right.right = newNode(14);

		Node node = root2.left; // 8
		Node node2 = root2.left.right.right; // 14
		System.out.println(recursive(root2, node)); // 10
		System.out.println(inOrderSuccessor(root2, node)); // 10

		System.out.println(recursive(root2, node2)); // 20
		System.out.println(inOrderSuccessor(root2, node2)); // 20

	}
}
