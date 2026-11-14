package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 230. Kth Smallest Element in a BST
//
// Input: the root of a binary search tree and an integer k. Returns the k-th smallest value
// (counting from 1), or an empty Optional when the tree has fewer than k nodes. On LeetCode:
// 1 <= k <= n <= 10^4 nodes, 0 <= node.val <= 10^4, so the empty case never arises there.
//
// The one idea: the in-order walk visits the values in ascending order, so the k-th node
// visited is the k-th smallest. Count the nodes visited; the walk stops as soon as the count
// reaches k. Each call returns an Optional: empty means "not found in this part of the tree",
// never a value, so -1 stays a legal answer.
//
// Known limit: the recursion is as deep as the tree, so a one-sided chain of about six
// thousand nodes or more overflows the default stack (LC allows 10^4 nodes).
// KthSmallestInorderIterative is the same walk with an explicit stack.
//
// Time: O(h + k) -- the walk goes down the left side to the smallest node (h steps) and then
// visits k nodes; h is log n for a balanced tree and n for a chain.
// Space: O(h) -- the recursion stack.
//
// Siblings: KthSmallestInorderIterative is this walk with an explicit stack and the same
// Optional answer; KthSmallestInorderGlobal keeps the answer in a field and returns -1 for
// "not found"; KthSmallestIterative is the explicit-stack walk returning -1; KthLargestInorder
// is the mirror walk, right side first.

public final class KthSmallestInorder {

	public static Optional<Integer> kthSmallest(TreeNode root, int k) {
		AtomicInteger count = new AtomicInteger(); // nodes visited so far
		return kthSmallest(root, k, count);
	}

	private static Optional<Integer> kthSmallest(TreeNode root, int k, AtomicInteger count) {
		if (isNull(root) || count.get() >= k) { // BASE: empty subtree, or the answer is already found
			return Optional.empty();
		}
		// follow inorder traversal
		// recur for left subtree first
		Optional<Integer> left = kthSmallest(root.left, k, count); // WALK: smaller values first
		if (left.isPresent()) { // RETURN: found below, pass it up
			return left;
		}
		// increment count of visited nodes
		int curr = count.incrementAndGet(); // VISIT: this node is the curr-th smallest
		// if count becomes k now, then
		// this is the k-th smallest node
		if (curr == k) {
			return Optional.of(root.val); // RETURN: found
		}
		// recur for right subtree
		return kthSmallest(root.right, k, count); // WALK: larger values
	}

	public static void main(String[] args) {
		TreeNode root = newNode(5);
		root.left = newNode(3);
		root.right = newNode(6);
		root.left.left = newNode(2);
		root.left.right = newNode(4);
		root.left.left.left = newNode(1);
		System.out.println(kthSmallest(root, 3)); // Optional[3]

		TreeNode root1 = newNode(3);
		root1.left = newNode(1);
		root1.right = newNode(4);
		root1.left.right = newNode(2);
		System.out.println(kthSmallest(root1, 1)); // Optional[1]

		// -1 is a legal node value, and here it is the answer
		TreeNode neg = newNode(15);
		neg.left = newNode(-1);
		neg.right = newNode(16);
		System.out.println(kthSmallest(neg, 1)); // Optional[-1]
		System.out.println(kthSmallest(neg, 4)); // Optional.empty -- only 3 nodes
	}
}
