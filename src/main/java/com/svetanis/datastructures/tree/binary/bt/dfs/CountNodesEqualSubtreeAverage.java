package com.svetanis.datastructures.tree.binary.bt.dfs;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 2265. Count Nodes Equal to Average of Subtree

public final class CountNodesEqualSubtreeAverage {
	// Time Complexity: O(n)
	// Space Complexity: O(h)

	public static int count(Node root) {
		return dfs(root).count;
	}

	private static Result dfs(Node node) {
		if (isNull(node)) {
			return new Result(0, 0, 0);
		}
		Result left = dfs(node.left);
		Result right = dfs(node.right);
		int sum = node.data + left.sum + right.sum;
		int size = 1 + left.size + right.size;
		int count = left.count + right.count;
		if (node.data == sum / size) {
			count += 1;
		}
		return new Result(sum, size, count);
	}

	public static void main(String[] args) {
		Node root = newNode(4);
		root.left = newNode(8);
		root.right = newNode(5);
		root.left.left = newNode(0);
		root.left.right = newNode(1);
		root.right.right = newNode(6);
		System.out.println(count(root)); // 5

		Node root2 = newNode(1);
		System.out.println(count(root2)); // 1
	}

	private static class Result {
		private int sum;
		private int size;
		private int count;

		public Result(int sum, int size, int count) {
			this.sum = sum;
			this.size = size;
			this.count = count;
		}
	}
}