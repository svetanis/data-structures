package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Level order of a perfect binary tree, deepest level first, alternating between the two ends
//
// Input: root of a PERFECT binary tree (every level full; not a LeetCode problem). Returns its nodes
// level by level, deepest level first; inside a level the nodes are taken alternately from the
// left end and the right end, moving inwards: for the levels 8 .. 15, 4 .. 7, 2 3 and 1 that is
// 8 15 9 14 10 13 11 12, then 4 7 5 6, then 2 3, then 1. A null root gives an empty list.
//
// The one idea: it is the top-down walk of LOTPerfectBtTopDown with a stack in place of the
// output list. One queue entry is one node whose children have not been listed yet; entries are
// taken two at a time, first on the left half of its level and second its mirror on the right
// half, and the tree being perfect guarantees the mirror. Each pair's four children are pushed in
// the reverse of their listing order, and the pairs are queued inner pair first, so the pairs of
// a level are pushed last-listed first. Popping the stack at the end gives the last level pushed
// first, and inside every level the same order the top-down walk lists.
//
// Time: O(n) -- every node is pushed once and queued at most once.
// Space: O(n) -- the stack holds every node; the queue at most a quarter of them.
//
// Siblings: LOTPerfectBtTopDown lists the same nodes with the top level first.

public final class LOTPerfectBtBottomUp {

	public static List<TreeNode> traverse(TreeNode root) {
		if (isNull(root)) {
			return new ArrayList<>();
		}
		Deque<TreeNode> stack = new ArrayDeque<>(); // STACK: pushed top-down, read from the top
		stack.push(root);
		if (isNull(root.left)) { // a single node: no next level
			return new ArrayList<>(stack);
		}

		// Since it is a perfect BT, right is not checked
		stack.push(root.right);
		stack.push(root.left);

		// if there are no nodes at the next level, return
		if (isNull(root.left.left)) {
			return new ArrayList<>(stack); // ArrayDeque iterates from the top: the pop order
		}

		// QUEUE: the two nodes of level 2, as one mirror pair
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root.left);
		queue.offer(root.right);

		// process two nodes at a time,
		// store two front items of queue
		while (!queue.isEmpty()) {
			TreeNode first = queue.poll();
			TreeNode second = queue.poll();

			// PUSH: first and second's children in reverse of the listing order
			stack.push(second.left);
			stack.push(first.right);
			stack.push(second.right);
			stack.push(first.left);

			// if first and second have grandchildren,
			// queue them in a specific order: inner pair first
			if (first.left.left != null) {
				queue.offer(first.right); // QUEUE: inner pair
				queue.offer(second.left);
				queue.offer(first.left); // QUEUE: outer pair
				queue.offer(second.right);
			}
		}
		return new ArrayList<>(stack); // RETURN: popped order = deepest level first
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
		// [16, 31, 17, 30, 18, 29, 19, 28, 20, 27, 21, 26, 22, 25, 23, 24, 8, 15, 9, 14, 10, 13, 11,
		// 12, 4, 7, 5, 6, 2, 3, 1]
		System.out.println(traverse(root));
	}
}
