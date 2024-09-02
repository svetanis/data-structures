package com.svetanis.datastructures.tree.binary.bt.path;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLists;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree and a number ‘S’, 
// find all paths from root-to-leaf such that
// the sum of all the node values of each path equals ‘S’.

public final class RootToLeafPathsGivenSum {

	public static ImmutableList<ImmutableList<Integer>> paths(Node node, int sum) {
		List<Integer> path = newArrayList();
		List<ImmutableList<Integer>> paths = newArrayList();
		paths(node, sum, path, paths);
		return newList(paths);
	}

	private static void paths(Node node, int sum, List<Integer> path, List<ImmutableList<Integer>> paths) {
		// Time complexity: O(n)

		if (isNull(node)) {
			return;
		}
		// add the current node to the path
		path.add(node.data);
		// if the current node is a leaf &
		// its value is equal to sum,
		// save the current path
		if (isLeaf(node) && sum == node.data) {
			paths.add(newList(path));
		} else {
			// traverse left subtree
			paths(node.left, sum - node.data, path, paths);
			// traverse right subtree
			paths(node.right, sum - node.data, path, paths);
		}
		// backtrack: remove the current node from the path
		// while going up the recursive call stack
		path.remove(path.size() - 1);
	}

	public static void main(String[] args) {
		// sum = 21 : 10->8->3
		// sum = 23 : 10->8->5
		// sum = 21 : 10->9->2

		Node root = newNode(10);
		root.left = newNode(8);
		root.right = newNode(9);
		root.left.left = newNode(3);
		root.left.right = newNode(5);
		root.right.left = newNode(2);

		inOrder(root);
		System.out.println();

		// given sum root-to-leaf path
		printLists(paths(root, 21));
	}
}
