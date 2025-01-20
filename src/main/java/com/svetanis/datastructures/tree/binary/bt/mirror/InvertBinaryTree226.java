package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.bt.traversal.lot.LotQueue.traverse;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 226. Invert Binary Tree

// given a binary tree, invert it and
// return the new value. you may invert
// it in-place. to invert a binary tree,
// switch the left subtree and the right
// subtree, and invert them both. inverting
// an empty tree does nothing.

public final class InvertBinaryTree226 {
	// Time Complexity: O(n)
	// Space Complexity: O(log n)

	public static Node invert(Node root) {
		dfs(root);
		return root;
	}

	private static void dfs(Node root) {
		if (root == null) {
			return;
		}
		// swap the left and right children
		swap(root);
		// do the subtrees
		dfs(root.left);
		dfs(root.right);
	}

	private static void swap(Node root) {
		Node temp = root.left;
		root.left = root.right;
		root.right = temp;
	}

	public static void main(String[] args) {
		Node root = newNode(4);
		root.left = newNode(2);
		root.right = newNode(7);
		root.left.left = newNode(1);
		root.left.right = newNode(3);
		root.right.left = newNode(6);
		root.right.right = newNode(9);
		System.out.println(traverse(root)); // 4 2 7 1 3 6 9
		Node inverted = invert(root);
		System.out.println(traverse(inverted)); // 4 7 2 9 6 3 1

		Node root2 = newNode(2);
		root2.left = newNode(1);
		root2.right = newNode(3);
		System.out.println(traverse(root2)); // 2 1 3
		Node inverted2 = invert(root2);
		System.out.println(traverse(inverted2)); // 2 3 1
	}
}
