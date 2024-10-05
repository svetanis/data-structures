package com.svetanis.datastructures.tree.binary.bt.special;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Math.abs;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a BT, write an efficient algorithm to check 
// if tree is balanced or not. In balanced tree, the
// abs diff between height of left and right subtree
// for every node is 0 or 1.

public final class IsBalancedBinaryTree {
	// Time Complexity: O(n)
	// Space Complexity: O(h)

	public static boolean balanced(Node root) {
		return height(root) != -1;
	}

	private static int height(Node root) {
		if (isNull(root)) {
			return 0;
		}
		int left = height(root.left);
		int right = height(root.right);
		if (left == -1 || right == -1) {
			return -1;
		}
		if (abs(left - right) > 1) {
			return -1;
		}
		return 1 + max(left, right);
	}

	public static void main(String[] args) {
		Node root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.left = newNode(4);
		root1.left.right = newNode(5);
		root1.right.left = newNode(6);
		root1.left.left.left = newNode(7);
		System.out.println(balanced(root1));
	}
}
