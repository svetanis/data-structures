package com.svetanis.datastructures.tree.dp;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 968. Binary Tree Cameras

public final class BinaryTreeCameras {
	// Time Complexity: O(n)

	public static int btc(Node root) {
		if (root == null) {
			return 0;
		}
		Result r = dfs(root);
		return Math.min(r.current, r.child);
	}

	private static Result dfs(Node root) {
		if (root == null) {
			return new Result(Integer.MAX_VALUE / 2, 0, 0);
		}
		Result left = dfs(root.left);
		Result right = dfs(root.right);
		int leftMin = Math.min(left.current, Math.min(left.child, left.parent));
		int rightMin = Math.min(right.current, Math.min(right.child, right.parent));
		int current = 1 + leftMin + rightMin;
		int one = left.current + right.current;
		int two = left.current + right.child;
		int three = left.child + right.current;
		int children = Math.min(one, Math.min(two, three));
		int parent = left.child + right.child;
		return new Result(current, children, parent);
	}

	public static void main(String[] args) {
		Node root1 = newNode(0);
		root1.left = newNode(0);
		root1.left.left = newNode(0);
		root1.left.right = newNode(0);
		System.out.println(btc(root1)); // 1

		Node root2 = newNode(0);
		root2.left = newNode(0);
		root2.left.left = newNode(0);
		root2.left.left.left = newNode(0);
		root2.left.left.left.right = newNode(0);
		System.out.println(btc(root2)); // 2

		Node root3 = newNode(0);
		System.out.println(btc(root3)); // 0
	}

	private static class Result {
		int current;
		int child;
		int parent;

		public Result(int current, int child, int parent) {
			this.current = current;
			this.child = child;
			this.parent = parent;
		}
	}
}
