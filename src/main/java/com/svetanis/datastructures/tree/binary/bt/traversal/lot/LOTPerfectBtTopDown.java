package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Level order of a perfect binary tree, alternating between the two ends of each level
//
// Input: root of a PERFECT binary tree (every level full; not a LeetCode problem). Returns its nodes
// level by level, top level first; inside a level the nodes are taken alternately from the left
// end and the right end, moving inwards: for the levels 4 5 6 7 and 8 .. 15 that is 4 7 5 6 and
// 8 15 9 14 10 13 11 12. A null root gives an empty list.
//
// The one idea: one queue entry is one node whose children have not been listed yet. Because the
// tree is perfect, the entries can be taken two at a time: first is on the left half of its level
// and second is its mirror on the right half. Their four children are listed outermost first --
// first.left, second.right, then first.right, second.left. Queueing the children as the same two
// mirror pairs, outer pair first, makes the next level come out outside-in as well. Perfect means
// a mirror always exists, and that the next level exists exactly when first has a left child.
//
// Time: O(n) -- every node is listed once and queued at most once.
// Space: O(n) -- the output list; the queue holds at most a quarter of the nodes.
//
// Siblings: LOTPerfectBtBottomUp lists the same nodes with the deepest level first.

public final class LOTPerfectBtTopDown {

	public static List<TreeNode> traverse(TreeNode root) {
		List<TreeNode> list = new ArrayList<>();
		if (isNull(root)) {
			return list;
		}
		list.add(root); // LEVEL 1
		if (isNull(root.left)) { // a single node: no next level
			return list;
		}
		// Since it is a perfect BT, right is not checked
		list.add(root.left); // LEVEL 2: left, right
		list.add(root.right);

		// if there are no nodes at the next level, return
		if (isNull(root.left.left)) {
			return list;
		}

		// QUEUE: the two nodes of level 2, as one mirror pair
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root.left);
		queue.offer(root.right);

		// process two nodes at a time: first, and its mirror second
		while (!queue.isEmpty()) {
			TreeNode first = queue.poll();
			TreeNode second = queue.poll();

			list.add(first.left); // LIST: their children, outermost first
			list.add(second.right);
			list.add(first.right);
			list.add(second.left);

			// if first and second have grandchildren, queue the children as
			// two mirror pairs, outer pair first
			if (isNotNull(first.left.left)) {
				queue.offer(first.left); // QUEUE: outer pair
				queue.offer(second.right);
				queue.offer(first.right); // QUEUE: inner pair
				queue.offer(second.left);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(7);
		root.left.left.left = new TreeNode(8);
		root.left.left.right = new TreeNode(9);
		root.left.right.left = new TreeNode(10);
		root.left.right.right = new TreeNode(11);
		root.right.left.left = new TreeNode(12);
		root.right.left.right = new TreeNode(13);
		root.right.right.left = new TreeNode(14);
		root.right.right.right = new TreeNode(15);
		root.left.left.left.left = new TreeNode(16);
		root.left.left.left.right = new TreeNode(17);
		root.left.left.right.left = new TreeNode(18);
		root.left.left.right.right = new TreeNode(19);
		root.left.right.left.left = new TreeNode(20);
		root.left.right.left.right = new TreeNode(21);
		root.left.right.right.left = new TreeNode(22);
		root.left.right.right.right = new TreeNode(23);
		root.right.left.left.left = new TreeNode(24);
		root.right.left.left.right = new TreeNode(25);
		root.right.left.right.left = new TreeNode(26);
		root.right.left.right.right = new TreeNode(27);
		root.right.right.left.left = new TreeNode(28);
		root.right.right.left.right = new TreeNode(29);
		root.right.right.right.left = new TreeNode(30);
		root.right.right.right.right = new TreeNode(31);
		// [1, 2, 3, 4, 7, 5, 6, 8, 15, 9, 14, 10, 13, 11, 12, 16, 31, 17, 30, 18, 29, 19, 28, 20, 27,
		// 21, 26, 22, 25, 23, 24]
		System.out.println(traverse(root));
	}
}
