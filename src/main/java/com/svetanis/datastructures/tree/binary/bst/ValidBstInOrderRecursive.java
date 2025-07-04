package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 98. Validate Binary Search Tree

// given a Binary Tree (BT)
// determine if it is a BST or not

public final class ValidBstInOrderRecursive {
	// Time complexity O(n);
	// Space complexity: O(h)

	private Integer prev;

	public boolean isValidBst(Node root) {
		return inOrder(root);
	}

	private boolean inOrder(Node root) {
		// an empty tree is a BST
		if (root == null) {
			return true;
		}
		// check if left subtree is BST or not
		if (!inOrder(root.left)) {
			return false;
		}
		// value of current node should be
		// more than that of prev node
		if (prev != null && prev >= root.data) {
			return false;
		}
		// update the prev node
		prev = root.data;
		// check if right subtree is BST or not
		return inOrder(root.right);
	}

	public static void main(String[] args) {
		Node root = newNode(2);
		root.left = newNode(1);
		root.right = newNode(3);
		ValidBstInOrderRecursive vb1 = new ValidBstInOrderRecursive();
		System.out.println(vb1.isValidBst(root)); // true

		Node root2 = newNode(5);
		root2.left = newNode(1);
		root2.right = newNode(4);
		root2.right.left = newNode(3);
		root2.right.right = newNode(6);
		ValidBstInOrderRecursive vb2 = new ValidBstInOrderRecursive();
		System.out.println(vb2.isValidBst(root2)); // false

		Node root3 = new Node(0);
		ValidBstInOrderRecursive vb3 = new ValidBstInOrderRecursive();
		System.out.println(vb3.isValidBst(root3)); // true

		Node root4 = new Node(1);
		root4.left = new Node(1);
		ValidBstInOrderRecursive vb4 = new ValidBstInOrderRecursive();
		System.out.println(vb4.isValidBst(root4)); // false
	}
}
