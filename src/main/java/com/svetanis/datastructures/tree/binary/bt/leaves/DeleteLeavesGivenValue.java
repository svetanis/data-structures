package com.svetanis.datastructures.tree.binary.bt.leaves;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 1325. Delete Leaves With a Given Value

public final class DeleteLeavesGivenValue {
	// Time Complexity: O(n)
	// Space Complexity: O(log n)

	public static Node removeLeaves(Node root, int target) {
		if (root == null) {
			return null;
		}
		root.left = removeLeaves(root.left, target);
		root.right = removeLeaves(root.right, target);
		if (root.left == null && root.right == null && root.data == target) {
			return null;
		}
		return root;
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(2);
		root.right.left = new Node(2);
		root.right.right = new Node(4);
		Nodes.preOrder(removeLeaves(root, 2));

		Node root1 = new Node(1);
		root1.left = new Node(3);
		root1.right = new Node(3);
		root1.left.left = new Node(3);
		root1.left.right = new Node(2);
		Nodes.preOrder(removeLeaves(root1, 3));
	}
}
