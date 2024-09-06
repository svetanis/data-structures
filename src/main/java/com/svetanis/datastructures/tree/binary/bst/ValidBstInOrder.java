package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Integer.MIN_VALUE;

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

	public static boolean isValidBst(Node root) {
		Node prev = new Node(MIN_VALUE);
		return isValidBst(root, prev);
	}

	private static boolean isValidBst(Node root, Node prev) {
		// Time complexity O(n);
		// Space complexity: O(h)

		// an empty tree is a BST
		if (isNull(root)) {
			return true;
		}

		// check if left subtree is BST or not
		boolean left = isValidBst(root.left, prev);

		// value of current node should be
		// more than that of prev node
		if (root.data <= prev.data) {
			return false;
		}

		// update the prev node
		prev.data = root.data;
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
		System.out.println(isValidBst(root2));
	}
}
