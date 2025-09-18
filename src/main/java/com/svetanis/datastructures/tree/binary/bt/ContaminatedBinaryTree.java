package com.svetanis.datastructures.tree.binary.bt;

import java.util.HashSet;
import java.util.Set;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 1261. Find Elements in a Contaminated Binary Tree

public final class ContaminatedBinaryTree {

	private Set<Integer> values;

	public ContaminatedBinaryTree(Node root) {
		root.data = 0;
		this.values = new HashSet<>();
		this.values.add(0);
		dfs(root);
	}

	private void dfs(Node node) {
		this.values.add(node.data);
		if (node.left != null) {
			node.left.data = 2 * node.data + 1;
			dfs(node.left);
		}
		if (node.right != null) {
			node.right.data = 2 * node.data + 2;
			dfs(node.right);
		}
	}

	public boolean find(int target) {
		return values.contains(target);
	}

	public static void main(String[] args) {
		Node root = new Node(-1);
		root.left = new Node(-1);
		root.right = new Node(-1);
		root.left.left = new Node(-1);
		root.left.right = new Node(-1);

		ContaminatedBinaryTree cbt = new ContaminatedBinaryTree(root);
		System.out.println(cbt.find(1)); // true
		System.out.println(cbt.find(3)); // true
		System.out.println(cbt.find(5)); // false
		System.out.println();
		
		Node root3 = new Node(-1);
		root3.right = new Node(-1);
		root3.right.left = new Node(-1);
		root3.right.left.left = new Node(-1);

		ContaminatedBinaryTree cbt2 = new ContaminatedBinaryTree(root3);
		System.out.println(cbt2.find(2)); // true
		System.out.println(cbt2.find(3)); // false
		System.out.println(cbt2.find(4)); // false
		System.out.println(cbt2.find(5)); // true
	}
}
