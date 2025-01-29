package com.svetanis.datastructures.tree.binary.bt.serialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 652. Find Duplicate Subtrees

public final class DuplicateSubtrees {
	// Time Complexity: O(n^2)

	public static List<Node> duplicateSubtrees(Node root) {
		List<Node> list = new ArrayList<>();
		Map<String, Integer> map = new HashMap<>();
		dfs(root, list, map);
		return list;
	}

	private static String dfs(Node root, List<Node> list, Map<String, Integer> map) {
		if (root == null) {
			return "#";
		}
		String key = root.data + "," + dfs(root.left, list, map) + "," + dfs(root.right, list, map);
		map.put(key, map.getOrDefault(key, 0) + 1);
		if (map.get(key) == 2) {
			list.add(root);
		}
		return key;
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(4);
		root.right.left = new Node(2);
		root.right.right = new Node(4);
		root.right.left.left = new Node(4);
		System.out.println(duplicateSubtrees(root)); // [2,4], [4]

		Node root1 = new Node(2);
		root1.left = new Node(2);
		root1.right = new Node(2);
		root1.left.left = new Node(3);
		root1.right.left = new Node(3);
		System.out.println(duplicateSubtrees(root1)); // [2,3], [3]

		Node root2 = new Node(2);
		root2.left = new Node(1);
		root2.right = new Node(1);
		System.out.println(duplicateSubtrees(root2)); // [1]
	}
}
