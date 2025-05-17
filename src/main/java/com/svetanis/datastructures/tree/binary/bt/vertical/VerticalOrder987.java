package com.svetanis.datastructures.tree.binary.bt.vertical;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 987. Vertical Order Traversal of a Binary Tree

public final class VerticalOrder987 {
	// Time Complexity: O(n)
	// Aux Space: O(n)

	public static List<List<Integer>> verticalOrder(Node root) {
		if (root == null) {
			return new ArrayList<>();
		}
		Map<Integer, List<Item>> map = bfs(root);
		return flatten(map);
	}

	private static List<List<Integer>> flatten(Map<Integer, List<Item>> map) {
		List<List<Integer>> lists = new ArrayList<>();
		for (int key : map.keySet()) {
			List<Item> items = new ArrayList<>(map.get(key));
			Collections.sort(items);
			List<Integer> list = new ArrayList<>();
			for (Item item : items) {
				list.add(item.val);
			}
			lists.add(list);
		}
		return lists;
	}

	private static Map<Integer, List<Item>> bfs(Node root) {
		Map<Integer, List<Item>> map = new TreeMap<>();
		Queue<Pair> queue = new ArrayDeque<>();
		queue.offer(new Pair(root, 0, 0));
		while (!queue.isEmpty()) {
			Pair pair = queue.poll();
			int dist = pair.dist;
			int level = pair.level;
			Node node = pair.node;
			List<Item> list = map.getOrDefault(dist, new ArrayList<>());
			list.add(new Item(level, node.data));
			map.put(dist, list);
			// in a single line:
			// map.computeIfAbsent(dist, k -> new ArrayList<>()).add(node.data);
			if (node.left != null) {
				queue.offer(new Pair(node.left, level + 1, dist - 1));
			}
			if (node.right != null) {
				queue.offer(new Pair(node.right, level + 1, dist + 1));
			}
		}
		return map;
	}

	public static void main(String[] args) {
		Node root1 = tree1();
		print(verticalOrder(root1));
		// [4] [2] [1, 5, 6] [3, 8, 10] [7, 11] [9, 12]
		System.out.println();
		Node root2 = tree2();
		print(verticalOrder(root2));
		// [2, 7] [1, 5, 9] [3, 8] [6, 10]
	}

	private static Node tree2() {
		Node root = newNode(1);
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

	private static Node tree1() {
		Node root = newNode(1);
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
				return this.val - other.val;
			}
			return this.row - other.row;
		}
	}

	private static class Pair {
		private Node node;
		private int level;
		private int dist;

		public Pair(Node node, int level, int dist) {
			this.node = node;
			this.level = level;
			this.dist = dist;
		}
	}
}
