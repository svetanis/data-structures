package com.svetanis.datastructures.tree.binary.bt.leaves;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 404. Sum of Left Leaves

public final class AllLeftLeavesSum {
	// Time Complexity: O(n)

	public static int sum(Node root) {
		if (root == null) {
			return 0;
		}
		int sum = 0;
		if (root.left != null && isLeaf(root.left)) {
			sum += root.left.data;
		}
		sum += sum(root.left);
		sum += sum(root.right);
		return sum;
	}

	private static boolean isLeaf(Node node) {
		return node.left == null && node.right == null;
	}

	public static void main(String[] args) {
		Node root = new Node(20);
		root.left = new Node(9);
		root.right = new Node(49);
		root.right.left = new Node(23);
		root.right.right = new Node(52);
		root.right.right.left = new Node(50);
		root.left.left = new Node(5);
		root.left.right = new Node(12);
		root.left.right.right = new Node(12);
		System.out.println(sum(root));
	}
}
