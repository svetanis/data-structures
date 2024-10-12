package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given two arrays representing
// the preorder and inorder traversal
// of the same binary tree consisting
// of unique values, construct the 
// original tree.

public final class BinaryTreeFromInPreOrderSubmit {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static Node construct(List<Integer> pre, List<Integer> in) {
		Map<Integer, Integer> map = indexMap(in);
		return construct(0, 0, in.size(), pre, in, map);
	}

	private static Node construct(int preStart, int inStart, int size, List<Integer> pre, List<Integer> in,
			Map<Integer, Integer> map) {
		if (size <= 0) {
			return null;
		}

		int root = pre.get(preStart);
		int rootIndex = map.get(root);
		int leftSize = rootIndex - inStart; // size of left subtree

		Node left = construct(preStart + 1, inStart, leftSize, pre, in, map);
		Node right = construct(preStart + leftSize + 1, rootIndex + 1, size - 1 - leftSize, pre, in, map);
		return new Node(root, left, right);
	}

	private static Map<Integer, Integer> indexMap(List<Integer> list) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i), i);
		}
		return map;
	}

	public static void main(String[] args) {
		List<Integer> in = asList(4, 2, 5, 1, 3, 6);
		List<Integer> pre = asList(1, 2, 4, 5, 3, 6);
		Node root = construct(pre, in);
		inOrder(root); // 4 2 5 1 3 6
		System.out.println();
	}
}
