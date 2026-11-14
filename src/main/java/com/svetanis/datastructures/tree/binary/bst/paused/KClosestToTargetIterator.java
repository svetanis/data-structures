package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static java.lang.Math.abs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 272. Closest Binary Search Tree Value II
//
// Input: the root of a binary search tree, a target (a double) and an integer k. Returns the k
// values in the tree closest to the target, in ascending order, without copying the tree into
// a list first. On LeetCode: 1 <= k <= n <= 10^4 nodes, 0 <= node.val <= 10^9, the k closest
// values are unique, -10^9 <= target <= 10^9 (any output order is accepted).
//
// The one idea: a BstIterator hands out the values in ascending order, and in that order the k
// closest values to the target form one unbroken run. Keep a window of the last k values that
// were good enough, oldest (smallest) at the front. While the window has fewer than k values,
// add every value. After that, a new value (larger than all in the window) replaces the front
// when it is closer to the target than the front is. What is left in the window is the k
// closest.
//
// This reads every node, so it is O(n); LeetCode's follow-up asks for O(k log n) by running
// two iterators outward from the target, which this file does not do.
//
// Time: O(n) -- n calls to next(), each amortized O(1), with constant work for each value.
// Space: O(h + k) -- the iterator's stack and the window.
//
// Siblings: KClosestToTargetInOrder is the same window over a recursive walk.

public final class KClosestToTargetIterator {

	public static List<Integer> kClosest(TreeNode root, int k, double target) {
		BstIterator iter = new BstIterator(root);
		Queue<Integer> queue = new ArrayDeque<>(); // the window, smallest value at the front
		while (iter.hasNext()) {
			int val = iter.next(); // the next value in ascending order
			if (queue.size() < k) {
				queue.offer(val); // TAKE: the window is not full yet
			} else if (abs(queue.peek() - target) > abs(val - target)) {
				queue.poll(); // DROP: the front is farther from the target than this value
				queue.offer(val); // TAKE
			}
		}
		return new ArrayList<>(queue);
	}

	public static void main(String[] args) {
		TreeNode root = newNode(20);
		root.left = newNode(8);
		root.right = newNode(22);
		root.left.left = newNode(4);
		root.left.right = newNode(12);
		root.left.right.left = newNode(10);
		root.left.right.right = newNode(14);
		System.out.println(kClosest(root, 3, 11.5)); // [10, 12, 14]

		TreeNode root2 = newNode(8);
		root2.left = newNode(5);
		root2.right = newNode(10);
		root2.left.left = newNode(2);
		root2.left.right = newNode(6);
		root2.left.left.right = newNode(3);
		root2.right.right = newNode(14);
		System.out.println(kClosest(root2, 4, 7)); // [5, 6, 8, 10]
	}
}
