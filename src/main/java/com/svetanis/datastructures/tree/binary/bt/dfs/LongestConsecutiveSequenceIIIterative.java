package com.svetanis.datastructures.tree.binary.bt.dfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 549. Binary Tree Longest Consecutive Sequence II
//
// Input: root of a binary tree. A consecutive path is any path in the tree -- it may go up and
// then down through a common ancestor -- whose values go up by 1 at every step or down by 1 at
// every step. Returns the length, in nodes, of the longest one. On LeetCode:
// 1 <= number of nodes <= 3 * 10^4, -3 * 10^4 <= node.val <= 3 * 10^4.
//
// The one idea: for each node keep two numbers about the chains that start at the node and go
// down: inc, the longest chain where each value is one smaller than the value above it, and dec,
// the longest where each value is one larger. A node alone is 1 for both. A child extends inc
// when child.val + 1 == node.val and dec when child.val - 1 == node.val; with two children, take
// the longer. A path that bends at this node joins an inc chain going down one side with a dec
// chain going down the other side; they share the node, so the path has inc + dec - 1 nodes.
// A node's numbers need its children's, so this file lists the nodes parents-before-children
// with an explicit stack and walks that list backwards -- the recursion of
// LongestConsecutiveSequenceII without the call stack, which a tree that is one long chain
// would overflow.
//
// Time: O(n) -- every node is listed once and computed once.
// Space: O(n) -- the list and the map of the two numbers per node.
//
// Siblings: LongestConsecutiveSequenceII is the recursive version (and names this file for its
// stack limit).

public final class LongestConsecutiveSequenceIIIterative {

	public int longestConsecutive(TreeNode root) {
		if (root == null) {
			return 0;
		}
		Map<TreeNode, int[]> chains = new IdentityHashMap<>(); // node -> { inc, dec }
		int maxLen = 0;
		List<TreeNode> order = parentsFirst(root);
		for (int i = order.size() - 1; i >= 0; i--) { // WALK: backwards, so children come before parents
			TreeNode node = order.get(i);
			int inc = 1;
			int dec = 1; // START: the node alone
			if (node.left != null) {
				int[] left = chains.get(node.left);
				if (node.left.val + 1 == node.val) {
					inc = left[0] + 1; // EXTEND: left child is one smaller
				}
				if (node.left.val - 1 == node.val) {
					dec = left[1] + 1; // EXTEND: left child is one larger
				}
			}
			if (node.right != null) {
				int[] right = chains.get(node.right);
				if (node.right.val + 1 == node.val) {
					inc = Math.max(inc, right[0] + 1); // EXTEND: right child is one smaller
				}
				if (node.right.val - 1 == node.val) {
					dec = Math.max(dec, right[1] + 1); // EXTEND: right child is one larger
				}
			}
			maxLen = Math.max(maxLen, inc + dec - 1); // RECORD: the path that bends at this node
			chains.put(node, new int[] { inc, dec });
		}
		return maxLen;
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
		LongestConsecutiveSequenceIIIterative lcs = new LongestConsecutiveSequenceIIIterative();
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		System.out.println(lcs.longestConsecutive(root)); // 2

		TreeNode root1 = new TreeNode(2);
		root1.left = new TreeNode(1);
		root1.right = new TreeNode(3);
		System.out.println(lcs.longestConsecutive(root1)); // 3
	}
}
