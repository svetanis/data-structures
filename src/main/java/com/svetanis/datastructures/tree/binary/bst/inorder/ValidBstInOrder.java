package com.svetanis.datastructures.tree.binary.bst.inorder;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.concurrent.atomic.AtomicReference;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a Binary Tree (BT)
// determine if it is a BST or not

// in-order traversal of BST returns
// the nodes in sorted order
// to determine if given BT is BST or not
// perform in-order traversal and keep
// track of the last visited node while
// traversing the tree and check whether
// its key is smaller compared to the current key

public final class ValidBstInOrder {
	// Time complexity O(n);
	// Space complexity: O(h)

	public static boolean isValidBst(Node root) {
		// null, not a Node holding Integer.MIN_VALUE. LC 98 allows a node
		// to hold -2^31, and a node carrying the marker would then be
		// compared against a fake predecessor equal to itself and rejected
		AtomicReference<Integer> prev = new AtomicReference<>();
		return isValidBst(root, prev);
	}

	private static boolean isValidBst(Node root, AtomicReference<Integer> prev) {
		// an empty tree is a BST
		if (isNull(root)) {
			return true;
		}
		// check if left subtree is BST or not
		boolean left = isValidBst(root.left, prev);
		// value of current node should be
		// more than that of prev node
		if (prev.get() != null && root.data <= prev.get()) {
			return false;
		}
		// update the prev node
		prev.set(root.data);
		// check if right subtree is BST or not
		boolean right = isValidBst(root.right, prev);
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
		System.out.println(isValidBst(root2)); // false

		// the smallest node holds the old marker. against a prev seeded
		// with Integer.MIN_VALUE this printed false for both
		System.out.println(isValidBst(newNode(Integer.MIN_VALUE))); // true
		Node root3 = newNode(0);
		root3.left = newNode(Integer.MIN_VALUE);
		System.out.println(isValidBst(root3)); // true
	}
}
