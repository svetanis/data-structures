package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 105. Construct Binary Tree from Preorder and Inorder Traversal (List version)
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
// subtree. Repeat on each side. A slice is named by its start in each array and one size.
//
// Time: O(n) -- one map lookup per node, and a node is built once.
// Space: O(n) -- the value -> in-order index map, plus the recursion (as deep as the tree is tall).
//
// Siblings: BinaryTreeFromInPreOrder105 is the same method on int[]; BinaryTreeFromInPreOrder
// uses start and end indices instead of a size.

public final class BinaryTreeFromInPreOrderSubmit {

	public static TreeNode construct(List<Integer> pre, List<Integer> in) {
		Map<Integer, Integer> map = indexMap(in); // INDEX: value -> its in-order position
		return construct(0, 0, in.size(), pre, in, map);
	}

	private static TreeNode construct(int preStart, int inStart, int size, List<Integer> pre, List<Integer> in,
			Map<Integer, Integer> map) {
		if (size <= 0) { // BASE: empty slice, no node
			return null;
		}

		int root = pre.get(preStart); // ROOT: first pre-order value of the slice
		int rootIndex = map.get(root); // SPLIT POINT: root's in-order position
		int leftSize = rootIndex - inStart; // size of left subtree

		TreeNode left = construct(preStart + 1, inStart, leftSize, pre, in, map); // LEFT: next leftSize pre values
		TreeNode right = construct(preStart + leftSize + 1, rootIndex + 1, size - 1 - leftSize, pre, in, map); // RIGHT: the rest
		return new TreeNode(root, left, right);
	}

	private static Map<Integer, Integer> indexMap(List<Integer> list) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i), i); // RECORD: value -> position
		}
		return map;
	}

	public static void main(String[] args) {
		List<Integer> in = asList(4, 2, 5, 1, 3, 6);
		List<Integer> pre = asList(1, 2, 4, 5, 3, 6);
		TreeNode root = construct(pre, in);
		inOrder(root); // 4 2 5 1 3 6
		System.out.println();
	}
}
