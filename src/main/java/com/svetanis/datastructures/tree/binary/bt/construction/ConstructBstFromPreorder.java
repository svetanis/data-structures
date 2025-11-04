package com.svetanis.datastructures.tree.binary.bt.construction;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 1008. Construct Binary Search Tree from Preorder Traversal

public final class ConstructBstFromPreorder {
	// Time Complexity: O(n log n)
	// Space Complexity: O(n)

	public static Node bstFromPreorder(int[] pre) {
		return dfs(pre, pre[0], 0, pre.length - 1);
	}

	private static Node dfs(int[] pre, int target, int low, int high) {
		if (low > high) {
			return null;
		}
		Node root = new Node(pre[low]);
		int boundary = binary(pre, pre[low], low + 1, high + 1);
		root.left = dfs(pre, pre[low], low + 1, boundary - 1);
		root.right = dfs(pre, pre[low], boundary, high);
		return root;
	}

	private static int binary(int[] a, int target, int left, int right) {
		while (left < right) {
			int mid = left + (right - left) / 2;
			if (a[mid] > target) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}
		return left;
	}

	public static void main(String[] args) {
		int[] pre = { 8, 5, 1, 7, 10, 12 };
		Node root = bstFromPreorder(pre);
		Nodes.preOrder(root);
	}
}