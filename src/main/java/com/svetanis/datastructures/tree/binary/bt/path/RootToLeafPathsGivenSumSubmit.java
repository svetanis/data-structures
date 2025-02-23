package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 113. Path Sum II

// Given a binary tree and a number ‘S’, 
// find all paths from root-to-leaf such that
// the sum of all the node values of each path equals ‘S’.

public final class RootToLeafPathsGivenSumSubmit {
	// Time complexity: O(n)

	public static List<List<Integer>> paths(Node node, int target) {
		Deque<Integer> path = new ArrayDeque<>();
		List<List<Integer>> paths = new ArrayList<>();
		paths(node, target, path, paths);
		return paths;
	}

	private static void paths(Node node, int target, Deque<Integer> path, List<List<Integer>> paths) {
		if (node == null) {
			return;
		}
		// add the current node to the path
		path.addLast(node.data);
		// if the current node is a leaf &
		// its value is equal to sum,
		// save the current path
		if (node.left == null && node.right == null && target == node.data) {
			paths.add(new ArrayList<>(path));
		} else {
			// traverse left subtree
			paths(node.left, target - node.data, path, paths);
			// traverse right subtree
			paths(node.right, target - node.data, path, paths);
		}
		// backtrack: remove the current node from the path
		// while going up the recursive call stack
		path.removeLast();
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
		System.out.println(paths(root, 21));
	}
}
