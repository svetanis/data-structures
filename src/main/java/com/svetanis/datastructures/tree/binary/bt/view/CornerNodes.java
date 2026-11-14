package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Corner Nodes of Every Level (no LeetCode number)
//
// Input: the root of a binary tree. Returns, for each row from the top, the leftmost and the
// rightmost node of that row; a row with a single node gives that one node once.
//
// The one idea: walk the tree one row at a time, left to right. When a pass starts, the queue
// holds exactly one row, so its size says how many nodes belong to that row. The first node
// taken (position 0) and the last node taken (position size - 1) are the row's two corners;
// when the row has one node both tests hit the same node, and the single test keeps it once.
//
// Time: O(n) -- every node is queued once and taken once.
// Space: O(w) -- the queue holds at most the widest row, w <= n, plus the output.

public final class CornerNodes {

	public static List<List<TreeNode>> cornerNodes(TreeNode root) {
		if (isNull(root)) {
			return new ArrayList<>(); // BASE: no rows
		}
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root); // SEED: the first row is the root alone
		List<List<TreeNode>> lists = new ArrayList<>();

		while (!queue.isEmpty()) { // WALK: one pass per row
			int size = queue.size(); // COUNT: nodes in this row
			List<TreeNode> list = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				TreeNode node = queue.poll();
				if (i == 0 || i == size - 1) {
					list.add(node); // TAKE: first or last of the row
				}
				if (isNotNull(node.left)) {
					queue.offer(node.left); // QUEUE: next row, left child first
				}
				if (isNotNull(node.right)) {
					queue.offer(node.right);
				}
			}
			lists.add(list);
		}
		return lists;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(6);
		root.left = newNode(3);
		root.right = newNode(8);
		root.right.left = newNode(4);
		root.right.right = newNode(2);
		root.right.left.left = newNode(1);
		root.right.left.right = newNode(7);
		root.right.right.right = newNode(3);
		System.out.println(cornerNodes(root)); // [[6], [3, 8], [4, 2], [1, 3]]
	}
}
