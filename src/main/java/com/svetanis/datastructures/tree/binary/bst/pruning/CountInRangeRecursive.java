package com.svetanis.datastructures.tree.binary.bst.pruning;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and a range
// count number of nodes
// that lie in the given range

public final class CountInRangeRecursive {

	public static int countInRange(Node root, int low, int high) {
		// Time Complexity: O(h + k)

		if (isNull(root)) {
			return 0;
		}
		if (inRange(root.data, low, high)) {
			int left = countInRange(root.left, low, high);
			int right = countInRange(root.right, low, high);
			return 1 + left + right;
		} else if (root.data < low) {
			return countInRange(root.right, low, high);
		} else {
			return countInRange(root.left, low, high);
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
		System.out.println(countInRange(root, 5, 45));
	}
}