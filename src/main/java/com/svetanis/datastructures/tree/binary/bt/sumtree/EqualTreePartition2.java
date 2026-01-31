package com.svetanis.datastructures.tree.binary.bt.sumtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 663. Equal Tree Partition

public final class EqualTreePartition2 {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	private boolean equalSplit;

	public boolean checkEqualTree(Node root) {
		int totalSum = sum(root);
		if (totalSum % 2 != 0) {
			return false;
		}
		dfs(root, totalSum / 2, root);
		return equalSplit;
	}

	private int sum(Node root) {
		if (root == null) {
			return 0;
		}
		int sum = root.data;
		sum += sum(root.left);
		sum += sum(root.right);
		return sum;
	}

	private int dfs(Node root, int target, Node node) {
		if (node == null) {
			return 0;
		}
		int sum = node.data;
		sum += dfs(root, target, node.left);
		sum += dfs(root, target, node.right);
		if (sum == target && node != root) {
			equalSplit = true;
		}
		return sum;
	}

	public static void main(String[] args) {
		Node root = newNode(5);
		root.left = newNode(10);
		root.right = newNode(10);
		root.right.left = newNode(2);
		root.right.right = newNode(3);
		EqualTreePartition2 mst = new EqualTreePartition2();
		System.out.println(mst.checkEqualTree(root)); // true

		Node root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(10);
		root1.right.left = newNode(2);
		root1.right.right = newNode(20);
		EqualTreePartition2 mst2 = new EqualTreePartition2();
		System.out.println(mst2.checkEqualTree(root1)); // false

	}
}
