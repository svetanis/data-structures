package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 102. Binary Tree Level Order Traversal

public final class LotLineByLineDfs {
	// Time complexity: O(n)

	public static List<List<Integer>> traverse(Node root) {
		List<List<Integer>> lists = new ArrayList<>();
		dfs(root, 0, lists);
		return lists;
	}

	private static void dfs(Node node, int depth, List<List<Integer>> list) {
		if (node == null) {
			return;
		}
		if (depth == list.size()) {
			list.add(new ArrayList<>());
		}
		list.get(depth).add(node.data);
		dfs(node.left, depth + 1, list);
		dfs(node.right, depth + 1, list);
	}

	public static void main(String[] args) {
		Node root = newNode(3);
		root.left = newNode(9);
		root.right = newNode(20);
		root.right.left = newNode(15);
		root.right.right = newNode(7);
		System.out.println(traverse(root));
	}
}
