package com.svetanis.datastructures.tree.binary.bt.vertical;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 314. Binary Tree Vertical Order Traversal

// given a binary tree, perform vertical 
// traversal of it: print nodes of a binary tree
// in vertical order by assuming that the left and
// right child of a node makes 45 degree angle with 
// the parent

public final class VerticalOrderSubmit {
	// Time Complexity: O(n)
	// Aux Space: O(n)

	public static List<List<Integer>> verticalOrder(Node root) {
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
			Node node = pair.node;
			map.computeIfAbsent(dist, k -> new ArrayList<>()).add(node.data);
			if (node.left != null) {
				queue.offer(new Pair(node.left, dist - 1));
			}
			if (node.right != null) {
				queue.offer(new Pair(node.right, dist + 1));
			}
			maxDist = Math.max(maxDist, dist);
			minDist = Math.min(minDist, dist);
		}
		List<List<Integer>> lists = new ArrayList<>();
		for (int i = minDist; i <= maxDist; i++) {
			if (map.containsKey(i)) {
				lists.add(map.get(i));
			}
		}
		return lists;
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

	private static class Pair {

		private Node node;
		private int dist;

		public Pair(Node node, int dist) {
			this.node = node;
			this.dist = dist;
		}
	}

}
