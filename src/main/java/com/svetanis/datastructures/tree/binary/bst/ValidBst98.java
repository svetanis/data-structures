package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 98. Validate Binary Search Tree

// given a Binary Tree (BT)
// determine if it is a BST or not

public final class ValidBst98 {
	// Time complexity O(n);
	// Space complexity: O(h)

	private Integer prev;

	public boolean isValidBst(Node root) {
		Integer prev = null;
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
		if (!inOrder(root.right)) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		Node root = newNode(2);
		root.left = newNode(1);
		root.right = newNode(3);
		ValidBst98 vb1 = new ValidBst98();
		System.out.println(vb1.isValidBst(root)); // true

		Node root2 = newNode(5);
		root2.left = newNode(1);
		root2.right = newNode(4);
		root2.right.left = newNode(3);
		root2.right.right = newNode(6);
		ValidBst98 vb2 = new ValidBst98();
		System.out.println(vb2.isValidBst(root2)); // false

		Node root3 = new Node(0);
		ValidBst98 vb3 = new ValidBst98();
		System.out.println(vb3.isValidBst(root3)); // true

		Node root4 = new Node(1);
		root4.left = new Node(1);
		ValidBst98 vb4 = new ValidBst98();
		System.out.println(vb4.isValidBst(root4)); // false
	}
}
