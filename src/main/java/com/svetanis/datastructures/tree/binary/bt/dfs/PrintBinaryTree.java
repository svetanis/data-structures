package com.svetanis.datastructures.tree.binary.bt.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 655. Print Binary Tree
//
// Input: root of a binary tree. Returns a grid of strings with height + 1 rows and
// 2^(height + 1) - 1 columns, where the height is the number of edges on the longest root-to-leaf
// path. The root goes in the middle of the top row; a node in row r and column c puts its left
// child in row r + 1 and column c - 2^(height - r - 1), and its right child at c + that amount.
// Every cell no node uses is the empty string. On LeetCode: 1 <= number of nodes <= 2^10, the
// depth is at most 10, -99 <= node.val <= 99.
//
// The one idea: one cell holds one node's value, so the grid size comes first -- from the height
// -- and then each node only needs its row and column. The offset to a child is half the width
// of the space below, which halves each row, so left and right subtrees can never collide: a
// child at row r + 1 lies exactly 2^(height - r - 1) columns away from its parent.
//
// Time: O(h * 2^h) -- filling the grid sets h + 1 rows of 2^(h + 1) - 1 cells; the tree walks
// are O(n), and n is at most 2^(h + 1) - 1.
// Space: O(h * 2^h) -- the grid; the recursion stack is only O(h).

public final class PrintBinaryTree {

	public static List<List<String>> printTree(TreeNode root) {
		int height = height(root);
		int m = height + 1; // ROWS: one per level
		int n = (1 << m) - 1; // COLS: 2^(height + 1) - 1
		String[][] matrix = new String[m][n];
		for (int i = 0; i < m; i++) {
			Arrays.fill(matrix[i], ""); // FILL: every unused cell is the empty string
		}
		dfs(root, height, 0, (n - 1) / 2, matrix); // WALK: the root goes in the middle of row 0
		return toList(matrix);
	}

	private static void dfs(TreeNode root, int height, int row, int col, String[][] matrix) {
		if (root == null) {
			return;
		}
		matrix[row][col] = String.valueOf(root.val); // PLACE: this node's cell
		int offset = 1 << (height - row - 1); // OFFSET: 2^(height - row - 1) columns to each child
		dfs(root.left, height, row + 1, col - offset, matrix); // WALK: left child, down and left
		dfs(root.right, height, row + 1, col + offset, matrix); // WALK: right child, down and right
	}

	private static List<List<String>> toList(String[][] matrix) {
		List<List<String>> list = new ArrayList<>();
		for (String[] a : matrix) {
			list.add(Arrays.asList(a));
		}
		return list;
	}

	private static int height(TreeNode root) {
		if (root == null) {
			return -1; // BASE: an empty tree has height -1, so a single node has height 0
		}
		int left = height(root.left);
		int right = height(root.right);
		return 1 + Math.max(left, right); // RETURN: one more than the taller side
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		print(printTree(root)); // [, 1, ] [2, , ]

		TreeNode root1 = new TreeNode(1);
		root1.left = new TreeNode(2);
		root1.right = new TreeNode(3);
		root1.left.right = new TreeNode(4);
		print(printTree(root1)); // [, , , 1, , , ] [, 2, , , , 3, ] [, , 4, , , , ]
	}

	private static void print(List<List<String>> rows) {
		for (List<String> row : rows) {
			System.out.print(row);
			System.out.print(" ");
		}
		System.out.println();
	}
}
