package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.base.Optional;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 230. K-th Smallest BST

// given a BST and and integer k,
// find k'th smallest element in BST

// K-SMALLEST : INORDER TRAVERSAL
// in-order traversal of BST sorts
// all nodes in ascending order
// while doing traversal, count nodes
// visited so far. stop when the count
// becomes equal to k

public final class KthSmallestInorder {
	// Time Complexity: O(log n + k)
	// Space Complexity: O(log n)

	public static Optional<Integer> kthSmallest(Node root, int k) {
		// count of nodes visited
		AtomicInteger count = new AtomicInteger();
		return kthSmallest(root, k, count);
	}

	// absent means "not found here" -- never a value, so -1 stays a legal answer
	private static Optional<Integer> kthSmallest(Node root, int k, AtomicInteger count) {
		if (isNull(root) || count.get() >= k) {
			return absent();
		}
		// follow inorder traversal
		// recur for left subtree first
		Optional<Integer> left = kthSmallest(root.left, k, count);
		if (left.isPresent()) {
			return left;
		}
		// increment count of visited nodes
		int curr = count.incrementAndGet();
		// if count becomes k now, then
		// this is the k'th smallest node
		if (curr == k) {
			return of(root.data);
		}
		// recur for right subtree
		return kthSmallest(root.right, k, count);
	}

	public static void main(String[] args) {
		Node root = newNode(5);
		root.left = newNode(3);
		root.right = newNode(6);
		root.left.left = newNode(2);
		root.left.right = newNode(4);
		root.left.left.left = newNode(1);
		System.out.println(kthSmallest(root, 3)); // Optional.of(3)

		Node root1 = newNode(3);
		root1.left = newNode(1);
		root1.right = newNode(4);
		root1.left.right = newNode(2);
		System.out.println(kthSmallest(root1, 1)); // Optional.of(1)

		// -1 is a legal node value, and here it is the answer
		Node neg = newNode(15);
		neg.left = newNode(-1);
		neg.right = newNode(16);
		System.out.println(kthSmallest(neg, 1)); // Optional.of(-1)
		System.out.println(kthSmallest(neg, 4)); // Optional.absent() -- only 3 nodes
	}
}
