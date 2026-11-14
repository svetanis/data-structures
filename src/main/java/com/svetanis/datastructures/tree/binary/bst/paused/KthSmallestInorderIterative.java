package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 230. Kth Smallest Element in a BST
//
// Input: the root of a binary search tree and an integer k. Returns the k-th smallest value
// (counting from 1), or an empty Optional when the tree has fewer than k nodes. On LeetCode:
// 1 <= k <= n <= 10^4 nodes, 0 <= node.val <= 10^4, so the empty case never arises there.
//
// The one idea: the in-order walk visits the values in ascending order, so the k-th node
// visited is the k-th smallest. Count the nodes visited; the walk stops as soon as the count
// reaches k. The walk uses an explicit stack of the nodes whose left subtree is being
// explored, so a very tall tree cannot overflow the call stack. An empty Optional means "the
// tree has fewer than k nodes", never a value, so -1 stays a legal answer.
//
// Time: O(h + k) -- the walk goes down the left side to the smallest node (h steps) and then
// visits k nodes; h is log n for a balanced tree and n for a chain.
// Space: O(h) -- the stack holds one root-to-node path at most.
//
// Siblings: KthSmallestInorder is the same walk, recursive, with the same Optional answer; its
// call stack overflows on a one-sided chain of about six thousand nodes, this one does not.
// KthSmallestIterative is the same stack walk returning -1 for "not found".

public final class KthSmallestInorderIterative {

	public static Optional<Integer> kthSmallest(TreeNode root, int k) {
		int remaining = k; // nodes still to visit before the answer
		Deque<TreeNode> stack = new ArrayDeque<>();
		TreeNode curr = root;
		while (!stack.isEmpty() || curr != null) {
			while (curr != null) { // DESCEND: push the path down the left side
				stack.push(curr);
				curr = curr.left;
			}
			curr = stack.pop(); // VISIT: the smallest value not yet visited
			remaining--;
			if (remaining == 0) {
				return Optional.of(curr.val); // RETURN: this is the k-th
			}
			curr = curr.right; // WALK: then the larger values
		}
		return Optional.empty(); // RETURN: fewer than k nodes
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
