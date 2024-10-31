package com.svetanis.datastructures.tree.binary.bt.dfs;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1110. Delete Nodes And Return Forest

public final class DeleteNodes {
	// Time Complexity: O(n)

	public static List<Node> forest(Node root, int[] delete) {
		List<Node> list = new ArrayList<>();
		Set<Integer> set = Arrays.stream(delete).boxed().collect(Collectors.toSet());
		if (isNull(root)) {
			return list;
		}
		root = dfs(root, true, set, list);
		return list;
	}

	private static Node dfs(Node root, boolean isRoot, Set<Integer> set, List<Node> list) {
		if (isNull(root)) {
			return null;
		}
		boolean deleted = set.contains(root.data);
		if (isRoot && !deleted) {
			list.add(root);
		}
		root.left = dfs(root.left, deleted, set, list);
		root.right = dfs(root.right, deleted, set, list);
		return deleted ? null : root;
	}

	public static void main(String[] args) {
		int[] a = { 3, 5 };
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.left = newNode(6);
		root.right.right = newNode(7);
		System.out.println(forest(root, a)); // [1, 2, null, 4], [6], [7]
	}
}
