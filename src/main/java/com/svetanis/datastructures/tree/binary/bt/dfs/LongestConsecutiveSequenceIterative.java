package com.svetanis.datastructures.tree.binary.bt.dfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 298. Binary Tree Longest Consecutive Sequence
//
// Input: root of a binary tree. A consecutive path goes from a node down to one of its
// descendants, and each value is exactly 1 more than the value before it. Returns the length, in
// nodes, of the longest such path; it may start at any node. On LeetCode: 1 <= number of nodes
// <= 3 * 10^4, -3 * 10^4 <= node.val <= 3 * 10^4.
//
// The one idea: for each node, the longest consecutive path that starts there and goes down is 1,
// plus the same number for a child whose value is node.val + 1, taking the longer child. A
// node's number needs its children's numbers, so compute them children first. This file lists
// the nodes parents-before-children with an explicit stack, then walks that list backwards, so
// every child is done before its parent -- the recursion of LongestConsecutiveSequence without
// the call stack, which a tree that is one long chain would overflow.
//
// Time: O(n) -- every node is listed once and computed once.
// Space: O(n) -- the list and the map of lengths.
//
// Siblings: LongestConsecutiveSequence is the recursive version (and names this file for its
// stack limit); LongestConsecutiveSequenceLen carries the run length down instead of returning
// it up.

public final class LongestConsecutiveSequenceIterative {

	public int longestConsecutive(TreeNode root) {
		if (root == null) {
			return 0;
		}
		Map<TreeNode, Integer> len = new IdentityHashMap<>(); // node -> longest path starting there, going down
		int maxLen = 0;
		List<TreeNode> order = parentsFirst(root);
		for (int i = order.size() - 1; i >= 0; i--) { // WALK: backwards, so children come before parents
			TreeNode node = order.get(i);
			int best = Math.max(run(node, node.left, len), run(node, node.right, len));
			len.put(node, best);
			maxLen = Math.max(maxLen, best); // RECORD: the longest path starting here
		}
		return maxLen;
	}

	private int run(TreeNode node, TreeNode child, Map<TreeNode, Integer> len) {
		if (child != null && child.val - node.val == 1) {
			return 1 + len.get(child); // EXTEND: the child continues the run
		}
		return 1; // RESTART: no child, or the child breaks the run
	}

	private List<TreeNode> parentsFirst(TreeNode root) {
		List<TreeNode> order = new ArrayList<>();
		Deque<TreeNode> stack = new ArrayDeque<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			TreeNode node = stack.pop();
			order.add(node); // LIST: a node is listed before its children
			if (node.left != null) {
				stack.push(node.left);
			}
			if (node.right != null) {
				stack.push(node.right);
			}
		}
		return order;
	}

	public static void main(String[] args) {
		LongestConsecutiveSequenceIterative lcs = new LongestConsecutiveSequenceIterative();
		TreeNode root = new TreeNode(1);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(2);
		root.right.right = new TreeNode(4);
		root.right.right.right = new TreeNode(5);
		System.out.println(lcs.longestConsecutive(root)); // 3

		TreeNode root1 = new TreeNode(2);
		root1.right = new TreeNode(3);
		root1.right.left = new TreeNode(2);
		root1.right.left.left = new TreeNode(1);
		System.out.println(lcs.longestConsecutive(root1)); // 2
	}
}
