package com.svetanis.datastructures.tree.binary.bt.vertical;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Vertical Order Traversal of a Binary Tree, depth first
//
// Input: the root of a binary tree. Give the root column 0; a left child is one column left of
// its parent and a right child one column right (the children make a 45 degree angle with the
// parent). Returns a map from column number to the values in that column, columns sorted from
// left to right; inside a column, in the order a preorder walk meets them. An empty tree gives an
// empty map. Equal values in one column are all kept.
//
// The one idea: carry the column number down the recursion and append each node to its column's
// list in a TreeMap, which keeps the columns sorted by their number.
//
// Order inside a column: column 3 of the tree in main comes out DEEPEST-FIRST here, [12, 9],
// and the level-order sibling gives [9, 12]. Node 9 sits three levels down and node 12 five, but
// 12 is reached through the LEFT child, which this walk finishes before it looks at the right
// one. A walk by levels cannot get this wrong; a walk by branches has to sort by depth
// afterwards -- see VerticalOrderIterative.
//
// Time: O(n log c) -- each node is visited once and filed in a TreeMap of c columns (c <= n).
// Space: O(n) -- the lists, plus the recursion stack of the height of the tree.
//
// Known limit: the recursion is as deep as the tree is tall, so a chain of tens of thousands of
// nodes overflows the default stack; VerticalOrderIterative is the loop version (it also gives
// the level-by-level order).
//
// Siblings: VerticalOrderIterative uses a queue instead of recursion; VerticalOrderSubmit is the
// LeetCode 314 version.

public final class VerticalOrderRecursive {

	public static Map<Integer, List<Integer>> verticalOrder(TreeNode root) {
		Map<Integer, List<Integer>> columns = new TreeMap<>();
		verticalOrder(root, 0, columns);
		return columns;
	}

	private static void verticalOrder(TreeNode node, int dist, Map<Integer, List<Integer>> columns) {
		if (isNull(node)) {
			return;
		}
		columns.computeIfAbsent(dist, k -> new ArrayList<>()).add(node.val); // FILE under its column
		verticalOrder(node.left, dist - 1, columns); // WALK: one column left
		verticalOrder(node.right, dist + 1, columns); // WALK: one column right
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.left = newNode(6);
		root.right.right = newNode(7);
		root.right.left.right = newNode(8);
		root.right.right.right = newNode(9);
		root.right.right.left = newNode(10);
		root.right.right.left.right = newNode(11);
		root.right.right.left.right.right = newNode(12);
		System.out.println(verticalOrder(root));
		// {-2=[4], -1=[2], 0=[1, 5, 6], 1=[3, 8, 10], 2=[7, 11], 3=[12, 9]}
		System.out.println(verticalOrder(null)); // {}
	}
}
