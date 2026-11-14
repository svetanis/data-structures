package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1120. Maximum Average Subtree
//
// Input: the root of a binary tree. A subtree is a node together with everything below it; its
// average is the sum of its values divided by how many nodes it has. Returns the largest average
// over all subtrees (any node may be the top). On LeetCode: 1 <= number of nodes <= 10^4,
// 0 <= Node.val <= 10^5; an answer within 10^-5 of the true value is accepted.
//
// The one idea: keep two numbers for one node's subtree: the sum of its values and its node count.
// A node's pair is its own value plus its children's sums, and 1 plus its children's counts, so the
// children's pairs must be finished first. A walk that takes each node before its children lists
// every parent before its children; going through that list backwards finishes every child before
// its parent. Each node divides its own pair once and keeps the largest average seen. Averages are
// compared as doubles, which is exact enough here: sums are at most 10^9 and counts at most 10^4.
//
// Time: O(n) -- one walk forwards, one pass backwards, constant work per node.
// Space: O(n) -- the walk order and one pair per node.
//
// Siblings: MaxAverageSubtree -- the same pairs by recursion; it overflows the default stack on a
// chain of about 10^4 nodes, this one does not.

public final class MaxAverageSubtreeIterative {

	public double maxAvgSubtree(TreeNode root) {
		List<TreeNode> order = new ArrayList<>(); // ORDER: every parent comes before its children
		Deque<TreeNode> stack = new ArrayDeque<>();
		if (root != null) {
			stack.push(root);
		}
		while (!stack.isEmpty()) {
			TreeNode node = stack.pop();
			order.add(node); // WALK: take the node, then its children
			if (node.left != null) {
				stack.push(node.left);
			}
			if (node.right != null) {
				stack.push(node.right);
			}
		}
		Map<TreeNode, int[]> pairs = new IdentityHashMap<>(); // PAIR: { sum of values, node count } per node
		double maxAverage = 0.0; // BEST: values are never negative, so 0 is a safe start
		for (int i = order.size() - 1; i >= 0; i--) { // FINISH: children before their parent
			TreeNode node = order.get(i);
			int[] left = node.left == null ? new int[] { 0, 0 } : pairs.get(node.left);
			int[] right = node.right == null ? new int[] { 0, 0 } : pairs.get(node.right);
			int sum = node.val + left[0] + right[0]; // SUM: this subtree's values
			int count = 1 + left[1] + right[1]; // COUNT: this subtree's nodes
			maxAverage = Math.max(maxAverage, (double) sum / count); // RECORD: keep the largest
			pairs.put(node, new int[] { sum, count });
		}
		return maxAverage; // RETURN: the largest average
	}

	public static void main(String[] args) {
		TreeNode root = newNode(5);
		root.left = newNode(6);
		root.right = newNode(1);
		root.left.left = newNode(2);
		MaxAverageSubtreeIterative mst = new MaxAverageSubtreeIterative();
		System.out.println(mst.maxAvgSubtree(root)); // 4.0

		TreeNode root1 = newNode(5);
		root1.left = newNode(6);
		root1.right = newNode(1);
		System.out.println(mst.maxAvgSubtree(root1)); // 6.0
	}
}
