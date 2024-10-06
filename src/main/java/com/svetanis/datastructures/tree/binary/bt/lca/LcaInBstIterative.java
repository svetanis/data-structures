package com.svetanis.datastructures.tree.binary.bt.lca;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST, find the LCA node 
// of two given nodes in BST.

// the LCA is defined between two nodes p and q
// as the lowest node in T that has both p and q
// as descendants (where we allow a node to be a
// descendant of itself)

public final class LcaInBstIterative {
	// Time complexity: O(log n) for balanced BST,
	// O(n) for skewed BST

	public static Node lca(Node root, Node p, Node q) {
		while (root.data < p.data || root.data > q.data) {
			// LCA must be in root's right child
			if (root.data < p.data) {
				root = root.right;
			}
			// LCA must be in root's left child
			if (root.data > q.data) {
				root = root.left;
			}
		}
		// root.data >= p.data && root.data <= q.data
		return root; // root is LCA
	}

	public static Node lca(Node root, int p, int q) {
		while (root.data < p || root.data > q) {
			// LCA must be in root's right child
			if (root.data < p) {
				root = root.right;
			}
			// LCA must be in root's left child
			if (root.data > q) {
				root = root.left;
			}
		}
		// root.data >= p.data && root.data <= q.data
		return root; // root is LCA
	}

	public static void main(String[] args) {
		Node root = new Node(6);
		root.left = new Node(2);
		root.right = new Node(8);
		root.left.left = new Node(0);
		root.left.right = new Node(4);
		root.left.right.left = new Node(3);
		root.left.right.right = new Node(5);
		root.right.left = new Node(7);
		root.right.right = new Node(9);
		System.out.println(lca(root, 2, 8)); // 6
		System.out.println(lca(root, 2, 4)); // 2
	}
}