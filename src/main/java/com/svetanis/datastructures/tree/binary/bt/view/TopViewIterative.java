package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Top View of a Binary Tree (no LeetCode number)
//
// Input: the root of a binary tree. Returns the values of the top view, from the leftmost
// column to the rightmost: for each column the value of the highest node in it. Two nodes in
// the same column and on the same row: the left one counts.
//
// The one idea: give every node a column number. The root is column 0, a left child is one
// column less than its parent, a right child one more. Walk the tree row by row, top to
// bottom and left to right, so the first node met in a column is the highest one (and, on a
// tie, the leftmost). Write a column's value only the first time the column appears. The
// sorted map then lists the columns from left to right.
//
// Time: O(n log n) -- n nodes, each one looks up and maybe puts in a TreeMap of up to n
// columns, O(log n) each.
// Space: O(n) -- the queue and the map.
//
// Siblings: TopViewRecursive reaches the same answer with a depth-first walk that keeps the row
// beside each column's value. BottomViewIterative keeps the last node per column instead of
// the first.

public final class TopViewIterative {

	public static List<Integer> topView(TreeNode root) {
		if (root == null) {
			return new ArrayList<>(); // BASE: empty tree, empty view
		}

		Queue<Item> queue = new ArrayDeque<>(); // QUEUE: nodes waiting, each with its column
		Map<Integer, Integer> map = new TreeMap<>(); // MAP: column -> value of the highest node
		queue.offer(new Item(root, 0)); // SEED: the root is column 0

		while (!queue.isEmpty()) { // WALK: row by row, left to right
			Item item = queue.poll();
			TreeNode node = item.node;
			int hd = item.hd;

			if (!map.containsKey(hd)) {
				map.put(hd, node.val); // RECORD: first node in this column is the highest
			}

			if (isNotNull(node.left)) {
				queue.offer(new Item(node.left, hd - 1)); // QUEUE: left child, one column left
			}
			if (isNotNull(node.right)) {
				queue.offer(new Item(node.right, hd + 1)); // QUEUE: right child, one column right
			}
		}
		return new ArrayList<>(map.values()); // RETURN: values in column order
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.left = newNode(6);
		root.right.right = newNode(7);
		System.out.println(topView(root)); // [4, 2, 1, 3, 7]

		TreeNode root2 = newNode(1);
		root2.left = newNode(2);
		root2.right = newNode(3);
		root2.left.right = newNode(4);
		root2.left.right.right = newNode(5);
		root2.left.right.right.right = newNode(6);
		System.out.println(topView(root2)); // [2, 1, 3, 6]
	}
}
