package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Kth Largest Element in a BST (not a LeetCode problem; LC 230 asks for the k-th smallest, and
// the k-th largest of n nodes is the (n - k + 1)-th smallest)
//
// Input: the root of a binary search tree and a positive integer k. Returns the k-th largest
// value (counting from 1), or an empty Optional when the tree has fewer than k nodes.
//
// The one idea: the REVERSE in-order walk -- right subtree, node, left subtree -- visits the
// values in descending order, so the k-th node visited is the k-th largest. Count the nodes
// visited; the walk stops as soon as the count reaches k. Each call returns an Optional: empty
// means "not found in this part of the tree", never a value, so -1 stays a legal answer.
//
// Known limit: the walk recurses once per level of the tree, so a one-sided chain of a few
// thousand nodes overflows the default stack; the exact depth varies from run to run.
// KthLargestInorderIterative has no such limit.
//
// Time: O(h + k) -- the walk goes down the right side to the largest node (h steps) and then
// visits k nodes; h is log n for a balanced tree and n for a chain.
// Space: O(h) -- the recursion stack.
//
// Siblings: KthLargestInorderIterative is the same walk with an explicit stack;
// KthSmallestInorder is the mirror walk, left side first.

public final class KthLargestInorder {

	public static Optional<Integer> kthLargest(TreeNode root, int k) {
		AtomicInteger count = new AtomicInteger(); // nodes visited so far
		return kthLargest(root, k, count);
	}

	private static Optional<Integer> kthLargest(TreeNode root, int k, AtomicInteger count) {
		if (isNull(root) || count.get() >= k) { // BASE: empty subtree, or the answer is already found
			return Optional.empty();
		}
		// follow reverse in-order traversal so that
		// the largest element is visited first

		// recur for right subtree
		Optional<Integer> right = kthLargest(root.right, k, count); // WALK: larger values first

		if (right.isPresent()) { // RETURN: found below, pass it up
			return right;
		}

		// increment count of visited nodes
		int curr = count.incrementAndGet(); // VISIT: this node is the curr-th largest

		// if curr becomes k now,
		// then this is the k-th largest
		if (curr == k) {
			return Optional.of(root.val); // RETURN: found
		}

		// recur for left subtree
		return kthLargest(root.left, k, count); // WALK: smaller values
	}

	public static void main(String[] args) {
		TreeNode root = newNode(20);
		root.left = newNode(8);
		root.right = newNode(22);
		root.left.left = newNode(4);
		root.left.right = newNode(12);
		root.left.right.left = newNode(10);
		root.left.right.right = newNode(14);
		System.out.println(kthLargest(root, 5)); // Optional[10]

		// -1 is a legal node value, and here it is the answer
		TreeNode neg = newNode(15);
		neg.left = newNode(-1);
		neg.right = newNode(16);
		System.out.println(kthLargest(neg, 3)); // Optional[-1]
		System.out.println(kthLargest(neg, 4)); // Optional.empty -- only 3 nodes
	}
}
