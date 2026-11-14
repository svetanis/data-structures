package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 230. Kth Smallest Element in a BST
//
// Input: the root of a binary search tree and an integer k. Returns the k-th smallest value
// (counting from 1). On LeetCode: 1 <= k <= n <= 10^4 nodes, 0 <= node.val <= 10^4. If the
// tree has fewer than k nodes this returns -1, which cannot be told apart from a node holding
// -1 (LC's values are never negative, so it is unambiguous there); KthSmallestInorder returns
// an Optional and can tell them apart.
//
// The one idea: the in-order walk visits the values in ascending order, so the k-th node
// visited is the k-th smallest. Count the nodes visited in a field, and when the count reaches
// k store that node's value in a second field. Once the count is k, every call returns at
// once, so the walk stops. The two fields live across the whole walk, so kthSmallest() resets
// them on its first lines; without that, a second call on the same object would start with the
// first call's count.
//
// Time: O(h + k) -- the walk goes down the left side to the smallest node (h steps) and then
// visits k nodes; h is log n for a balanced tree and n for a chain.
// Space: O(h) -- the recursion stack.
//
// Siblings: KthSmallestInorder is the same walk returning an Optional, with the count passed
// down instead of kept in a field; KthSmallestIterative is the explicit-stack walk.

public final class KthSmallestInorderGlobal {

	private int count = 0; // nodes visited so far
	private int value = -1; // the answer, once found

	public int kthSmallest(TreeNode root, int k) {
		this.count = 0;
		this.value = -1;
		dfs(root, k);
		return value;
	}

	private void dfs(TreeNode root, int k) {
		if (root == null || count >= k) { // BASE: empty subtree, or the answer is already found
			return;
		}
		// follow inorder traversal
		// recur for left subtree first
		dfs(root.left, k); // WALK: smaller values first
		if (count >= k) { // the answer was found below: do not count this node
			return;
		}
		// increment count of visited nodes
		count++; // VISIT: this node is the count-th smallest
		// if count becomes k now, then
		// this is the k-th smallest node
		if (count == k) {
			value = root.val; // RECORD: found
			return;
		}
		// recur for right subtree
		dfs(root.right, k); // WALK: larger values
	}

	public static void main(String[] args) {
		KthSmallestInorderGlobal ksi = new KthSmallestInorderGlobal();
		TreeNode root = newNode(5);
		root.left = newNode(3);
		root.right = newNode(6);
		root.left.left = newNode(2);
		root.left.right = newNode(4);
		root.left.left.left = newNode(1);
		System.out.println(ksi.kthSmallest(root, 3)); // 3

		TreeNode root1 = newNode(3);
		root1.left = newNode(1);
		root1.right = newNode(4);
		root1.left.right = newNode(2);
		System.out.println(ksi.kthSmallest(root1, 1)); // 1
	}
}
