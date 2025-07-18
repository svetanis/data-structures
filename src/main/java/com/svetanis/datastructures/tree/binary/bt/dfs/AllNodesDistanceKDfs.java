package com.svetanis.datastructures.tree.binary.bt.dfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 863. All Nodes Distance K in Binary Tree

public final class AllNodesDistanceK {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static List<Integer> nodesDistK(Node root, Node target, int k) {
		Map<Node, Node> map = new HashMap<>();
		parents(root, null, map);
		Set<Integer> visited = new HashSet<>();
		List<Integer> list = new ArrayList<>();
		dfs(target, k, visited, list, map);
		return list;
	}

	private static void dfs(Node node, int k, Set<Integer> visited, List<Integer> list, Map<Node, Node> map) {
		if (node == null || visited.contains(node.data)) {
			return;
		}
		visited.add(node.data);
		if (k == 0) {
			list.add(node.data);
			return;
		}
		dfs(node.left, k - 1, visited, list, map);
		dfs(node.right, k - 1, visited, list, map);
		dfs(map.get(node), k - 1, visited, list, map);
	}

	private static void parents(Node root, Node parent, Map<Node, Node> map) {
		if (root == null) {
			return;
		}
		map.put(root, parent);
		parents(root.left, root, map);
		parents(root.right, root, map);
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
