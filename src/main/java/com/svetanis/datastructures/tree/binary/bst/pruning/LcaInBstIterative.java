package com.svetanis.datastructures.tree.binary.bst.pruning;

import static java.lang.Math.max;
import static java.lang.Math.min;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 235. Lowest Common Ancestor of a Binary Search Tree

// given a BST, find the LCA node 
// of two given nodes in BST.

// the LCA is defined between two nodes p and q
// as the lowest node in T that has both p and q
// as descendants (where we allow a node to be a
// descendant of itself)

public final class LcaInBstIterative {
	// Time complexity: O(log n) for balanced BST,
	// O(n) for skewed BST

	public static Node lcaSimple(Node root, Node p, Node q) {
		while (root != null) {
			if (root.data < Math.min(p.data, q.data)) {
				root = root.right;
			} else if (root.data > Math.max(p.data, q.data)) {
				root = root.left;
			} else {
				return root;
			}
		}
		return null;
	}

	public static Node lca(Node root, Node p, Node q) {
		return lca(root, p.data, q.data);
	}

	public static Node lca(Node root, int p, int q) {
		// LC 235 does not say which of the two comes first, and comparing
		// against p and q in the order they arrive walks the wrong way
		// for every call where p > q -- off the tree, and into an NPE.
		// lcaSimple above already took the min and the max
		int lo = min(p, q);
		int hi = max(p, q);
		while (root != null && (root.data < lo || root.data > hi)) {
			// LCA must be in root's right child
			if (root.data < lo) {
				root = root.right;
			} else {
				// LCA must be in root's left child
				root = root.left;
			}
		}
		// root.data >= lo && root.data <= hi
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

		// the same two nodes handed over in the other order. comparing
		// against p and q as given threw here
		System.out.println(lca(root, 8, 2)); // 6
		System.out.println(lca(root, 4, 2)); // 2
	}
}