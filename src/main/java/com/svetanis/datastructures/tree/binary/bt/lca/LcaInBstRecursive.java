package com.svetanis.datastructures.tree.binary.bt.lca;

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

public final class LcaInBstRecursive {
	// Time complexity: O(log n) for balanced BST,
	// O(n) for skewed BST

	public static Node lca(Node root, Node p, Node q) {
		if (root == null || p == null || q == null) {
			return null;
		}
		if (max(p.data, q.data) < root.data) {
			return lca(root.left, p, q);
		} else if (min(p.data, q.data) > root.data) {
			return lca(root.right, p, q);
		} else {
			return root;
		}
	}

	public static Node lca(Node root, int p, int q) {
		if (root == null) {
			return null;
		}
		if (max(p, q) < root.data) {
			return lca(root.left, p, q);
		} else if (min(p, q) > root.data) {
			return lca(root.right, p, q);
		} else {
			return root;
		}
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