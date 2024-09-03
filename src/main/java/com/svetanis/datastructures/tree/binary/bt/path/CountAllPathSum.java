package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree and a number ‘S’, 
// find all paths in the tree such that 
// the sum of all the node values of each path equals ‘S’. 
// Please note that the paths can start or end at any node 
// but all paths must follow direction from parent to child (top to bottom).

public final class CountAllPathSum {

	public static int pathCount(Node root, int k) {
		// Worst Case Time Complexity (skewed tree) : O(n^2)
		// Best Case Time Complexity (balanced tree): O(n log n)

		List<Integer> list = new LinkedList<>();
		return pathCount(root, k, list);
	}

	private static int pathCount(Node node, int k, List<Integer> path) {
		if (isNull(node)) {
			return 0;
		}
		int sum = 0;
		int count = 0;
		// add current node to the path
		path.add(node.data);
		// find the sums of all sub-paths in the current path
		ListIterator<Integer> iter = path.listIterator(path.size());
		while (iter.hasPrevious()) {
			sum += iter.previous();
			// if the sum of any sub-path equals
			// target increment the path count
			if (sum == k) {
				count++;
			}
		}
		// traverse the left subtree
		count += pathCount(node.left, k, path);
		// traverse the right subtree
		count += pathCount(node.right, k, path);
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
