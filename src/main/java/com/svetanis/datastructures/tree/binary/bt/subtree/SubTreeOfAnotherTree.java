package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 572. Subtree of Another Tree

// given two binary trees root and subRoot,
// find if subRoot is a subtree of root.
// a subtree of a binary tree is a tree that
// consists of a node in the tree and all of
// its descendants.

public final class SubTreeOfAnotherTree {
	// Time Complexity: O(n * m)

	public static boolean isSubTree(Node root, Node subRoot) {
		if (isNull(root)) {
			return false;
		}
		// empty tree is always a subtree
		if (isNull(subRoot)) {
			return true;
		}
		if (isIdentical(root, subRoot)) {
			return true;
		}
		boolean left = isSubTree(root.left, subRoot);
		boolean right = isSubTree(root.right, subRoot);
		return left || right;
	}

	private static boolean isIdentical(Node root1, Node root2) {
		if (isNull(root1) && isNull(root2)) {
			return true;
		}
		if (isNull(root1) || isNull(root2)) {
			return false;
		}
		if (root1.data != root2.data) {
			return false;
		}
		boolean left = isIdentical(root1.left, root2.left);
		boolean right = isIdentical(root1.right, root2.right);
		return left && right;
	}

	public static void main(String[] args) {
		Node root1 = newNode(3);
		root1.left = newNode(4);
		root1.right = newNode(5);
		root1.left.left = newNode(1);
		root1.left.right = newNode(2);
		root1.right = newNode(5);

		Node root2 = newNode(4);
		root2.left = newNode(1);
		root2.right = newNode(2);

		System.out.println(isSubTree(root1, root2));
	}
}
