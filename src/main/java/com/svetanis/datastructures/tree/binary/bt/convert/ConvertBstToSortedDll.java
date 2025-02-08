package com.svetanis.datastructures.tree.binary.bt.convert;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 426. Convert Binary Search Tree to Sorted Doubly Linked List

public final class ConvertBstToSortedDll {
	// Time complexity: O(n)

	private Node prev;
	private Node head;

	public Node bstToDll(Node root) {
		if (root == null) {
			return null;
		}
		prev = null;
		head = null;
		inOrder(root);
		prev.right = head;
		head.left = prev;
		return head;
	}

	private void inOrder(Node root) {
		if (root == null) {
			return;
		}
		inOrder(root.left);
		if (prev != null) {
			prev.right = root;
			root.left = prev;
		} else {
			head = root;
		}
		prev = root;
		inOrder(root.right);
	}

	public static void main(String[] args) {
		Node root = new Node(4);
		root.left = new Node(2);
		root.right = new Node(5);
		root.left.left = new Node(1);
		root.left.right = new Node(3);
		ConvertBstToSortedDll bts = new ConvertBstToSortedDll();
		bts.bstToDll(root);
	}
}
