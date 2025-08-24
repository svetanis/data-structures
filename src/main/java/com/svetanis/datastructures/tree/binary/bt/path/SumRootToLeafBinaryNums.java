package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1022. Sum of Root To Leaf Binary Numbers 

public final class SumRootToLeafBinaryNums {
	// Time Complexity: O(n)
	// Space Complexity: O(log n)

	public static int sum(Node root) {
		return sum(root, 0);
	}

	private static int sum(Node root, int sum) {
		if (root == null) {
			return 0;
		}
		sum = (sum << 1) | root.data;
		if (root.left == null && root.right == null) {
			return sum;
		}
		int left = sum(root.left, sum);
		int right = sum(root.right, sum);
		return left + right;
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(0);
		root.right = newNode(1);
		root.left.left = newNode(0);
		root.left.right = newNode(1);
		root.right.left = newNode(0);
		root.right.right = newNode(1);
		System.out.println(sum(root)); // 22

		Node root2 = newNode(0);
		System.out.println(sum(root2)); // 0
	}
}
