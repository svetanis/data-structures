package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static java.lang.Integer.MIN_VALUE;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// Root-to-Leaf Path With the Maximum Sum -- not a LeetCode problem
//
// Input: the root of a binary tree. Returns the values of the root-to-leaf path with the largest
// sum, root first; if several paths tie, the leftmost one. Returns an empty list for an empty
// tree. Values may be negative. Assumes the sums fit in an int.
//
// The one idea: walk down depth first keeping two things: the values from the root to the
// current node in a deque, and the sum of those values as a number handed down to each child. At
// a leaf the number is the sum of one complete root-to-leaf path, so it is compared with the best
// so far with no adding up; if it is strictly larger, the deque is copied as the new best.
// Leaving a node removes its value from the deque again.
//
// Time: O(n + L * h) -- each node is visited once; each of the L leaves may copy a path of up to
//   h values (h = height).
// Space: O(h) -- the deque and the recursion stack; the best path is extra.
//
// Siblings: RootToLeafMaxSumPathRecursive adds the deque up at each leaf instead of handing the
// sum down; RootToLeafMaxSumPathIterative reaches the same answer level by level with a parent
// map; RootToLeafMaxSumPath finds the best sum first, then a path with that sum.
public final class RootToLeafMaxSumPathRecursive2 {

	public static List<Integer> maxSumPath(TreeNode root) {
		Deque<Integer> dq = new ArrayDeque<>(); // PATH: values from the root to the current node
		List<Integer> path = new ArrayList<>(); // BEST: the best complete path so far
		AtomicInteger max = new AtomicInteger(MIN_VALUE); // SUM: the best sum so far
		paths(root, 0, max, dq, path);
		return path;
	}

	private static void paths(
			TreeNode root, int above, AtomicInteger max, Deque<Integer> dq, List<Integer> path) {
		if (isNull(root)) { // BASE: nothing below
			return;
		}
		dq.addLast(root.val); // TAKE: the current node joins the path
		int sum = above + root.val; // SUM: the path from the root through this node
		if (isLeaf(root)) { // LEAF: sum is the sum of one complete root-to-leaf path
			if (sum > max.get()) { // RECORD: a strictly larger sum, so ties keep the leftmost
				max.set(sum);
				path.clear();
				path.addAll(dq);
			}
		} else {
			paths(root.left, sum, max, dq, path); // WALK: left subtree
			paths(root.right, sum, max, dq, path); // WALK: right subtree
		}
		dq.removeLast(); // UNDO: leave the node
	}

	public static void main(String[] args) {
		// 10->8->3
		// 10->8->5
		// 10->2->2
		TreeNode root = newNode(10);
		root.left = newNode(8);
		root.right = newNode(2);
		root.left.left = newNode(3);
		root.left.right = newNode(5);
		root.right.left = newNode(2);
		inOrder(root); // prints 3 8 5 10 2 2
		System.out.println();

		// print Root-to-leaf max sum path
		System.out.println("Root-to-leaf max sum path: ");
		System.out.println(maxSumPath(root)); // [10, 8, 5]

		// the leaf holding the largest value (5) is not on the best path
		TreeNode root2 = newNode(-1);
		root2.left = newNode(-9);
		root2.right = newNode(1);
		root2.left.left = newNode(5);
		root2.right.left = newNode(0);
		System.out.println(maxSumPath(root2)); // [-1, 1, 0]
	}
}
