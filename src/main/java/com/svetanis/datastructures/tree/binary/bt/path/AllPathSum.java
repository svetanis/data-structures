package com.svetanis.datastructures.tree.binary.bt.path;

import static com.google.common.base.Verify.verify;
import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree and a number ‘S’, 
// find all paths in the tree such that 
// the sum of all the node values of each path equals ‘S’. 
// Please note that the paths can start or end at any node 
// but all paths must follow direction from parent to child (top to bottom).

public final class AllPathSum {

	public static ImmutableList<ImmutableList<Integer>> pathCount(Node root, int k) {
		// Worst Case Time Complexity (skewed tree) : O(n^2)
		// Best Case Time Complexity (balanced tree): O(n log n)

		List<ImmutableList<Integer>> paths = newArrayList();
		List<Integer> path = new LinkedList<>();
		int count = pathCount(root, k, path, paths);
		verify(count == paths.size());
		return newList(paths);
	}

	private static int pathCount(Node node, int k, List<Integer> path, List<ImmutableList<Integer>> paths) {
		if (isNull(node)) {
			return 0;
		}
		int sum = 0;
		int count = 0;
		// add current node to the path
		path.add(node.data);
		// find the sums of all sub-paths in the current path
		List<Integer> list = newArrayList();
		ListIterator<Integer> iter = path.listIterator(path.size());
		while (iter.hasPrevious()) {
			int prev = iter.previous();
			list.add(prev);
			sum += prev;
			// if the sum of any sub-path equals
			// target increment the path count
			if (sum == k) {
				count++;
				paths.add(newList(list));
				list.clear();
			}
		}
		// traverse the left subtree
		count += pathCount(node.left, k, path, paths);
		// traverse the right subtree
		count += pathCount(node.right, k, path, paths);
		// backtrack: remove the current node from the path
		// while going up the recursive call stack
		path.remove(path.size() - 1);
		return count;
	}

	public static void main(String[] args) {
		Node root = newNode(12);
		root.left = newNode(7);
		root.right = newNode(1);
		root.left.left = newNode(4);
		root.right.left = newNode(10);
		root.right.right = newNode(5);
		System.out.println(pathCount(root, 11));
	}
}
