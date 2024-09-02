package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.Deque;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree and a number ‘S’, 
// find if the tree has a path from root-to-leaf 
// such that the sum of all the node values of that path equals ‘S’.

public final class RootToLeafPathGivenSum {

	public static ImmutableList<Integer> path(Node node, int k) {
		Deque<Integer> dq = new ArrayDeque<>();
		isPathSum(node, k, dq);
		return newList(dq);
	}

	private static boolean isPathSum(Node node, int k, Deque<Integer> dp) {
		// Time complexity: O(n)

		if (isNull(node)) {
			return false;
		}
		if (k == node.data && isLeaf(node)) {
			dp.addFirst(node.data);
			return true;
		}
		if (isPathSum(node.left, k - node.data, dp)) {
			dp.addFirst(node.data);
			return true;
		}
		if (isPathSum(node.right, k - node.data, dp)) {
			dp.addFirst(node.data);
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		// sum = 21 : 10->8->3
		// sum = 23 : 10->8->5
		// sum = 14 : 10->2->2

		Node root = newNode(10);
		root.left = newNode(8);
		root.right = newNode(2);
		root.left.left = newNode(3);
		root.left.right = newNode(5);
		root.right.left = newNode(2);

		inOrder(root);
		System.out.println();

		// given sum root-to-leaf path
		print(path(root, 21));
		print(path(root, 23));
		print(path(root, 14));
	}
}
