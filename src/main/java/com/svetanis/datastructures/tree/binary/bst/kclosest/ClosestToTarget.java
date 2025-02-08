package com.svetanis.datastructures.tree.binary.bst.kclosest;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 270. Closest Binary Search Tree Value

// Given BST and a target value. 
// Find the value that is closest to the target.

public final class ClosestToTarget {
	// Time Complexity: O(log n)

	public static int closestToTarget(Node root, double target) {
		int closest = root.data;
		double minDiff = Double.MAX_VALUE;
		while (root != null) {
			double currDiff = Math.abs(target - root.data);
			boolean equals = currDiff == minDiff && root.data < closest;
			if (currDiff < minDiff || equals) {
				minDiff = currDiff;
				closest = root.data;
			}
			if (root.data > target) {
				root = root.left;
			} else {
				root = root.right;
			}
		}
		return closest;
	}

	public static void main(String[] args) {
		Node root = newNode(4);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(1);
		root.left.right = newNode(3);
		System.out.println(closestToTarget(root, 4.5)); // 4
	}
}
