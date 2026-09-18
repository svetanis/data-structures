package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and an integer k
// find the sum of all elements
// smaller than or equal to Kth
// smallest element

// in-order traversal of BST
// sorts elements in ascending order
// while traversing, count nodes
// visited so far and keep adding
// nodes until count becomes k

public final class SumKthSmallest {

	public static int sum(Node root, int k) {
		// Time complexity: O(h + k)

		// count of nodes visited
		AtomicInteger count = new AtomicInteger();
		return sum(root, k, count);
	}

	private static int sum(Node root, int k, AtomicInteger count) {

		if (isNull(root) || count.get() > k) {
			return 0;
		}

		// follow inorder traversal
		// recur for left subtree first
		int sum = sum(root.left, k, count);

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

		// if count < k, recur for right subtree
		return sum + sum(root.right, k, count);
	}

	public static void main(String[] args) {
		Node root = newNode(20);
		root.left = newNode(8);
		root.right = newNode(22);
		root.left.left = newNode(4);
		root.left.right = newNode(12);
		root.left.right.left = newNode(10);
		// 14, not 22: this node sits under 20's LEFT child, so a 22 here
		// makes the test tree not a BST. the answer for k = 3 is the same
		// either way, which is why it went unnoticed
		root.left.right.right = newNode(14);
		System.out.println(sum(root, 3)); // 22
		System.out.println(sum(root, 7)); // 90
	}
}
