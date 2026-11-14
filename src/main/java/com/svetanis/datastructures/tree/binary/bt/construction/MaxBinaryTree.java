package com.svetanis.datastructures.tree.binary.bt.construction;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 654. Maximum Binary Tree
//
// Input: an int array a of distinct values. On LeetCode: 1 <= a.length <= 1000,
// 0 <= a[i] <= 1000.
// Returns: the root of the tree built by the rule: the root is the maximum of the array, its left
// subtree is built the same way from the values to the left of the maximum, its right subtree from
// the values to the right.
//
// The one idea: the rule is the recipe itself, so follow it. One call builds the tree for one slice
// of the array: scan the slice for its maximum, make that value a node, and hand the part before it
// and the part after it to two more calls. An empty slice is no node.
//
// Time: O(n^2) worst case -- every call scans its whole slice; on a sorted array the slices shrink
// by one each time, n + (n-1) + ... comparisons. On random input the slices halve, O(n log n).
// (A stack of decreasing values builds the same tree in O(n).)
// Space: O(n) -- the n nodes built, plus the recursion (as deep as the tree is tall, up to n).
//
// Siblings: CartesianBtFromInOrder is the same method with the scan in a helper, for any int
// values.

public final class MaxBinaryTree {

	public static TreeNode maxBinaryTree(int[] a) {
		return dfs(a, 0, a.length - 1);
	}

	private static TreeNode dfs(int[] a, int left, int right) {
		if (left > right) { // BASE: empty slice, no node
			return null;
		}
		int max = left;
		for (int index = left; index <= right; index++) { // SCAN: index of the maximum
			if (a[max] < a[index]) {
				max = index;
			}
		}
		TreeNode node = new TreeNode(a[max]); // ROOT: the maximum
		node.left = dfs(a, left, max - 1); // LEFT: values before the maximum
		node.right = dfs(a, max + 1, right); // RIGHT: values after the maximum
		return node;
	}

	public static void main(String[] args) {
		int[] a = { 3, 2, 1, 6, 0, 5 };
		Nodes.preOrder(maxBinaryTree(a)); // 6 3 2 1 5 0
		System.out.println();
		int[] b = { 3, 2, 1 };
		Nodes.preOrder(maxBinaryTree(b)); // 3 2 1
		System.out.println();
	}
}
