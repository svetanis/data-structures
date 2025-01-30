package com.svetanis.datastructures.tree.binary.bt.dimention;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 662. Maximum Width of Binary Tree

// including null nodes between the end nodes

public final class MaxWidth662 {

	public static int maxWidth(Node root) {
		if (root == null) {
			return 0;
		}
		int max = 0;
		Deque<Pair> queue = new ArrayDeque<>();
		queue.add(new Pair(root, 1));
		while (!queue.isEmpty()) {
			int left = queue.peekFirst().index;
			int right = queue.peekLast().index;
			max = Math.max(max, right - left + 1);
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				Pair pair = queue.poll();
				Node node = pair.node;
				int index = pair.index;

				if (node.left != null) {
					queue.offer(new Pair(node.left, 2 * index));
				}
				if (node.right != null) {
					queue.offer(new Pair(node.right, 2 * index + 1));
				}
			}
		}
		return max;
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.right = newNode(8);
		root.right.right.left = newNode(6);
		root.right.right.right = newNode(7);
		System.out.println(maxWidth(root));
	}

	private static class Pair {
		private Node node;
		private int index;

		public Pair(Node node, int index) {
			this.node = node;
			this.index = index;
		}
	}
}
