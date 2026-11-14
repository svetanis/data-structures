package com.svetanis.datastructures.tree.binary.bt.vertical;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 314. Binary Tree Vertical Order Traversal
//
// Input: the root of a binary tree. On LeetCode: 0 <= number of nodes <= 100,
// -100 <= val <= 100. Give the root column 0; a left child is one column left of its parent and
// a right child one column right. Returns the node values column by column, leftmost column
// first; inside a column, top to bottom, and left to right on the same row.
//
// The one idea: a queue walks the tree one row at a time, left to right inside a row, and
// remembers each node's column. Appending every node to its column's list as it is taken off the
// queue puts each list in exactly the required order, so nothing needs sorting. The columns run
// from the smallest to the largest column number seen, with no gaps, so reading the map from the
// minimum to the maximum gives them left to right.
//
// Time: O(n) -- each node is queued once; the final scan visits at most n columns.
// Space: O(n) -- the queue and the map.
//
// Siblings: VerticalOrder987 is the 987 version, where nodes sharing a row and column are sorted
// by value; VerticalOrderIterative is the same walk with a TreeMap, which keeps columns sorted
// by itself.

public final class VerticalOrderSubmit {

	public static List<List<Integer>> verticalOrder(TreeNode root) {
		if (root == null) {
			return new ArrayList<>();
		}
		Map<Integer, List<Integer>> map = new HashMap<>();
		Queue<Pair> queue = new ArrayDeque<>();
		queue.offer(new Pair(root, 0));
		int maxDist = 0, minDist = 0;
		while (!queue.isEmpty()) {
			Pair pair = queue.poll();
			int dist = pair.dist;
			TreeNode node = pair.node;
			map.computeIfAbsent(dist, k -> new ArrayList<>()).add(node.val); // FILE under its column
			if (node.left != null) {
				queue.offer(new Pair(node.left, dist - 1)); // PUSH: one column left
			}
			if (node.right != null) {
				queue.offer(new Pair(node.right, dist + 1)); // PUSH: one column right
			}
			maxDist = Math.max(maxDist, dist); // TRACK: the column range seen so far
			minDist = Math.min(minDist, dist);
		}
		List<List<Integer>> lists = new ArrayList<>();
		for (int i = minDist; i <= maxDist; i++) { // WALK: columns, left to right
			if (map.containsKey(i)) {
				lists.add(map.get(i));
			}
		}
		return lists;
	}

	public static void main(String[] args) {
		TreeNode root1 = tree1();
		System.out.println(verticalOrder(root1));
		// [[4], [2], [1, 5, 6], [3, 8, 10], [7, 11], [9, 12]]
		TreeNode root2 = tree2();
		System.out.println(verticalOrder(root2));
		// [[2, 7], [1, 5, 9], [3, 8], [6, 10]]
		TreeNode ex1 = newNode(3);
		ex1.left = newNode(9);
		ex1.right = newNode(20);
		ex1.right.left = newNode(15);
		ex1.right.right = newNode(7);
		System.out.println(verticalOrder(ex1)); // [[9], [3, 15], [20], [7]]
		System.out.println(verticalOrder(null)); // []
	}

	private static TreeNode tree2() {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.right.left = newNode(5);
		root.right.right = newNode(6);
		root.right.left.left = newNode(7);
		root.right.left.right = newNode(8);
		root.right.left.right.left = newNode(9);
		root.right.left.right.right = newNode(10);
		return root;
	}

	private static TreeNode tree1() {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.left = newNode(6);
		root.right.right = newNode(7);
		root.right.left.right = newNode(8);
		root.right.right.right = newNode(9);
		root.right.right.left = newNode(10);
		root.right.right.left.right = newNode(11);
		root.right.right.left.right.right = newNode(12);
		return root;
	}

	private static class Pair {

		private TreeNode node;
		private int dist;

		public Pair(TreeNode node, int dist) {
			this.node = node;
			this.dist = dist;
		}
	}

}
