package com.svetanis.datastructures.tree.binary.bt.dfs;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1110. Delete Nodes And Return Forest

public final class DeleteNodesPostOrder {
	// Time Complexity: O(n)

	public static List<Node> forest(Node root, int[] delete) {
		List<Node> list = new ArrayList<>();
		Set<Integer> set = Arrays.stream(delete)
											.boxed().collect(Collectors.toSet());
		if (isNull(root)) {
			return list;
		}
		if (!set.contains(root.data)) {
			list.add(root);
		}
		dfs(root, set, list);
		return list;
	}

	private static Node dfs(Node root, Set<Integer> set, List<Node> list) {
		if (isNull(root)) {
			return null;
		}
		root.left = dfs(root.left, set, list);
		root.right = dfs(root.right, set, list);
		if (set.contains(root.data)) {
			if (isNotNull(root.left)) {
				list.add(root.left);
			}
			if (isNotNull(root.right)) {
				list.add(root.right);
			}
			return null;
		}
		return root;
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
