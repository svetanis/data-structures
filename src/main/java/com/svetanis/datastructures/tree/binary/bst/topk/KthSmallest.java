package com.svetanis.datastructures.tree.binary.bst.topk;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.size;

import com.google.common.base.Optional;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and and integer k,
// find k'th smallest element in BST

public final class KthSmallest {
	// Time Complexity : O(n) or O(k + h)
	// Aux Space Complexity: O(h)
	// Total Space Complexity: O(n)

	public static Optional<Integer> kthSmallest(Node root, int k) {
		if (isNull(root)) {
			return absent();
		}
		int left = size(root.left);
		if (k == left + 1) {
			return of(root.data);
		} else if (left < k) {
			return kthSmallest(root.right, k - left - 1);
		} else {
			return kthSmallest(root.left, k);
		}
	}

	public static void main(String[] args) {
		Node root = newNode(20);
		root.left = newNode(8);
		root.right = newNode(22);
		root.left.left = newNode(4);
		root.left.right = newNode(12);
		root.left.right.left = newNode(10);
		root.left.right.right = newNode(14);
		System.out.println(kthSmallest(root, 3));
	}
}