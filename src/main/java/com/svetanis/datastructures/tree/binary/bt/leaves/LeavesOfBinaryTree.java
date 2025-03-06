package com.svetanis.datastructures.tree.binary.bt.leaves;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 366. Find Leaves of Binary Tree

public final class LeavesOfBinaryTree {
	// Time Complexity: O(n)

	public static List<List<Integer>> leaves(Node root) {
		List<List<Integer>> list = new ArrayList<>();
		dfs(root, list);
		return list;
	}

	private static int dfs(Node root, List<List<Integer>> list) {
		if (root == null) {
			return -1;
		}
		int left = dfs(root.left, list);
		int right = dfs(root.right, list);
		int level = 1 + Math.max(left, right);
		while (list.size() <= level) {
			list.add(new ArrayList<>());
		}
		list.get(level).add(root.data);
		return level;
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(4);
		root.left.right = new Node(5);
		System.out.println(leaves(root));
	}
}
