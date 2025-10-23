package com.svetanis.datastructures.tree.binary.bt.special;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 965. Univalued Binary Tree

public final class UnivaluedBinaryTree {

	public static boolean isUnivalTree(Node root) {
		return dfs(root, root.data);
	}
	
	private static boolean dfs(Node root, int value) {
		if(root == null) {
			return true;
		}
		if(root.data != value) {
			return false;
		}
		boolean left = dfs(root.left, value);
		boolean right = dfs(root.right, value);
		return left && right;
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(1);
		root.right = newNode(1);
		root.left.left = newNode(1);
		root.left.right = newNode(1);
		root.right.right = newNode(1);
		System.out.println(isUnivalTree(root)); // true

		Node root1 = newNode(2);
		root1.left = newNode(2);
		root1.right = newNode(2);
		root1.left.left = newNode(5);
		root1.left.right = newNode(2);
		System.out.println(isUnivalTree(root1)); // false
	}
}
