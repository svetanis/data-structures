package com.svetanis.datastructures.tree.binary.bst.inorder;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.insert;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST of integers with duplicate entries
// if an element has a duplicate entry,
// it is in the right subtree of that element
// count the total number of duplicates

// traverse the given BST in-order and compare
// the previous element with the current element

// what it counts: EXTRA COPIES -- nodes minus distinct
// values. 2, 2, 2 counts 2. "how many values repeat"
// would count 1; the two readings agree until some
// value appears three or more times.
//
// `prev` is passed down rather than kept as a running
// value, which works only because insert() sends a
// duplicate RIGHT, where it becomes the leftmost node
// in the right subtree of the copy inserted before it,
// and so is compared against that copy.

public final class CountDuplicates {

	public static int duplicates(Node root) {
		return duplicates(root, root);
	}

	private static int duplicates(Node root, Node prev) {
		int count = 0;
		if (isNull(root)) {
			return count;
		}
		count += duplicates(root.left, prev);
		if (root != prev && root.data == prev.data) {
			count++;
		}
		count += duplicates(root.right, root);
		return count;
	}

	public static void main(String[] args) {
		Node root = newNode(5);
		insert(root, 5);
		insert(root, 3);
		insert(root, 2);
		insert(root, 2);
		insert(root, 4);
		insert(root, 4);
		insert(root, 6);
		insert(root, 6);

		inOrder(root);
		System.out.println();
		System.out.println(duplicates(root));
	}
}