package com.svetanis.datastructures.tree.binary.bt.dfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 863. All Nodes Distance K in Binary Tree

public final class AllNodesDistanceKBfs {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static List<Integer> nodesDistK(Node root, Node target, int k) {
		Map<Node, List<Node>> map = new HashMap<>();
		graph(root, null, map);
		Set<Integer> visited = new HashSet<>();
		Deque<Node> dq = new ArrayDeque<>();
		dq.offer(target);
		visited.add(target.data);
		int dist = 0;
		while (!dq.isEmpty()) {
			if (dist == k) {
				return kDown(dq);
			}
			int size = dq.size();
			for (int i = 0; i < size; i++) {
				Node node = dq.poll();
				for (Node adj : map.get(node)) {
					if (!visited.contains(adj.data)) {
						visited.add(adj.data);
						dq.offer(adj);
					}
				}
			}
			dist++;
		}
		return new ArrayList<>();
	}

	private static List<Integer> kDown(Deque<Node> dq) {
		List<Integer> list = new ArrayList<>();
		while (!dq.isEmpty()) {
			list.add(dq.poll().data);
		}
		return list;
	}

	private static void graph(Node node, Node parent, Map<Node, List<Node>> map) {
		if (node == null) {
			return;
		}
		map.putIfAbsent(node, new ArrayList<>());
		if (parent != null) {
			map.get(node).add(parent);
			map.get(parent).add(node);
		}
		graph(node.left, node, map);
		graph(node.right, node, map);
	}

	public static void main(String[] args) {
		Node target = new Node(5);
		Node root = new Node(3);
		root.left = target;
		root.right = new Node(1);
		root.left.left = new Node(6);
		root.left.right = new Node(2);
		root.left.right.left = new Node(7);
		root.left.right.right = new Node(4);
		root.right.left = new Node(0);
		root.right.right = new Node(8);
		System.out.println(nodesDistK(root, target, 2));

		Node root2 = new Node(1);
		System.out.println(nodesDistK(root2, root2, 3));
	}
}
