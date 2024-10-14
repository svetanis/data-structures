package com.svetanis.datastructures.tree.binary.bst.topk;

import static com.google.common.base.Optional.absent;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.size;
import static com.svetanis.java.base.Optionals.present;

import com.google.common.base.Optional;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BT and integer k
// find the k-th node appearing
// in an inorder traversal

// If the left child has k - 1 children, 
// then the root is the k-th node;

// If the left child has k or more children, 
// then the k-th node is the k-th node of the left subtree;

// If the left child has l < k - 1, 
// the k-th node is the k - (l + 1)-th node of the right subtree

public final class KthNode {
	// Time Complexity: O(log n)

	public static Optional<Node> kthNode(Node root, int k) {
		while (isNotNull(root) && k > 0) {
			int left = isNull(root.left) ? 0 : size(root.left);
			if (left < k - 1) {
				k = k - (left + 1);
				root = root.right;
			} else if (left == k - 1) {
				return present(root);
			} else {
				// leftSize > k - 1
				root = root.left;
			}
		}
		return absent();
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(4);
		root.left.right = new Node(5);
		root.right.right = new Node(6);

		inOrder(root);
		System.out.println();
		System.out.println();
		for (int k = 1; k <= 6; k++) {
			System.out.println(kthNode(root, k));
		}
	}
}