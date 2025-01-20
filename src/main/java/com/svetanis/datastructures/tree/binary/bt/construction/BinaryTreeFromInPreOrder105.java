package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;

import java.util.HashMap;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 105. Construct Binary Tree from Preorder and Inorder

// given two arrays representing
// the preorder and inorder traversal
// of the same binary tree consisting
// of unique values, construct the 
// original tree.

public final class BinaryTreeFromInPreOrder105 {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static Node construct(int[] pre, int[] in) {
		Map<Integer, Integer> map = indexMap(in);
		return dfs(0, 0, in.length, pre, in, map);
	}

	private static Node dfs(int preStart, int inStart, int size, int[] pre, int[] in, Map<Integer, Integer> map) {
		if (size <= 0) {
			return null;
		}
		int root = pre[preStart];
		int rootIndex = map.get(root);
		int leftSize = rootIndex - inStart; // size of left subtree
		Node left = dfs(preStart + 1, inStart, leftSize, pre, in, map);
		Node right = dfs(preStart + leftSize + 1, rootIndex + 1, size - 1 - leftSize, pre, in, map);
		return new Node(root, left, right);
	}

	private static Map<Integer, Integer> indexMap(int[] a) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < a.length; i++) {
			map.put(a[i], i);
		}
		return map;
	}

	public static void main(String[] args) {
		int[] in = { 3, 9, 20, 15, 7 };
		int[] pre = { 9, 3, 15, 20, 7 };
		Node root = construct(pre, in);
		inOrder(root); // 3 9 20 15 7
		System.out.println();
	}
}
