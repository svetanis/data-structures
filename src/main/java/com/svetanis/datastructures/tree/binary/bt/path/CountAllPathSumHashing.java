package com.svetanis.datastructures.tree.binary.bt.path;

import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree and a number ‘S’, 
// find all paths in the tree such that 
// the sum of all the node values of each path equals ‘S’. 
// Please note that the paths can start or end at any node 
// but all paths must follow direction from parent to child (top to bottom).

public final class CountAllPathSumHashing {

	public static int pathCount(Node root, int k) {
		// Time Complexity: O(n)
		// Space Complexity: O(n)

		Map<Integer, Integer> map = newHashMap();
		return pathCount(root, k, 0, map);
	}

	private static int pathCount(Node node, int k, int sum, Map<Integer, Integer> map) {
		if (isNull(node)) {
			return 0;
		}
		int count = 0;
		// prefix sum: sum of all node
		// values from root to current node
		sum += node.data;

		// base case
		if (sum == k) {
			count++;
		}
		// if within the root to current node path
		// there is a valid path, then
		// sum - old = target or
		// sum - target = old
		count += map.getOrDefault(sum - k, 0);
		// store the number of times the prefix
		// sum has occurred so far
		map.put(sum, map.getOrDefault(sum, 0) + 1);

		// traverse the left subtree
		count += pathCount(node.left, k, sum, map);
		// traverse the right subtree
		count += pathCount(node.right, k, sum, map);
		// backtrack: remove the current path sum from the map
		// while going up the recursive call stack
		map.put(sum, map.getOrDefault(sum, 1) - 1);
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
