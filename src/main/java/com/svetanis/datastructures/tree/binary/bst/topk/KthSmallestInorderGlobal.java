package com.svetanis.datastructures.tree.binary.bst.topk;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

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

public final class KthSmallestInorderGlobal {
	// Time Complexity: O(log n + k)
	// Space Complexity: O(log n)

	private int count = 0;
	private int value = -1;

	public int kthSmallest(Node root, int k) {
		dfs(root, k);
		return value;
	}

	private void dfs(Node root, int k) {
		if (root == null) {
			return;
		}
		// follow inorder traversal
		// recur for left subtree first
		dfs(root.left, k);
		// increment count of visited nodes
		count++;
		// if count becomes k now, then
		// this is the k'th smallest node
		if (count == k) {
			value = root.data;
			return;
		}
		// recur for right subtree
		dfs(root.right, k);
	}

	public static void main(String[] args) {
		KthSmallestInorderGlobal ksi = new KthSmallestInorderGlobal();
		Node root = newNode(5);
		root.left = newNode(3);
		root.right = newNode(6);
		root.left.left = newNode(2);
		root.left.right = newNode(4);
		root.left.left.left = newNode(1);
		System.out.println(ksi.kthSmallest(root, 3)); // 3

		KthSmallestInorderGlobal ksi2 = new KthSmallestInorderGlobal();
		Node root1 = newNode(3);
		root1.left = newNode(1);
		root1.right = newNode(4);
		root1.left.right = newNode(2);
		System.out.println(ksi2.kthSmallest(root1, 1)); // 1
	}
}
