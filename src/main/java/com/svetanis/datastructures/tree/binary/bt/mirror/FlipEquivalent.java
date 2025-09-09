package com.svetanis.datastructures.tree.binary.bt.mirror;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 951. Flip Equivalent Binary Trees

public final class FlipEquivalent {
	// Time Complexity: O(n)
	// Space Complexity: O(log n)

	public static boolean flipEquivalent(Node root1, Node root2) {
		if (isNull(root1) && isNull(root2)) {
			return true;
		}
		if (isNull(root1) || isNull(root2)) {
			return false;
		}
		if (root1.data != root2.data) {
			return false;
		}
		boolean leftLeft = flipEquivalent(root1.left, root2.left);
		boolean rightRight = flipEquivalent(root1.right, root2.right);
		boolean leftRight = flipEquivalent(root1.left, root2.right);
		boolean rightLeft = flipEquivalent(root1.right, root2.left);
		return (leftLeft && rightRight) || (leftRight && rightLeft);
	}

	public static void main(String[] args) {

		Node root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.left = newNode(4);
		root1.left.right = newNode(5);
		inOrder(root1);
		System.out.println();

		Node root2 = newNode(1);
		root2.left = newNode(3);
		root2.right = newNode(2);
		root2.right.left = newNode(5);
		root2.right.right = newNode(4);
		inOrder(root2);
		System.out.println();

		System.out.println(flipEquivalent(root1, root2));
	}
}
