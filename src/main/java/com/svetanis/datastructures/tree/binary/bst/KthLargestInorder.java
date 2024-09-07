package com.svetanis.datastructures.tree.binary.bst;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.base.Optional;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and positive integer k
// find k'th largest element in BST

// K-LARGEST : INORDER TRAVERSAL
// reverse in-order traversal of BST
// sorts all nodes in descending order
// while doing traversal, count nodes
// visited so far. stop when the count
// becomes equal to k

public final class KthLargestInorder {

	public static Optional<Integer> kthLargest(Node root, int k) {
		// Time complexity: O(h + k)

		// count of visited nodes
		AtomicInteger count = new AtomicInteger();
		int kLargest = kthLargest(root, k, count);
		return kLargest != -1 ? of(kLargest) : absent();
	}

	private static int kthLargest(Node root, int k, AtomicInteger count) {

		// base case
		if (isNull(root) || count.get() >= k) {
			return -1;
		}
		// follow reverse in-order traversal so that
		// the largest element is visited first

		// recur for right subtree
		int right = kthLargest(root.right, k, count);

		if (right != -1) {
			return right;
		}

		// increment count of visited nodes
		int curr = count.incrementAndGet();

		// if c becomes k now,
		// then this is the k'th largest
		if (curr == k) {
			return root.data;
		}

		// recur for left subtree
		return kthLargest(root.left, k, count);
	}

	public static void main(String[] args) {
		Node root = newNode(20);
		root.left = newNode(8);
		root.right = newNode(22);
		root.left.left = newNode(4);
		root.left.right = newNode(12);
		root.left.right.left = newNode(10);
		root.left.right.right = newNode(14);
		System.out.println(kthLargest(root, 5));
	}
}
