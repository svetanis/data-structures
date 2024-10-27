package com.svetanis.datastructures.tree.binary.bst.topk;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayDeque;
import java.util.Deque;

import com.google.common.base.Optional;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and and integer k,
// find k'th smallest element in BST

// K-SMALLEST : INORDER TRAVERSAL
// in-order traversal of BST sorts
// all nodes in ascending order
// while doing traversal, count nodes
// visited so far. stop when the count
// becomes equal to k

public final class KthSmallestIterative {

	public static Optional<Integer> kthSmallest(Node root, int k) {
		// Time complexity: O(h + k)
		
		Deque<Node> stack = new ArrayDeque<>();
		while (root != null || !stack.isEmpty()) {
			while (root != null) {
				stack.push(root);
				root = root.left;
			}
			root = stack.pop();
			if (--k == 0) {
				return of(root.data);
			}
			root = root.right;
		}
		return absent();
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
