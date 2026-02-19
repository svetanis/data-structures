package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1339. Maximum Product of Splitted Binary Tree

public final class MaxProductSplittedBT {
	// Time Complexity: O(n)
	// Space Complexity: O(h)

	private static final int MOD = (int) 1e9 + 7;

	private long maxProduct;

	public int maxProduct(Node root) {
		this.maxProduct = 0;
		long total = sum(root);
		dfs(root, total);
		return (int) (maxProduct % MOD);
	}

	private long dfs(Node node, long total) {
		if (node == null) {
			return 0;
		}
		long sum = node.data + dfs(node.left, total) + dfs(node.right, total);
		long product = sum * (total - sum);
		maxProduct = Math.max(maxProduct, product);
		return sum;
	}

	private long sum(Node node) {
		if (node == null) {
			return 0;
		}
		long left = sum(node.left);
		long right = sum(node.right);
		return node.data + left + right;
	}

	public static void main(String[] args) {

		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.left = newNode(6);
		MaxProductSplittedBT mst = new MaxProductSplittedBT();
		System.out.println(mst.maxProduct(root)); // 110

		Node root2 = newNode(1);
		root2.right = newNode(2);
		root2.right.left = newNode(3);
		root2.right.right = newNode(4);
		root2.right.right.left = newNode(5);
		root2.right.right.right = newNode(6);
		MaxProductSplittedBT mst2 = new MaxProductSplittedBT();
		System.out.println(mst2.maxProduct(root2)); // 90

	}
}
