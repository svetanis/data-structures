package com.svetanis.datastructures.tree.binary.bt.construction;

import java.util.HashMap;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 106. Construct Binary Tree from Inorder and Postorder Traversal
//
// Input: in -- the in-order values, post -- the post-order values of the same binary tree; all
// values are distinct. On LeetCode: 1 <= n <= 3000, -3000 <= value <= 3000.
// Returns: the root of the tree both sequences describe.
//
// The one idea: post-order lists a node after both of its subtrees, so the last value of any
// post-order slice is the root of that slice's tree. In-order lists the left subtree, then the
// root, then the right subtree, so the root's in-order position cuts the in-order slice in two:
// the part before it is the left subtree, the part after it the right subtree. The size of the left
// part says how many post-order values (right after the slice start) belong to the left subtree;
// the rest, up to the last value, belong to the right subtree. Repeat on each side. A slice is
// named by its start in each array and one length, because both arrays hold the same number of
// values for the same subtree.
//
// Time: O(n) -- one map lookup per node, and a node is built once.
// Space: O(n) -- the value -> in-order index map, plus the recursion (as deep as the tree is tall).
//
// Siblings: BinaryTreeFromInPostOrder is the same method on List<Integer> with start and end
// indices for each array.

public final class BinaryTreeFromInPostOrder106 {

	public static TreeNode construct(int[] in, int[] post) {
		Map<Integer, Integer> map = indexMap(in); // INDEX: value -> its in-order position
		return dfs(in, 0, post, 0, in.length, map);
	}

	private static TreeNode dfs(int[] in, int inStart, int[] post, int postStart, int len, Map<Integer, Integer> map) {
		if (len <= 0) { // BASE: empty slice, no node
			return null;
		}
		// last node in the postorder segment is the current root
		TreeNode node = new TreeNode(post[postStart + len - 1]); // ROOT: last post-order value of the slice
		int rootIndex = map.get(node.val); // root index in inOrder array
		int leftSize = rootIndex - inStart; // size of left subtree
		node.left = dfs(in, inStart, post, postStart, leftSize, map); // LEFT: first leftSize post values
		node.right = dfs(in, rootIndex + 1, post, postStart + leftSize, len - leftSize - 1, map); // RIGHT: the rest, minus the root
		return node;
	}

	private static Map<Integer, Integer> indexMap(int[] a) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < a.length; i++) {
			map.put(a[i], i); // RECORD: value -> position
		}
		return map;
	}

	public static void main(String[] args) {
		int[] in = { 9, 3, 15, 20, 7 };
		int[] post = { 9, 15, 7, 20, 3 };
		TreeNode root = construct(in, post);
		Nodes.preOrder(root); // 3 9 20 15 7
		System.out.println();
	}
}
