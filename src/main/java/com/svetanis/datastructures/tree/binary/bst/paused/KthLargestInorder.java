package com.svetanis.datastructures.tree.binary.bst.paused;

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
		return kthLargest(root, k, count);
	}

	// absent means "not found here" -- never a value, so -1 stays a legal answer
	private static Optional<Integer> kthLargest(Node root, int k, AtomicInteger count) {

		// base case
		if (isNull(root) || count.get() >= k) {
			return absent();
		}
		// follow reverse in-order traversal so that
		// the largest element is visited first

		// recur for right subtree
		Optional<Integer> right = kthLargest(root.right, k, count);

		if (right.isPresent()) {
			return right;
		}

		// increment count of visited nodes
		int curr = count.incrementAndGet();

		// if c becomes k now,
		// then this is the k'th largest
		if (curr == k) {
			return of(root.data);
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
		System.out.println(kthLargest(root, 5)); // Optional.of(10)

		// -1 is a legal node value, and here it is the answer
		Node neg = newNode(15);
		neg.left = newNode(-1);
		neg.right = newNode(16);
		System.out.println(kthLargest(neg, 3)); // Optional.of(-1)
		System.out.println(kthLargest(neg, 4)); // Optional.absent() -- only 3 nodes
	}
}
