package com.svetanis.datastructures.tree.binary.bst.candidate;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and a number k
// find the smallest number 
// that is greater than or equal to k

public final class FirstNodeLargerOrEqualKRecursive {
	// Time Complexity: O(h)

	public static Node firstLargerOrEqualK(Node root, int key) {
		// the descent decides the answer on its own, so nothing here
		// needs to reach into a child. inspecting root.left.data to
		// recognise the answer early throws for every node with no left
		// child, and there is one of those on nearly every path
		if (isNull(root)) {
			return null;
		}
		if (root.data >= key) {
			// a candidate. anything smaller that still qualifies is
			// on the left, so keep this one and go look
			Node better = firstLargerOrEqualK(root.left, key);
			return isNull(better) ? root : better;
		}
		return firstLargerOrEqualK(root.right, key);
	}

	public static void main(String[] args) {
		Node root = newNode(19);
		root.left = newNode(7);
		root.right = newNode(21);
		root.left.left = newNode(3);
		root.left.right = newNode(11);
		root.left.right.left = newNode(9);
		root.left.right.right = newNode(14);

		inOrder(root);
		System.out.println();
		System.out.println(firstLargerOrEqualK(root, 20)); // 21
		System.out.println(firstLargerOrEqualK(root, 18)); // 19

		// each of these threw on root.left.data before
		System.out.println(firstLargerOrEqualK(root, 21)); // 21
		System.out.println(firstLargerOrEqualK(root, 4)); // 7
		System.out.println(firstLargerOrEqualK(root, 99)); // null
		System.out.println(firstLargerOrEqualK(null, 1)); // null
	}
}