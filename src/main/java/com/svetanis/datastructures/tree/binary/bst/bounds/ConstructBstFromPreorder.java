package com.svetanis.datastructures.tree.binary.bst.bounds;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 1008. Construct Binary Search Tree from Preorder Traversal
//
// Input: pre -- the preorder walk of a binary search tree (root, then left subtree, then right
// subtree). On LeetCode: 1 <= pre.length <= 100, 1 <= pre[i] <= 1000, all values distinct.
// Returns the root of the tree that walk came from.
//
// The one idea: one range pre[low..high] holds the walk of one subtree, and its first entry is
// that subtree's root. After the root come all the values smaller than it (the left subtree),
// then all the values larger than it (the right subtree). So the range reads "smaller, smaller,
// ..., larger, larger", and a binary search for the first value larger than the root finds the
// boundary. The smaller part is the walk of the left subtree, the larger part the walk of the
// right subtree; each is built the same way.
//
// Time: O(n log n) -- each of the n nodes costs one binary search over its own range.
// Space: O(h) -- the recursion is as deep as the tree is tall, h <= n, besides the n nodes returned.

public final class ConstructBstFromPreorder {

	public static TreeNode bstFromPreorder(int[] pre) {
		return dfs(pre, pre[0], 0, pre.length - 1);
	}

	// the target parameter is not read: the root of the range is always pre[low]
	private static TreeNode dfs(int[] pre, int target, int low, int high) {
		if (low > high) { // BASE: an empty range is an empty subtree
			return null;
		}
		TreeNode root = new TreeNode(pre[low]); // ROOT: the first entry of the range
		int boundary = binary(pre, pre[low], low + 1, high + 1); // SPLIT: first value larger than the root
		root.left = dfs(pre, pre[low], low + 1, boundary - 1); // LEFT: the smaller values
		root.right = dfs(pre, pre[low], boundary, high); // RIGHT: the larger values
		return root;
	}

	// the first index in [left, right) holding a value larger than target, or right when none does
	private static int binary(int[] a, int target, int left, int right) {
		while (left < right) {
			int mid = left + (right - left) / 2;
			if (a[mid] > target) {
				right = mid; // SEARCH: mid could be the boundary, keep it
			} else {
				left = mid + 1; // SEARCH: mid is smaller, the boundary is further right
			}
		}
		return left;
	}

	public static void main(String[] args) {
		int[] pre = { 8, 5, 1, 7, 10, 12 };
		TreeNode root = bstFromPreorder(pre);
		Nodes.preOrder(root); // prints 8 5 1 7 10 12
		System.out.println();
		Nodes.preOrder(bstFromPreorder(new int[] { 1, 3 })); // prints 1 3
		System.out.println();
		System.out.println(bstFromPreorder(pre).right.left); // null
		System.out.println(bstFromPreorder(pre).left.right); // 7
	}
}