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

// Bottom View of a Binary Tree (no LeetCode number)
//
// Input: the root of a binary tree. Returns the values of the bottom view, from the leftmost
// column to the rightmost: for each column the value of the lowest node in it. Two nodes in
// the same column and on the same row: the right one counts.
//
// The one idea: give every node a column number. The root is column 0, a left child is one
// column less than its parent, a right child one more. Walk the tree row by row, top to
// bottom and left to right, and write each node's value into a map keyed by its column. A
// later node in the same column is lower (or, on the same row, further right), so it simply
// overwrites the earlier one. When the walk ends, each column holds its lowest node, and the
// sorted map lists the columns from left to right.
//
// Time: O(n log n) -- n nodes, each one put into a TreeMap of up to n columns, O(log n) each.
// Space: O(n) -- the queue and the map.
//
// Siblings: BottomViewRecursive reaches the same answer with a depth-first walk that keeps the
// row beside each column's value. TopViewIterative keeps the first node per column instead of
// the last.

public final class BottomViewIterative {

	public static List<Integer> bottomView(TreeNode root) {
		if (root == null) {
			return new ArrayList<>(); // BASE: empty tree, empty view
		}

		Queue<Item> queue = new ArrayDeque<>(); // QUEUE: nodes waiting, each with its column
		Map<Integer, Integer> map = new TreeMap<>(); // MAP: column -> value of the lowest node so far
		queue.offer(new Item(root, 0)); // SEED: the root is column 0

		while (!queue.isEmpty()) { // WALK: row by row, left to right
			Item item = queue.poll();
			TreeNode node = item.node;
			int hd = item.hd;

			map.put(hd, node.val); // RECORD: overwrite -- a later node in this column is lower

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
		TreeNode root = newNode(20);
		root.left = newNode(8);
		root.right = newNode(22);
		root.left.left = newNode(5);
		root.left.right = newNode(3);
		root.left.right.left = newNode(10);
		root.left.right.right = newNode(14);
		root.right.right = newNode(25);
		System.out.println(bottomView(root)); // [5, 10, 3, 14, 25]

		TreeNode root2 = newNode(20);
		root2.left = newNode(8);
		root2.right = newNode(22);
		root2.left.left = newNode(5);
		root2.left.right = newNode(3);
		root2.left.right.left = newNode(10);
		root2.left.right.right = newNode(14);
		root2.right.left = newNode(4);
		root2.right.right = newNode(25);
		System.out.println(bottomView(root2)); // [5, 10, 4, 14, 25]
	}
}