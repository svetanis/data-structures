package com.svetanis.datastructures.tree.binary.bt;

import com.svetanis.datastructures.tree.binary.bt.traversal.lot.LotQueue;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 623. Add One Row to Tree

public final class AddRowToBT {

	public static Node addRow(Node root, int val, int depth) {
		if (depth == 1) {
			Node node = new Node(val);
			node.left = root;
			return node;
		}
		dfs(root, val, depth, 1);
		return root;
	}

	private static void dfs(Node root, int val, int depth, int current) {
		if (root == null) {
			return;
		}
		if (current == depth - 1) {
			Node node1 = new Node(val);
			node1.left = root.left;
			Node node2 = new Node(val);
			node2.right = root.right;
			root.left = node1;
			root.right = node2;
		}
		dfs(root.left, val, depth, current + 1);
		dfs(root.right, val, depth, current + 1);
	}

	public static void main(String[] args) {
		Node root = new Node(4);
		root.left = new Node(2);
		root.right = new Node(6);
		root.left.left = new Node(3);
		root.left.right = new Node(1);
		root.right.left = new Node(5);
		Node root2 = addRow(root, 1, 2);
		System.out.println(LotQueue.traverse(root2));
		// 4,1,1,2,null,null,6,3,1,5

		Node root3 = new Node(4);
		root3.left = new Node(2);
		root3.left.left = new Node(3);
		root3.left.right = new Node(1);
		Node root4 = addRow(root3, 1, 3);
		System.out.println(LotQueue.traverse(root4));
		// 4,2,null,1,1,3,null,null,1
	}
}
