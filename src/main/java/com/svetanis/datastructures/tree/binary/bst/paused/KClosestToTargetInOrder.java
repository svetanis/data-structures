package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 272. Closest Binary Search Tree Value II
//
// Input: the root of a binary search tree, a target (a double) and an integer k. Returns the k
// values in the tree closest to the target, in ascending order here (LeetCode accepts any
// order). On LeetCode: 1 <= k <= n <= 10^4 nodes, 0 <= node.val <= 10^9, the k closest values
// are unique, -10^9 <= target <= 10^9.
//
// The one idea: the in-order walk gives the values in ascending order, and in that order the
// k closest values to the target form one unbroken run. Keep a window of the last k values
// that were good enough, oldest (smallest) at the front. While the window has fewer than k
// values, add every value. After that, a new value (larger than all in the window) replaces
// the front when it is closer to the target than the front is; if it is not closer, the walk
// has already passed the target and every later value is farther still. What is left in the
// window is the k closest.
//
// This reads every node, so it is O(n); LeetCode's follow-up asks for O(k log n) by running
// two iterators outward from the target, which this file does not do.
//
// Known limit: the recursion is as deep as the tree, so a one-sided chain of about five
// thousand nodes or more overflows the default stack (LC allows 10^4 nodes).
// KClosestToTargetInOrderIterative is the same walk with an explicit stack.
//
// Time: O(n) -- every node is visited once, with constant work each.
// Space: O(h + k) -- the recursion stack and the window.
//
// Siblings: KClosestToTargetInOrderIterative is this walk with an explicit stack;
// KClosestToTargetIterator takes the values from a BstIterator instead of recursing.

public final class KClosestToTargetInOrder {

	public static List<Integer> kClosest(TreeNode root, int k, double target) {
		Queue<Integer> queue = new ArrayDeque<>(); // the window, smallest value at the front
		dfs(root, k, target, queue);
		return new ArrayList<>(queue);
	}

	private static void dfs(TreeNode root, int k, double target, Queue<Integer> queue) {
		if (root == null) { // BASE: empty subtree
			return;
		}
		dfs(root.left, k, target, queue); // WALK: smaller values first
		if (queue.size() < k) {
			queue.offer(root.val); // TAKE: the window is not full yet
		} else if (Math.abs(queue.peek() - target) > Math.abs(root.val - target)) {
			queue.poll(); // DROP: the front is farther from the target than this value
			queue.offer(root.val); // TAKE
		}
		dfs(root.right, k, target, queue); // WALK: larger values last
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
