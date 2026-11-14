package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Sum of the Elements Up to the K-th Smallest (not a LeetCode problem)
//
// Input: the root of a binary search tree with distinct values and a positive integer k.
// Returns the sum of all elements smaller than or equal to the k-th smallest element, which
// is the sum of the k smallest values (the sum of the whole tree when it has fewer than k
// nodes). With equal values in the tree "smaller than or equal" would also take every copy of
// the k-th value; this file adds exactly k nodes.
//
// The one idea: the in-order walk visits the values in ascending order. Add each value as it
// is visited and count the nodes; stop descending as soon as k have been added. The sum comes
// back up the recursion: each call returns the total of what it added in its own part of the
// walk.
//
// Time: O(h + k) -- the walk goes down the left side to the smallest node (h steps) and then
// visits k nodes; h is log n for a balanced tree and n for a chain.
// Space: O(h) -- the recursion stack.
//
// Siblings: SumKthLargest is the mirror walk, right side first.

public final class SumKthSmallest {

	public static int sum(TreeNode root, int k) {
		AtomicInteger count = new AtomicInteger(); // nodes added so far
		return sum(root, k, count);
	}

	private static int sum(TreeNode root, int k, AtomicInteger count) {

		if (isNull(root) || count.get() > k) { // BASE: empty subtree
			return 0;
		}

		// follow inorder traversal
		// recur for left subtree first
		int sum = sum(root.left, k, count); // WALK: smaller values first

		if (count.get() >= k) { // SKIP: k values were added below, this one is not needed
			return sum;
		}

		// add root's data
		sum += root.val; // ADD

		// increment count of visited nodes
		int curr = count.incrementAndGet();

		if (curr >= k) { // STOP: this was the k-th, leave the right side unvisited
			return sum;
		}

		// if count < k, recur for right subtree
		return sum + sum(root.right, k, count); // WALK: larger values
	}

	public static void main(String[] args) {
		TreeNode root = newNode(20);
		root.left = newNode(8);
		root.right = newNode(22);
		root.left.left = newNode(4);
		root.left.right = newNode(12);
		root.left.right.left = newNode(10);
		// 14 and not 22: this node sits under 20's LEFT child, so it must be below 20
		root.left.right.right = newNode(14);
		System.out.println(sum(root, 3)); // 22 -- 4 + 8 + 10
		System.out.println(sum(root, 7)); // 90 -- every node
	}
}
