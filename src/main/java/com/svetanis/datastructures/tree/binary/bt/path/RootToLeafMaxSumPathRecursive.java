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
// The one idea: walk down depth first keeping the values from the root to the current node in a
// deque. At a leaf the deque IS one complete root-to-leaf path: add it up, and if the sum is
// strictly larger than the best so far, copy the deque as the new best. Leaving a node removes
// its value from the deque again, so the deque always holds exactly the path to the current
// node.
//
// Time: O(n + L * h) -- each node is visited once, and each of the L leaves adds up and may copy
//   a path of up to h values (h = height).
// Space: O(h) -- the deque and the recursion stack; the best path is extra.
//
// Siblings: RootToLeafMaxSumPathRecursive2 carries the running sum down instead of adding the
// deque up at each leaf; RootToLeafMaxSumPathIterative reaches the same answer level by level
// with a parent map; RootToLeafMaxSumPath finds the best sum first, then a path with that sum.
public final class RootToLeafMaxSumPathRecursive {

	public static List<Integer> maxSumPath(TreeNode root) {
		Deque<Integer> dq = new ArrayDeque<>(); // PATH: values from the root to the current node
		List<Integer> path = new ArrayList<>(); // BEST: the best complete path so far
		AtomicInteger max = new AtomicInteger(MIN_VALUE); // SUM: the best sum so far
		paths(root, max, dq, path);
		return path;
	}

	private static void paths(
			TreeNode node, AtomicInteger max, Deque<Integer> dq, List<Integer> path) {
		if (isNull(node)) { // BASE: nothing below
			return;
		}
		dq.addLast(node.val); // TAKE: the current node joins the path
		if (isLeaf(node)) { // LEAF: the deque is a complete root-to-leaf path
			int sum = dq.stream().reduce(0, Integer::sum);
			if (sum > max.intValue()) { // RECORD: a strictly larger sum, so ties keep the leftmost
				max.set(sum);
				path.clear();
				path.addAll(dq);
			}
		} else {
			paths(node.left, max, dq, path); // WALK: left subtree
			paths(node.right, max, dq, path); // WALK: right subtree
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
	}
}
