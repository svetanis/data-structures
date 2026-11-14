package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 230. Kth Smallest Element in a BST
//
// Input: the root of a binary search tree and an integer k. Returns the k-th smallest value
// (counting from 1). On LeetCode: 1 <= k <= n <= 10^4 nodes, 0 <= node.val <= 10^4. If the
// tree has fewer than k nodes this returns -1, which cannot be told apart from a node holding
// -1 (LC's values are never negative, so it is unambiguous there).
//
// The one idea: the in-order walk visits the values in ascending order, so the k-th node
// visited is the k-th smallest. Count the nodes visited by counting k down; the walk stops as
// soon as it reaches 0. The walk uses an explicit stack of the nodes whose left subtree is
// being explored, so a very tall tree cannot overflow the call stack.
//
// Time: O(h + k) -- the walk goes down the left side to the smallest node (h steps) and then
// visits k nodes; h is log n for a balanced tree and n for a chain.
// Space: O(h) -- the stack holds one root-to-node path at most.
//
// Siblings: KthSmallestInorderIterative is the same stack walk returning an Optional, so that
// "fewer than k nodes" differs from a node holding -1; KthSmallestInorder and
// KthSmallestInorderGlobal are the recursive walks.

public final class KthSmallestIterative {

	public static int kthSmallest(TreeNode root, int k) {
		TreeNode curr = root;
		Deque<TreeNode> dq = new ArrayDeque<>();
		while (curr != null || !dq.isEmpty()) {
			while (curr != null) { // DESCEND: push the path down the left side
				dq.push(curr);
				curr = curr.left;
			}
			curr = dq.pop(); // VISIT: the smallest value not yet visited
			if (--k == 0) { // COUNT: k more to go, this one included
				return curr.val; // RETURN: this is the k-th
			}
			curr = curr.right; // WALK: then the larger values
		}
		return -1; // RETURN: fewer than k nodes
	}

	public static void main(String[] args) {
		TreeNode root = newNode(20);
		root.left = newNode(8);
		root.right = newNode(22);
		root.left.left = newNode(4);
		root.left.right = newNode(12);
		root.left.right.left = newNode(10);
		root.left.right.right = newNode(14);
		System.out.println(kthSmallest(root, 3)); // 10
	}
}
