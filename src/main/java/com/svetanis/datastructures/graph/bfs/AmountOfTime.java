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

// 2385. Amount of Time for Binary Tree to Be Infected

public final class AmountOfTime {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	private Map<Integer, List<Integer>> map = new HashMap<>();

	public int amountOfTime(Node root, int start) {
		buildGraph(root);
		Set<Integer> visited = new HashSet<>();
		Deque<Integer> dq = new ArrayDeque<>();
		dq.offer(start);
		int time = -1;
		while (!dq.isEmpty()) {
			time++;
			for (int i = dq.size(); i > 0; i--) {
				int node = dq.pollFirst();
				visited.add(node);
				if (map.containsKey(node)) {
					for (int neighbor : map.get(node)) {
						if (!visited.contains(neighbor)) {
							dq.offer(neighbor);
						}
					}
				}
			}
		}
		return time;
	}

	private void buildGraph(Node root) {
		if (root == null) {
			return;
		}
		if (root.left != null) {
			map.computeIfAbsent(root.data, k -> new ArrayList<>()).add(root.left.data);
			map.computeIfAbsent(root.left.data, k -> new ArrayList<>()).add(root.data);
		}
		if (root.right != null) {
			map.computeIfAbsent(root.data, k -> new ArrayList<>()).add(root.right.data);
			map.computeIfAbsent(root.right.data, k -> new ArrayList<>()).add(root.data);
		}
		buildGraph(root.left);
		buildGraph(root.right);
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(5);
		root.right = new Node(3);
		root.left.right = new Node(4);
		root.right.left = new Node(10);
		root.right.right = new Node(6);
		root.left.right.left = new Node(9);
		root.left.right.right = new Node(2);
		AmountOfTime aot = new AmountOfTime();
		System.out.println(aot.amountOfTime(root, 3)); // 4
	}
}
