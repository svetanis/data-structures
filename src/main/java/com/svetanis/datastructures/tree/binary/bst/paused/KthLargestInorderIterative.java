package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Kth Largest Element in a BST (not a LeetCode problem; LC 230 asks for the k-th smallest, and
// the k-th largest of n nodes is the (n - k + 1)-th smallest)
//
// Input: the root of a binary search tree and a positive integer k. Returns the k-th largest
// value (counting from 1), or an empty Optional when the tree has fewer than k nodes.
//
// The one idea: the REVERSE in-order walk -- right subtree, node, left subtree -- visits the
// values in descending order, so the k-th node visited is the k-th largest. Count the nodes
// visited; the walk stops as soon as the count reaches k. The walk uses an explicit stack of
// the nodes whose right subtree is being explored, so a very tall tree cannot overflow the call
// stack. An empty Optional means "the tree has fewer than k nodes", never a value, so -1 stays
// a legal answer.
//
// Time: O(h + k) -- the walk goes down the right side to the largest node (h steps) and then
// visits k nodes; h is log n for a balanced tree and n for a chain.
// Space: O(h) -- the stack holds one root-to-node path at most.
//
// Siblings: KthLargestInorder is the same walk, recursive, with the same Optional answer; its
// call stack overflows on a one-sided chain of a few thousand nodes, this one does not.
// KthSmallestInorderIterative is the mirror walk, left side first.

public final class KthLargestInorderIterative {

	public static Optional<Integer> kthLargest(TreeNode root, int k) {
		int remaining = k; // nodes still to visit before the answer
		Deque<TreeNode> stack = new ArrayDeque<>();
		TreeNode curr = root;
		while (!stack.isEmpty() || curr != null) {
			while (curr != null) { // DESCEND: push the path down the right side
				stack.push(curr);
				curr = curr.right;
			}
			curr = stack.pop(); // VISIT: the largest value not yet visited
			remaining--;
			if (remaining == 0) {
				return Optional.of(curr.val); // RETURN: this is the k-th largest
			}
			curr = curr.left; // WALK: then the smaller values
		}
		return Optional.empty(); // RETURN: fewer than k nodes
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
