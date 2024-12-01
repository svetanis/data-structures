package com.svetanis.datastructures.tree.binary.bt.leaves;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class AllRightLeavesSum {
	// Time Complexity: O(n)

	public static int sum(Node root) {
		if (root == null) {
			return 0;
		}
		int sum = 0;
		if (root.right != null && isLeaf(root.right)) {
			sum += root.right.data;
		}
		sum += sum(root.left);
		sum += sum(root.right);
		return sum;
	}

	private static boolean isLeaf(Node node) {
		return node.left == null && node.right == null;
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(4);
		root.left.right = new Node(5);
		root.left.left.right = new Node(2);
		root.right.right = new Node(8);
		root.right.right.left = new Node(6);
		root.right.right.right = new Node(7);
		System.out.println(sum(root));
	}
}
