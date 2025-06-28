package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 938. Range Sum of BST

public final class SumInRangeRecursive {
	// Time Complexity: O(h + k)

	public static int sumInRange2(Node root, int low, int high) {
		if (isNull(root)) {
			return 0;
		}
		int sum = 0;
		if (inRange(root.data, low, high)) {
			sum += root.data;
		}
		if (low < root.data) {
			sum += sumInRange(root.left, low, high);
		}
		if (high > root.data) {
			sum += sumInRange(root.right, low, high);
		}
		return sum;
	}

	public static int sumInRange(Node root, int low, int high) {
		if (isNull(root)) {
			return 0;
		}
		if (inRange(root.data, low, high)) {
			int left = sumInRange(root.left, low, high);
			int right = sumInRange(root.right, low, high);
			return root.data + left + right;
		} else if (root.data < low) {
			return sumInRange(root.right, low, high);
		} else {
			return sumInRange(root.left, low, high);
		}
	}

	private static boolean inRange(int val, int low, int high) {
		return val <= high && val >= low;
	}

	public static void main(String[] args) {
		Node root = newNode(10);
		root.left = newNode(5);
		root.right = newNode(50);
		root.left.left = newNode(1);
		root.right.left = newNode(40);
		root.right.right = newNode(100);
		System.out.println(sumInRange(root, 5, 45));
		System.out.println(sumInRange2(root, 5, 45));
	}
}