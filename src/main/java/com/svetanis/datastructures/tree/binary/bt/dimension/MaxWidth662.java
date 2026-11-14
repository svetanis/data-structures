package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 662. Maximum Width of Binary Tree
//
// Input: the root of a binary tree. On LeetCode: 1 <= number of nodes <= 3000,
// -100 <= val <= 100, and the answer fits in a 32-bit signed integer. Returns the maximum width
// of any level. The width of a level is the distance between its leftmost and rightmost real
// nodes, counting the empty positions in between as if the tree were complete.
//
// The one idea: give every node the position it would have in a complete tree. The root is 1; the
// node at position i has its left child at 2i and its right child at 2i + 1. On one level the
// positions are consecutive numbers, so the width of that level is last position - first
// position + 1, and the gaps between the end nodes count automatically. A queue holds one level
// at a time, front = leftmost, back = rightmost, each with its position.
//
// On a tall thin tree the positions outgrow an int (2i doubles at each level). That is harmless:
// int arithmetic wraps around, every position stays correct modulo 2^32, so
// last - first + 1 comes out exact whenever the true width fits in an int, and the statement
// promises that it does.
//
// Time: O(n) -- each node is pushed and popped once.
// Space: O(w) -- the queue holds one level, up to about n / 2 nodes.
//
// Siblings: MaxWidthQueue, MaxWidthPreOrder and MaxWidthLevelOrder count only the real nodes of
// a level (no gaps), which is a different question.

public final class MaxWidth662 {

	public static int maxWidth(TreeNode root) {
		if (root == null) {
			return 0; // BASE: no levels, no width
		}
		int max = 0;
		Deque<Pair> queue = new ArrayDeque<>();
		queue.add(new Pair(root, 1));
		while (!queue.isEmpty()) {
			int left = queue.peekFirst().index; // ENDS: position of the leftmost real node
			int right = queue.peekLast().index; // ENDS: position of the rightmost real node
			max = Math.max(max, right - left + 1); // RECORD: width of this level, gaps included
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				Pair pair = queue.poll();
				TreeNode node = pair.node;
				int index = pair.index;

				if (node.left != null) {
					queue.offer(new Pair(node.left, 2 * index)); // PUSH: left child at 2i
				}
				if (node.right != null) {
					queue.offer(new Pair(node.right, 2 * index + 1)); // PUSH: right child at 2i + 1
				}
			}
		}
		return max;
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.right = newNode(8);
		root.right.right.left = newNode(6);
		root.right.right.right = newNode(7);
		System.out.println(maxWidth(root)); // 4

		TreeNode ex1 = newNode(1);
		ex1.left = newNode(3);
		ex1.right = newNode(2);
		ex1.left.left = newNode(5);
		ex1.left.right = newNode(3);
		ex1.right.right = newNode(9);
		System.out.println(maxWidth(ex1)); // 4

		TreeNode ex2 = newNode(1);
		ex2.left = newNode(3);
		ex2.right = newNode(2);
		ex2.left.left = newNode(5);
		ex2.left.left.left = newNode(6);
		ex2.right.right = newNode(9);
		ex2.right.right.left = newNode(7);
		System.out.println(maxWidth(ex2)); // 7

		TreeNode ex3 = newNode(1);
		ex3.left = newNode(3);
		ex3.right = newNode(2);
		ex3.left.left = newNode(5);
		System.out.println(maxWidth(ex3)); // 2
	}

	private static class Pair {
		private TreeNode node;
		private int index;

		public Pair(TreeNode node, int index) {
			this.node = node;
			this.index = index;
		}
	}
}
