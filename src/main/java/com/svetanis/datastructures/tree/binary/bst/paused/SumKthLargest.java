package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Sum of the Elements From the K-th Largest Up (not a LeetCode problem)
//
// Input: the root of a binary search tree with distinct values and a positive integer k.
// Returns the sum of all elements greater than or equal to the k-th largest element, which
// is the sum of the k largest values (the sum of the whole tree when it has fewer than k
// nodes). With equal values in the tree "greater than or equal" would also take every copy of
// the k-th value; this file adds exactly k nodes.
//
// The one idea: the REVERSE in-order walk -- right subtree, node, left subtree -- visits the
// values from the largest down. Add each value as it is visited and count the nodes; stop
// descending as soon as k have been added. The sum comes back up the recursion: each call
// returns the total of what it added in its own part of the walk.
//
// Time: O(h + k) -- the walk goes down the right side to the largest node (h steps) and then
// visits k nodes; h is log n for a balanced tree and n for a chain.
// Space: O(h) -- the recursion stack.
//
// Siblings: SumKthSmallest is the mirror walk, left side first.

public final class SumKthLargest {

	public static int sum(TreeNode root, int k) {
		AtomicInteger count = new AtomicInteger(); // nodes added so far
		return sum(root, k, count);
	}

	private static int sum(TreeNode root, int k, AtomicInteger count) {

		if (isNull(root) || count.get() > k) { // BASE: empty subtree
			return 0;
		}

		// follow reverse in-order traversal
		// recur for right subtree first
		int sum = sum(root.right, k, count); // WALK: larger values first

		if (count.get() >= k) { // SKIP: k values were added below, this one is not needed
			return sum;
		}

		// add root's data
		sum += root.val; // ADD

		// increment count of visited nodes
		int curr = count.incrementAndGet();

		if (curr >= k) { // STOP: this was the k-th, leave the left side unvisited
			return sum;
		}

		// if count < k, recur for left subtree
		return sum + sum(root.left, k, count); // WALK: smaller values
	}

	public static void main(String[] args) {
		TreeNode root = newNode(19);
		root.left = newNode(7);
		root.right = newNode(21);
		root.left.left = newNode(3);
		root.left.right = newNode(11);
		root.left.right.left = newNode(9);
		root.left.right.right = newNode(13);
		System.out.println(sum(root, 2)); // 40 -- 21 + 19
	}
}
