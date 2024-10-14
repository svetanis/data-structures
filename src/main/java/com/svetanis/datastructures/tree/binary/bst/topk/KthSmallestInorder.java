package com.svetanis.datastructures.tree.binary.bst.topk;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.concurrent.atomic.AtomicInteger;

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

public final class KthSmallestInorder {

	public static Optional<Integer> kthSmallest(Node root, int k) {
		// Time complexity: O(h + k)

		// count of nodes visited
		AtomicInteger count = new AtomicInteger();
		int kSmallest = kthSmallest(root, k, count);
		return kSmallest != -1 ? of(kSmallest) : absent();
	}

	private static int kthSmallest(Node root, int k, AtomicInteger count) {

		if (isNull(root) || count.get() >= k) {
			return -1;
		}

		// follow inorder traversal
		// recur for left subtree first
		int left = kthSmallest(root.left, k, count);

		if (left != -1) {
			return left;
		}

		// increment count of visited nodes
		int curr = count.incrementAndGet();

		// if count becomes k now, then
		// this is the k'th smallest node
		if (curr == k) {
			return root.data;
		}

		// recur for right subtree
		return kthSmallest(root.right, k, count);
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
