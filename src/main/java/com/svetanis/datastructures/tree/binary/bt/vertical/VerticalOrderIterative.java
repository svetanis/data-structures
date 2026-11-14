package com.svetanis.datastructures.tree.binary.bt.vertical;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import com.svetanis.datastructures.tree.binary.bt.view.Item;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Vertical Order Traversal of a Binary Tree (LeetCode 314 is the same question)
//
// Input: the root of a binary tree. Give the root column 0; a left child is one column left of
// its parent and a right child one column right (the children make a 45 degree angle with the
// parent). Returns a map from column number to the values in that column, columns sorted from
// left to right; inside a column, top to bottom, and left to right on the same row. An empty
// tree gives an empty map. Equal values in one column are all kept.
//
// The one idea: a queue walks the tree one row at a time, left to right inside a row, and
// remembers each node's column. Appending every node to its column's list as it comes off the
// queue puts each list in exactly the required order, so nothing needs sorting. A TreeMap keeps
// the columns sorted by their number.
//
// Time: O(n log c) -- each node is queued once and filed in a TreeMap of c columns (c <= n).
// Space: O(n) -- the queue and the lists.
//
// Siblings: VerticalOrderRecursive files the same columns with a preorder recursion; inside a
// column it comes out in a different order, for example column 3 of the tree in main as [12, 9]
// where this file gives [9, 12]. Within a column the queue hands nodes over shallowest first, so
// this walk needs no sort and that one does. VerticalOrderSubmit is the LeetCode 314 version.

public final class VerticalOrderIterative {

	public static Map<Integer, List<Integer>> verticalOrder(TreeNode root) {
		Map<Integer, List<Integer>> columns = new TreeMap<>();
		if (root == null) {
			return columns; // BASE: no nodes, no columns
		}
		Queue<Item> queue = new ArrayDeque<>();
		queue.offer(new Item(root, 0)); // START: the root is column 0
		while (!queue.isEmpty()) {
			Item item = queue.poll();
			int dist = item.hd;
			TreeNode node = item.node;
			columns.computeIfAbsent(dist, k -> new ArrayList<>()).add(node.val); // FILE under its column
			if (isNotNull(node.left)) {
				queue.offer(new Item(node.left, dist - 1)); // PUSH: one column left
			}
			if (isNotNull(node.right)) {
				queue.offer(new Item(node.right, dist + 1)); // PUSH: one column right
			}
		}
		return columns;
	}

	public static void main(String[] args) {
		TreeNode root1 = tree1();
		System.out.println(verticalOrder(root1));
		// {-2=[4], -1=[2], 0=[1, 5, 6], 1=[3, 8, 10], 2=[7, 11], 3=[9, 12]}
		TreeNode root2 = tree2();
		System.out.println(verticalOrder(root2));
		// {-1=[2, 7], 0=[1, 5, 9], 1=[3, 8], 2=[6, 10]}
		TreeNode same = newNode(1);
		same.left = newNode(2);
		same.right = newNode(2);
		same.left.right = newNode(1);
		same.right.left = newNode(1);
		System.out.println(verticalOrder(same)); // {-1=[2], 0=[1, 1, 1], 1=[2]}
		System.out.println(verticalOrder(null)); // {}
	}

	private static TreeNode tree2() {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.right.left = newNode(5);
		root.right.right = newNode(6);
		root.right.left.left = newNode(7);
		root.right.left.right = newNode(8);
		root.right.left.right.left = newNode(9);
		root.right.left.right.right = newNode(10);
		return root;
	}

	private static TreeNode tree1() {
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
		return root;
	}
}
