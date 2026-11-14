package com.svetanis.datastructures.tree.binary.bt.subtree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.IdentityHashMap;
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
// children's subtrees, so the children's sums must be finished first. A walk that takes each node
// before its children lists every parent before its children; going through that list backwards
// finishes every child before its parent. Each sum is counted in a map, sum -> how many nodes have
// it, and the answer is every key whose count equals the highest count.
//
// Time: O(n) -- one walk forwards, one pass backwards, then one pass over at most n keys.
// Space: O(n) -- the walk order, one sum per node, and the map of up to n distinct sums.
//
// Siblings: MostFrequentSubtree -- the same counting by recursion; it overflows the default stack on
// a chain of about 10^4 nodes, this one does not.

public final class MostFrequentSubtreeIterative {

	public int[] mfs(TreeNode root) {
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
		Map<TreeNode, Integer> sums = new IdentityHashMap<>(); // SUM: each node's subtree sum
		Map<Integer, Integer> map = new HashMap<>(); // MAP: subtree sum -> number of nodes with it
		int max = Integer.MIN_VALUE; // the highest count of any sum so far
		for (int i = order.size() - 1; i >= 0; i--) { // FINISH: children before their parent
			TreeNode node = order.get(i);
			int left = node.left == null ? 0 : sums.get(node.left);
			int right = node.right == null ? 0 : sums.get(node.right);
			int sum = node.val + left + right;
			sums.put(node, sum);
			map.put(sum, map.getOrDefault(sum, 0) + 1); // COUNT: one more node with this sum
			max = Math.max(max, map.get(sum)); // TRACK: the highest count so far
		}
		List<Integer> list = new ArrayList<>();
		for (int key : map.keySet()) {
			if (map.get(key) == max) { // PICK: every sum that reaches the highest count
				list.add(key);
			}
		}
		return list.stream().mapToInt(i -> i).toArray();
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(5);
		root.left = new TreeNode(2);
		root.right = new TreeNode(-3);
		MostFrequentSubtreeIterative mfs = new MostFrequentSubtreeIterative();
		int[] a = mfs.mfs(root);
		System.out.println(Arrays.toString(a)); // [2, -3, 4] -- any order

		TreeNode root3 = new TreeNode(5);
		root3.left = new TreeNode(2);
		root3.right = new TreeNode(-5);
		MostFrequentSubtreeIterative mfs2 = new MostFrequentSubtreeIterative();
		int[] a1 = mfs2.mfs(root3);
		System.out.println(Arrays.toString(a1)); // [2]
	}
}
