package com.svetanis.datastructures.tree.binary.bt.lca;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isAbsent;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.google.common.base.Optional;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// find the Lowest Common Ancestor (LCA) 
// of the two given nodes in the given BT

// without pointer to parent; Bottom Up;
// No guarantee that p or q exist in the tree.
// If one value doesn’t exist in the tree then return -1.

public final class LcaBtNoParentBottomUp {

	public static Optional<Integer> lca(Node root, int p, int q) {
		if (isNull(root) || isAbsent(root, p) || isAbsent(root, q)) {
			return absent();
		}
		return of(lcaRecursive(root, p, q));
	}

	private static int lcaRecursive(Node root, int p, int q) {
		// Time complexity: O(n)

		if (isNull(root)) {
			return -1;
		}

		if (root.data == p || root.data == q) {
			return root.data;
		}

		int left = lcaRecursive(root.left, p, q);
		int right = lcaRecursive(root.right, p, q);

		// if p and q are on both sides
		if (left != -1 && right != -1) {
			return root.data;
		}

		// p and q are on one side
		return left != -1 ? left : right;
	}

	public static Node lca(Node root, Node p, Node q) {
		// Time complexity: O(n)

		if (isNull(root) || isNull(p) || isNull(q)) {
			return null;
		}

		if (root.data == p.data || root.data == q.data) {
			return root;
		}

		Node left = lca(root.left, p, q);
		Node right = lca(root.right, p, q);

		// if p and q are on both sides
		if (isNotNull(left) && isNotNull(right)) {
			return root;
		}

		// either one of p, q is on one side
		// or p, q is not in left and right subtrees
		return isNotNull(left) ? left : right;
	}

	public static void main(String[] args) {
		Node root = newNode(3);
		root.left = newNode(5);
		root.right = newNode(1);
		root.left.left = newNode(6);
		root.left.right = newNode(2);
		root.left.right.left = newNode(7);
		root.left.right.right = newNode(4);
		root.right.left = newNode(0);
		root.right.right = newNode(8);
		inOrder(root);
		System.out.println();
		System.out.println(lca(root, 5, 1));
		System.out.println(lca(root, 6, 4));
		System.out.println(lca(root, 4, 10));
		System.out.println();

		Node root2 = newNode(3);
		root2.left = newNode(1);
		root2.right = newNode(5);
		root2.right.right = newNode(8);
		System.out.println(lca(root2, 5, 7));
	}
}
