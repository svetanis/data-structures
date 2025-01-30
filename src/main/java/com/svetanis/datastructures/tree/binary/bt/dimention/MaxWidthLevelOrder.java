package com.svetanis.datastructures.tree.binary.bt.dimention;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.height;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, write a function to get the maximum width of the given tree. 
// Width of a tree is maximum of widths of all levels. 

// excluding null nodes between the end nodes

public final class MaxWidthLevelOrder {

	public static int maxWidth(Node root) {
		int max = 0;
		int height = height(root);

		// get width of each level and compare
		// the width with max width so far
		for (int i = 1; i <= height; i++) {
			int width = width(root, i);
			max = max(max, width);
		}
		return max;
	}

	private static int width(Node root, int level) {
		if (isNull(root)) {
			return 0;
		}
		if (level == 1) {
			return 1;
		} else {
			int left = width(root.left, level - 1);
			int right = width(root.right, level - 1);
			return left + right;
		}
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.right = newNode(8);
		root.right.right.left = newNode(6);
		root.right.right.right = newNode(7);
		System.out.println(maxWidth(root));
	}
}
