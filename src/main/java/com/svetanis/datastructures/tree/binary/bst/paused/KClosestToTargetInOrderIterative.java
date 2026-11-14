package com.svetanis.datastructures.tree.binary.bst.paused;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
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
// the front when it is closer to the target than the front is. What is left in the window is
// the k closest. The walk is done with an explicit stack of the nodes whose left subtree is
// being explored, so a very tall tree cannot overflow the call stack.
//
// This reads every node, so it is O(n); LeetCode's follow-up asks for O(k log n) by running
// two iterators outward from the target, which this file does not do.
//
// Time: O(n) -- every node is pushed once and popped once, with constant work each.
// Space: O(h + k) -- the stack holds one root-to-node path at most, plus the window.
//
// Siblings: KClosestToTargetInOrder is the same walk, recursive; its call stack overflows on
// a one-sided chain of about five thousand nodes, this one does not.
// KClosestToTargetIterator takes the values from a BstIterator.

public final class KClosestToTargetInOrderIterative {

	public static List<Integer> kClosest(TreeNode root, int k, double target) {
		Queue<Integer> queue = new ArrayDeque<>(); // the window, smallest value at the front
		Deque<TreeNode> stack = new ArrayDeque<>();
		TreeNode curr = root;
		while (!stack.isEmpty() || curr != null) {
			while (curr != null) { // DESCEND: push the path down the left side
				stack.push(curr);
				curr = curr.left;
			}
			curr = stack.pop(); // VISIT: the smallest value not yet visited
			if (queue.size() < k) {
				queue.offer(curr.val); // TAKE: the window is not full yet
			} else if (Math.abs(queue.peek() - target) > Math.abs(curr.val - target)) {
				queue.poll(); // DROP: the front is farther from the target than this value
				queue.offer(curr.val); // TAKE
			}
			curr = curr.right; // WALK: then the larger values
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
