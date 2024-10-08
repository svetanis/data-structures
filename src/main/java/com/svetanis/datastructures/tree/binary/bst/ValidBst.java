package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a Binary Tree (BT)
// determine if it is a BST or not

// conditions to check:
// 1. if the node is the left child
// then it must be smaller than the parent
// and must pass down the value from its
// parent to its right subtree to make sure
// none of the nodes in that subtree is
// greater than the parent

// 2. if the node is the right child 
// of its parent, then it must be larger
// than the parent and it must pass down
// the value from its parent to its left
// subtree to make sure none of the nodes
// in that subtree is lesser than then the parent

public final class ValidBst {

	public static boolean isValidBst(Node root) {
		return isValidBst(root, MIN_VALUE, MAX_VALUE);
	}

	private static boolean isValidBst(Node root, int min, int max) {
		// Time complexity O(n);
		// Space complexity: O(h)

		// an empty tree is a BST
		if (isNull(root)) {
			return true;
		}

		// false if this node violates
		// the min/max constraint
		if (root.data < min || root.data > max) {
			return false;
		}

		// otherwise check the subtrees recursively,
		// tightening the min or max constraint
		boolean left = isValidBst(root.left, min, root.data);
		boolean right = isValidBst(root.right, root.data, max);
		return left && right;
	}

	public static void main(String[] args) {
		Node root = newNode(4);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(1);
		root.left.right = newNode(3);
		System.out.println(isValidBst(root));

		Node root2 = newNode(1);
		root2.left = newNode(2);
		root2.right = newNode(3);
		root2.left.left = newNode(4);
		root2.left.right = newNode(5);
		root2.right.right = newNode(6);
		System.out.println(isValidBst(root2));
	}
}
