package com.svetanis.datastructures.tree.binary.bt.vertical;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 987. Vertical Order Traversal of a Binary Tree
//
// Input: the root of a binary tree. On LeetCode: 1 <= number of nodes <= 1000,
// 0 <= val <= 1000. Give the root position (row 0, column 0); a node at (row, col) has its left
// child at (row + 1, col - 1) and its right child at (row + 1, col + 1). Returns the node values
// column by column, leftmost column first; inside a column, top to bottom, and nodes sharing the
// same row and column in increasing order of value.
//
// The one idea: one entry is a node's value together with its row, and a column number is the
// key it is filed under. A level-by-level walk (queue) computes row and column for every node and
// files (row, value) under its column in a map that keeps columns sorted. Then each column is
// sorted by row first and value second, which is exactly the tie rule of the statement.
//
// Time: O(n log n) -- the walk is O(n), each column is then sorted; the sorts total O(n log n).
// Space: O(n) -- the queue and the filed entries.
//
// Siblings: VerticalOrderSubmit is the 314 version (a tie is broken by left-to-right, so it needs
// no sort); VerticalOrderIterative and VerticalOrderRecursive build the columns without a sort.

public final class VerticalOrder987 {

	public static List<List<Integer>> verticalOrder(TreeNode root) {
		if (root == null) {
			return new ArrayList<>();
		}
		Map<Integer, List<Item>> map = bfs(root);
		return flatten(map);
	}

	private static List<List<Integer>> flatten(Map<Integer, List<Item>> map) {
		List<List<Integer>> lists = new ArrayList<>();
		for (int key : map.keySet()) { // WALK: columns, leftmost first (TreeMap order)
			List<Item> items = new ArrayList<>(map.get(key));
			Collections.sort(items); // SORT: row first, then value
			List<Integer> list = new ArrayList<>();
			for (Item item : items) {
				list.add(item.val);
			}
			lists.add(list);
		}
		return lists;
	}

	private static Map<Integer, List<Item>> bfs(TreeNode root) {
		Map<Integer, List<Item>> map = new TreeMap<>();
		Queue<Pair> queue = new ArrayDeque<>();
		queue.offer(new Pair(root, 0, 0));
		while (!queue.isEmpty()) {
			Pair pair = queue.poll();
			int dist = pair.dist;
			int level = pair.level;
			TreeNode node = pair.node;
			List<Item> list = map.getOrDefault(dist, new ArrayList<>());
			list.add(new Item(level, node.val)); // FILE the node under its column, with its row
			map.put(dist, list);
			// in a single line:
			// map.computeIfAbsent(dist, k -> new ArrayList<>()).add(node.val);
			if (node.left != null) {
				queue.offer(new Pair(node.left, level + 1, dist - 1)); // PUSH: one row down, one column left
			}
			if (node.right != null) {
				queue.offer(new Pair(node.right, level + 1, dist + 1)); // PUSH: one row down, one column right
			}
		}
		return map;
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
		TreeNode ex3 = newNode(1);
		ex3.left = newNode(2);
		ex3.right = newNode(3);
		ex3.left.left = newNode(4);
		ex3.left.right = newNode(6);
		ex3.right.left = newNode(5);
		ex3.right.right = newNode(7);
		System.out.println(verticalOrder(ex3)); // [[4], [2], [1, 5, 6], [3], [7]]
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

	private static class Item implements Comparable<Item> {
		private int val;
		private int row;

		public Item(int row, int val) {
			this.row = row;
			this.val = val;
		}

		@Override
		public int compareTo(Item other) {
			if (this.row == other.row) {
				return Integer.compare(this.val, other.val);
			}
			return Integer.compare(this.row, other.row);
		}
	}

	private static class Pair {
		private TreeNode node;
		private int level;
		private int dist;

		public Pair(TreeNode node, int level, int dist) {
			this.node = node;
			this.level = level;
			this.dist = dist;
		}
	}
}
