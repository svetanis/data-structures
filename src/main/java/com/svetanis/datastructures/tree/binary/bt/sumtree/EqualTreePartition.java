package com.svetanis.datastructures.tree.binary.bt.sumtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 663. Equal Tree Partition

public final class EqualTreePartition {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	private List<Integer> list;

	public boolean checkEqualTree(Node root) {
		this.list = new ArrayList<>();
		int totalSum = dfs(root);
		if (totalSum % 2 != 0) {
			return false;
		}
		list.remove(list.size() - 1);
		return new HashSet<>(list).contains(totalSum / 2);
	}

	private int dfs(Node root) {
		if (root == null) {
			return 0;
		}
		int left = dfs(root.left);
		int right = dfs(root.right);
		int sum = root.data + left + right;
		list.add(sum);
		return sum;
	}

	public static void main(String[] args) {
		Node root = newNode(5);
		root.left = newNode(10);
		root.right = newNode(10);
		root.right.left = newNode(2);
		root.right.right = newNode(3);
		EqualTreePartition mst = new EqualTreePartition();
		System.out.println(mst.checkEqualTree(root)); // true

		Node root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(10);
		root1.right.left = newNode(2);
		root1.right.right = newNode(20);
		EqualTreePartition mst2 = new EqualTreePartition();
		System.out.println(mst2.checkEqualTree(root1)); // false

	}
}
