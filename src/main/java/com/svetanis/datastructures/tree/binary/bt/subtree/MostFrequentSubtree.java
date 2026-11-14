package com.svetanis.datastructures.tree.binary.bt.subtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 508. Most Frequent Subtree Sum
//
// Input: root of a binary tree. The subtree sum of a node is the sum of the values of the node
// and everything below it. Returns the subtree sum(s) that occur most often among all nodes, in
// any order; on a tie, all of them. On LeetCode: 1 <= number of nodes <= 10^4,
// -10^5 <= node value <= 10^5 (so a sum fits in an int: at most 10^9 in size).
//
// The one idea: one node has exactly one subtree sum, and it is its value plus the sums of its two
// children's subtrees. So a post-order walk (children first) computes every node's sum from the
// two sums just returned, in one pass. Each sum is counted in a map, sum -> how many nodes have
// it, and the highest count seen so far is kept; the answer is every key whose count equals that
// highest count.
//
// Time: O(n) -- one visit and one map update per node, then one pass over at most n keys.
// Space: O(n) -- the map holds up to n distinct sums; the recursion is as deep as the tree.
//
// Known limit: the recursion goes one call deeper per level, so a chain of about 10^4 nodes (each
// node the only child of the one before) overflows the default Java stack. It happens only on a
// very deep input; MostFrequentSubtreeIterative is the same method with an explicit stack.
//
// Siblings: MostFrequentSubtreeIterative -- the same counting without recursion.

public final class MostFrequentSubtree {

	private int max = Integer.MIN_VALUE; // the highest count of any sum so far

	public int[] mfs(TreeNode root) {
		max = Integer.MIN_VALUE; // RESET: a second call on this object starts from zero counts
		List<Integer> list = new ArrayList<>();
		Map<Integer, Integer> map = new HashMap<>(); // MAP: subtree sum -> number of nodes with it
		dfs(root, map);
		for (int key : map.keySet()) {
			if (map.get(key) == max) { // PICK: every sum that reaches the highest count
				list.add(key);
			}
		}
		return list.stream().mapToInt(i -> i).toArray();
	}

	private int dfs(TreeNode root, Map<Integer, Integer> map) {
		if (root == null) { // BASE: an empty subtree sums to 0
			return 0;
		}
		int sum = root.val + dfs(root.left, map) + dfs(root.right, map); // SUM: children first
		map.put(sum, map.getOrDefault(sum, 0) + 1); // COUNT: one more node with this sum
		max = Math.max(max, map.get(sum)); // TRACK: the highest count so far
		return sum;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(5);
		root.left = new TreeNode(2);
		root.right = new TreeNode(-3);
		MostFrequentSubtree mfs = new MostFrequentSubtree();
		int[] a = mfs.mfs(root);
		System.out.println(Arrays.toString(a)); // [2, -3, 4] -- any order

		TreeNode root3 = new TreeNode(5);
		root3.left = new TreeNode(2);
		root3.right = new TreeNode(-5);
		MostFrequentSubtree mfs2 = new MostFrequentSubtree();
		int[] a1 = mfs2.mfs(root3);
		System.out.println(Arrays.toString(a1)); // [2]
	}
}
