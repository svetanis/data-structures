package com.svetanis.datastructures.tree.binary.bt.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.java.base.utils.Print;

// 655. Print Binary Tree

public final class PrintBinaryTree {
	// Time Complexity: O(n)
	// Space Complexity: O(n^2)

	public static List<List<String>> printTree(Node root) {
		int height = height(root);
		int m = height + 1; // rows
		int n = (1 << m) - 1; // cols Math.pow(2, height + 1) - 1;
		String[][] matrix = new String[m][n];
		for (int i = 0; i < m; i++) {
			Arrays.fill(matrix[i], "");
		}
		dfs(root, height, 0, (n - 1) / 2, matrix);
		return toList(matrix);
	}

	private static void dfs(Node root, int height, int row, int col, String[][] matrix) {
		if (root == null) {
			return;
		}
		matrix[row][col] = root.data + "";
		int offset = 1 << (height - row - 1); // Math.pow(2, height - row - 1);
		dfs(root.left, height, row + 1, col - offset, matrix);
		dfs(root.right, height, row + 1, col + offset, matrix);
	}

	private static List<List<String>> toList(String[][] matrix) {
		List<List<String>> list = new ArrayList<>();
		for (String[] a : matrix) {
			list.add(Arrays.asList(a));
		}
		return list;
	}

	private static int height(Node root) {
		if (root == null) {
			return -1;
		}
		int left = height(root.left);
		int right = height(root.right);
		return 1 + Math.max(left, right);
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(2);
		Print.print(printTree(root));

		Node root1 = new Node(1);
		root1.left = new Node(2);
		root1.right = new Node(3);
		root1.left.right = new Node(4);
		Print.print(printTree(root1));
	}
}
