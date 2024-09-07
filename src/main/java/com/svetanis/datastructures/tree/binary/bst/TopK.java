package com.svetanis.datastructures.tree.binary.bst;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and an integer k
// find the k largest elements 

// reverse in-order traversal
// right, root, left
// results in nodes sorted in descending order
// take first k nodes

public final class TopK {
	// Time Complexity: O(n)

	public static ImmutableList<Node> topK(Node node, int k) {
		List<Node> list = newArrayList();
		topK(node, k, list);
		return newList(list);
	}

	private static void topK(Node root, int k, List<Node> list) {
		// perform reverse in-order traversal
		if (isNotNull(root) && list.size() < k) {
			topK(root.right, k, list);
			if (list.size() < k) {
				list.add(root);
				topK(root.left, k, list);
			}
		}
	}

	public static void main(String[] args) {
		Node root = newNode(3);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(1);
		root.right.left = newNode(4);
		root.right.right = newNode(6);
		print(topK(root, 4));
	}
}