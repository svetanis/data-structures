package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;

import java.util.HashMap;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 105. Construct Binary Tree from Preorder and Inorder Traversal
//
// Input: pre -- the pre-order values, in -- the in-order values of the same binary tree; all
// values are distinct. On LeetCode: 1 <= n <= 3000, -3000 <= value <= 3000.
// Returns: the root of the tree both sequences describe.
//
// The one idea: pre-order lists a node before its subtrees, so the first value of any pre-order
// slice is the root of that slice's tree. In-order lists the left subtree, then the root, then the
// right subtree, so the root's in-order position cuts the in-order slice in two: the part before it
// is the left subtree, the part after it the right subtree. The size of the left part says how many
// pre-order values (right after the root) belong to the left subtree; the rest belong to the right
// subtree. Repeat on each side. A slice is named by its start in each array and one size, because
// both arrays hold the same number of values for the same subtree.
//
// Time: O(n) -- one map lookup per node, and a node is built once.
// Space: O(n) -- the value -> in-order index map, plus the recursion (as deep as the tree is tall).
//
// Siblings: BinaryTreeFromInPreOrderSubmit is the same method on List<Integer>;
// BinaryTreeFromInPreOrder uses start and end indices instead of a size.

public final class BinaryTreeFromInPreOrder105 {

	public static TreeNode construct(int[] pre, int[] in) {
		Map<Integer, Integer> map = indexMap(in); // INDEX: value -> its in-order position
		return dfs(0, 0, in.length, pre, in, map);
	}

	private static TreeNode dfs(int preStart, int inStart, int size, int[] pre, int[] in, Map<Integer, Integer> map) {
		if (size <= 0) { // BASE: empty slice, no node
			return null;
		}
		int root = pre[preStart]; // ROOT: first pre-order value of the slice
		int rootIndex = map.get(root); // SPLIT POINT: root's in-order position
		int leftSize = rootIndex - inStart; // size of left subtree
		TreeNode left = dfs(preStart + 1, inStart, leftSize, pre, in, map); // LEFT: next leftSize pre values
		TreeNode right = dfs(preStart + leftSize + 1, rootIndex + 1, size - 1 - leftSize, pre, in, map); // RIGHT: the rest
		return new TreeNode(root, left, right);
	}

	private static Map<Integer, Integer> indexMap(int[] a) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < a.length; i++) {
			map.put(a[i], i); // RECORD: value -> position
		}
		return map;
	}

	public static void main(String[] args) {
		int[] in = { 3, 9, 20, 15, 7 };
		int[] pre = { 9, 3, 15, 20, 7 };
		TreeNode root = construct(pre, in);
		inOrder(root); // 3 9 20 15 7
		System.out.println();
		int[] pre1 = { 3, 9, 20, 15, 7 }; // LeetCode example 1
		int[] in1 = { 9, 3, 15, 20, 7 };
		inOrder(construct(pre1, in1)); // 9 3 15 20 7
		System.out.println();
	}
}
