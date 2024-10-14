package com.svetanis.datastructures.tree.binary.bst.topk;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST, find the sum of all
// elements greater than and equal
// to k-th largest element

public final class SumKthLargest {

	public static int sum(Node root, int k) {
		// Time complexity: O(k)

		// count of nodes visited
		AtomicInteger count = new AtomicInteger();
		return sum(root, k, count);
	}

	private static int sum(Node root, int k, AtomicInteger count) {

		if (isNull(root) || count.get() > k) {
			return 0;
		}

		// follow reverse in-order traversal
		// recur for right subtree first
		int sum = sum(root.right, k, count);

		if (count.get() >= k) {
			return sum;
		}

		// add root's data
		sum += root.data;

		// increment count of visited nodes
		int curr = count.incrementAndGet();

		if (curr >= k) {
			return sum;
		}

		// if count < k, recur for left subtree
		return sum + sum(root.left, k, count);
	}

	public static void main(String[] args) {
		Node root = newNode(19);
		root.left = newNode(7);
		root.right = newNode(21);
		root.left.left = newNode(3);
		root.left.right = newNode(11);
		root.left.right.left = newNode(9);
		root.left.right.right = newNode(13);
		System.out.println(sum(root, 2));
	}
}
