package com.svetanis.datastructures.tree.binary.bst.candidate;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and a number k
// find the greatest number 
// that is less than or equal to k

public final class LargestNodeLessOrEqualKRecursive {
	// Time Complexity: O(h)

	public static Node largestLessOrEqual(Node root, int key) {
		// the mirror of FirstNodeLargerOrEqualKRecursive, and it had the
		// mirror of the same fault: reading root.right.data to spot the
		// answer early throws for every node with no right child
		if (isNull(root)) {
			return null;
		}
		if (root.data <= key) {
			// a candidate. anything larger that still qualifies is on
			// the right, so keep this one and go look
			Node better = largestLessOrEqual(root.right, key);
			return isNull(better) ? root : better;
		}
		return largestLessOrEqual(root.left, key);
	}

	public static void main(String[] args) {
		Node root = newNode(5);
		root.left = newNode(2);
		root.right = newNode(12);
		root.left.left = newNode(1);
		root.left.right = newNode(3);
		root.right.left = newNode(9);
		root.right.right = newNode(21);
		root.right.right.left = newNode(19);
		root.right.right.right = newNode(25);

		inOrder(root);
		System.out.println();
		System.out.println(largestLessOrEqual(root, 24)); // 21
		System.out.println(largestLessOrEqual(root, 4)); // 3

		// each of these threw on root.right.data before
		System.out.println(largestLessOrEqual(root, 12)); // 12
		System.out.println(largestLessOrEqual(root, 0)); // null
		System.out.println(largestLessOrEqual(null, 1)); // null
	}
}