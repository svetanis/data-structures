package com.svetanis.datastructures.tree.binary.bt.construction;

import java.util.HashMap;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 106. Construct Binary Tree from Inorder and Postorder Traversal

public final class BinaryTreeFromInPostOrder106 {

	public static Node construct(int[] in, int[] post) {
		Map<Integer, Integer> map = indexMap(in);
		return dfs(in, 0, post, 0, in.length, map);
	}

	private static Node dfs(int[] in, int inStart, int[] post, int postStart, int len, Map<Integer, Integer> map) {
		if (len <= 0) {
			return null;
		}
		// last node in the postorder segment is the current root
		Node node = new Node(post[postStart + len - 1]);
		int rootIndex = map.get(node.data); // root index in inOrder array
		int leftSize = rootIndex - inStart; // size of left subtree
		node.left = dfs(in, inStart, post, postStart, leftSize, map);
		node.right = dfs(in, rootIndex + 1, post, postStart + leftSize, len - leftSize - 1, map);
		return node;
	}

	private static Map<Integer, Integer> indexMap(int[] a) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < a.length; i++) {
			map.put(a[i], i);
		}
		return map;
	}

	public static void main(String[] args) {
		int[] in = { 9, 3, 15, 20, 7 };
		int[] post = { 9, 15, 7, 20, 3 };
		Node root = construct(in, post);
		Nodes.preOrder(root); // 3 9 20 15 7
		System.out.println();
	}
}
