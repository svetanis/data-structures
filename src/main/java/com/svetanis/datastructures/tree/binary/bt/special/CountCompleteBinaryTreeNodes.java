package com.svetanis.datastructures.tree.binary.bt.special;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 222. Count Complete Binary Tree Nodes
// given the root of a complete binary tree
// return the number of the nodes in the tree

public final class CountCompleteBinaryTreeNodes {

	public static int count(Node root) {
		if (isNull(root)) {
			return 0;
		}
		int left = height(root.left);
		int right = height(root.right);
		if (left == right) {
			return 1 << left + count(root.right);
		} else {
			return 1 << right + count(root.left);
		}
	}

	private static int height(Node root) {
		int height = 0;
		while (isNotNull(root)) {
			height++;
			root = root.left;
		}
		return height;
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.left = newNode(6);
		System.out.println(count(root));

		Node root2 = newNode(1);
		System.out.println(count(root2));
	}
}
