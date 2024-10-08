package com.svetanis.datastructures.tree.binary.bt.path;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLists;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, print all of the paths from the root to leaf nodes.

public final class RootToLeafPathsRecursive {

	public static ImmutableList<ImmutableList<Integer>> paths(Node root) {
		// Time complexity: O(n)

		Deque<Integer> path = new ArrayDeque<>();
		List<ImmutableList<Integer>> paths = newArrayList();
		paths(root, path, paths);
		return newList(paths);
	}

	private static void paths(Node node, Deque<Integer> path, List<ImmutableList<Integer>> paths) {
		if (isNull(node)) {
			return;
		}
		// add the current node to the path
		path.addLast(node.data);
		// if the current node is a leaf
		// save the current path
		if (isLeaf(node)) {
			paths.add(newList(path));
		} else {
			// traverse left subtree
			paths(node.left, path, paths);
			// traverse right subtree
			paths(node.right, path, paths);
		}
		// backtrack: remove the current node from the path
		// while going up the recursive call stack
		path.removeLast();
	}

	public static void main(String[] args) {
		// 10->8->3
		// 10->8->5
		// 10->2->2
		Node root = newNode(10);
		root.left = newNode(8);
		root.right = newNode(2);
		root.left.left = newNode(3);
		root.left.right = newNode(5);
		root.right.left = newNode(2);

		inOrder(root);
		System.out.println();

		// print Root-to-leaf path
		System.out.println("Root-to-leaf paths: ");
		printLists(paths(root));
		System.out.println();
	}
}
