package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static java.lang.Math.max;

import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, find the length of its diameter. 
// The diameter of a tree is the number of nodes on 
// the longest path between any two leaf nodes. 
// The diameter of a tree may or may not pass through the root.

public final class Diameter {
	// Time complexity: O(n)

	public static int diameter(Node root) {
		AtomicInteger diameter = new AtomicInteger();
		height(root, diameter);
		return diameter.get();
	}

	private static int height(Node root, AtomicInteger diameter) {
		if (root == null) {
			return 0;
		}

		int left = height(root.left, diameter);
		int right = height(root.right, diameter);

		int d = 1 + left + right;
		int max = max(diameter.get(), d);
		diameter.set(max);
		return 1 + max(left, right);
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.right.left = newNode(5);
		root.right.right = newNode(6);
		System.out.println(diameter(root));

		root.left.left = null;
		root.right.left.left = newNode(7);
		root.right.left.right = newNode(8);
		root.right.right.left = newNode(9);
		root.right.left.right.left = newNode(10);
		root.right.right.left.left = newNode(11);
		System.out.println(diameter(root));
	}
}
