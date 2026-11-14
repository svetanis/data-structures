package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.concurrent.atomic.AtomicInteger;

// 124. Binary Tree Maximum Path Sum
//
// Input: the root of a binary tree. A path is a chain of nodes joined by edges, visiting each
// node at most once; it may start and end anywhere and need not pass through the root.
// Returns the largest sum of values on any path with at least one node. On LeetCode:
// 1 <= n <= 3 * 10^4, -1000 <= val <= 1000.
//
// The one idea: the best path has a highest node, the one nearest the root; the path goes down
// from it into at most two subtrees, one on each side. So ask each node for one number: the best
// sum of a path that starts at this node and goes DOWN (one way only), or 0 if every such path
// is negative, because a path may simply stop and skip a harmful subtree. At a node, the best
// path whose highest node is this one is value + (best down left) + (best down right); keep the
// largest of those over all nodes. What the node hands up to its parent is only
// value + the better of the two downward sums, since a path through the parent cannot fork.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack (h = height, n on a chain).
//
// Known limit: a chain about 3 * 10^4 nodes deep (the most the constraints allow) overflows the
// default thread stack, because the recursion is as deep as the tree. It only happens on very
// large input.
//
// Siblings: MaxSumPathBetweenAnyTwoNodesGlobal keeps the best in a field instead of an
// AtomicInteger parameter; MaxSumPathBetweenAnyTwoNodesIterative is this walk without recursion.
public final class MaxSumPathBetweenAnyTwoNodes {

	public static int maxSum(TreeNode root) {
		AtomicInteger max = new AtomicInteger(Integer.MIN_VALUE); // BEST: smaller than any sum
		maxSum(root, max);
		return max.get();
	}

	// returns the best sum of a downward path starting at root (at least 0, meaning "skip it");
	// records into max the best path whose highest node is root
	private static int maxSum(TreeNode root, AtomicInteger max) {
		if (root == null) { // BASE: an empty subtree adds nothing
			return 0;
		}
		int left = max(0, maxSum(root.left, max)); // DOWN LEFT: ignore a negative side
		int right = max(0, maxSum(root.right, max)); // DOWN RIGHT: ignore a negative side
		int sum = root.val + left + right; // FORK: the best path with root as its highest node
		max.set(max(max.get(), sum)); // RECORD: the best so far
		return root.val + max(left, right); // RETURN: a parent can use only one side
	}

	public static void main(String[] args) {
		// Max sum is 27 = 3 + 6 + 9 + 0 - 1 + 10
		TreeNode root = newNode(-15);
		root.left = newNode(5);
		root.right = newNode(6);
		root.left.left = newNode(-8);
		root.left.right = newNode(1);
		root.right.left = newNode(3);
		root.right.right = newNode(9);
		root.left.left.left = newNode(2);
		root.left.left.right = newNode(6);
		root.right.right.right = newNode(0);
		root.right.right.right.left = newNode(4);
		root.right.right.right.right = newNode(-1);
		root.right.right.right.right.left = newNode(10);
		inOrder(root); // prints 2 -8 6 5 1 -15 3 6 9 4 0 10 -1
		System.out.println();
		System.out.println(maxSum(root)); // 27

		// Max sum is 42 = 20 + 2 + 10 + 10
		TreeNode root2 = newNode(10);
		root2.left = newNode(2);
		root2.right = newNode(10);
		root2.left.left = newNode(20);
		root2.left.right = newNode(1);
		root2.right.right = newNode(-25);
		root2.right.right.left = newNode(3);
		root2.right.right.right = newNode(4);
		System.out.println(maxSum(root2)); // 42

		// LeetCode example 1
		TreeNode root3 = newNode(1);
		root3.left = newNode(2);
		root3.right = newNode(3);
		System.out.println(maxSum(root3)); // 6

		// LeetCode example 2
		TreeNode root4 = newNode(-10);
		root4.left = newNode(9);
		root4.right = newNode(20);
		root4.right.left = newNode(15);
		root4.right.right = newNode(7);
		System.out.println(maxSum(root4)); // 42
	}
}
