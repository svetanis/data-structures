package com.svetanis.datastructures.tree.binary.bt.dimention;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 543. Diameter of Binary Tree

// Given a binary tree, find the length of its diameter. 
// The diameter of a tree is the number of edges on 
// the longest path between any two leaf nodes. 
// The diameter of a tree may or may not pass through the root.

public final class DiameterSubmit {
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
		int d = left + right;
		int max = Math.max(diameter.get(), d);
		diameter.set(max);
		return 1 + Math.max(left, right);
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		System.out.println(diameter(root)); // 3
	}
}
