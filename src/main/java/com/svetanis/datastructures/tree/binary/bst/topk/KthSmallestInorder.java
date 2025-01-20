package com.svetanis.datastructures.tree.binary.bst.topk;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.concurrent.atomic.AtomicInteger;

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

	public static int kthSmallest(Node root, int k) {
		// count of nodes visited
		AtomicInteger count = new AtomicInteger();
		return kthSmallest(root, k, count);
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
		Node root = newNode(5);
		root.left = newNode(3);
		root.right = newNode(6);
		root.left.left = newNode(2);
		root.left.right = newNode(4);
		root.left.left.left = newNode(1);
		System.out.println(kthSmallest(root, 3)); // 3

		Node root1 = newNode(3);
		root1.left = newNode(1);
		root1.right = newNode(4);
		root1.left.right = newNode(2);
		System.out.println(kthSmallest(root1, 1)); // 1
	}
}
