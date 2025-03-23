package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 742. Closest Leaf in a Binary Tree

public final class ClosestLeafInBT {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	private Map<Node, List<Node>> map = new HashMap<>();

	public int amountOfTime(Node root, int k) {
		buildGraph(root, null);
		Deque<Node> dq = init(k);
		Set<Integer> visited = new HashSet<>();

		while (!dq.isEmpty()) {
			Node node = dq.poll();
			visited.add(node.data);
			if (node.left == null && node.right == null) {
				return node.data;
			}
			for (Node neighbor : map.get(node)) {
				if (neighbor != null && !visited.contains(neighbor.data)) {
					dq.offer(neighbor);
				}
			}
		}
		return 0;
	}

	private Deque<Node> init(int k) {
		Deque<Node> dq = new ArrayDeque<>();
		for (Node node : map.keySet()) {
			if (node != null && node.data == k) {
				dq.offer(node);
				break;
			}
		}
		return dq;
	}

	private void buildGraph(Node root, Node parent) {
		if (root != null) {
			map.computeIfAbsent(root, k -> new ArrayList<>()).add(parent);
			map.computeIfAbsent(parent, k -> new ArrayList<>()).add(root);
			buildGraph(root.left, root);
			buildGraph(root.right, root);
		}
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(4);
		root.right.left = new Node(5);
		root.right.right = new Node(6);
		root.right.left.left = new Node(7);
		ClosestLeafInBT aot = new ClosestLeafInBT();
		System.out.println(aot.amountOfTime(root, 2)); // 4
	}
}
