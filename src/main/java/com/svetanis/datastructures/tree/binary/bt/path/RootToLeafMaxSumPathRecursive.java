package com.svetanis.datastructures.tree.binary.bt.path;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static java.lang.Integer.MIN_VALUE;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, find 
// the root-to-leaf path
// with the maximum sum.

public final class RootToLeafMaxSumPathRecursive {

	public static ImmutableList<Integer> maxSumPath(Node root) {
		// Time complexity: O(n)

		Deque<Integer> dq = new ArrayDeque<>();
		List<Integer> path = newArrayList();
		AtomicInteger max = new AtomicInteger(MIN_VALUE);
		paths(root, max, dq, path);
		return newList(path);
	}

	private static void paths(Node node, AtomicInteger max, Deque<Integer> dq, List<Integer> path) {
		if (isNull(node)) {
			return;
		}
		// add the current node to the path
		dq.addLast(node.data);
		// if the current node is a leaf
		// & the sum is greater than max
		// save the current path
		if (isLeaf(node)) {
			int sum = dq.stream().reduce(0, Integer::sum);
			if (sum > max.intValue()) {
				max.set(sum);
				path.clear();
				path.addAll(dq);
			}
		} else {
			// traverse left subtree
			paths(node.left, max, dq, path);
			// traverse right subtree
			paths(node.right, max, dq, path);
		}
		// backtrack: remove the current node from the path
		// while going up the recursive call stack
		dq.removeLast();
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

		// print Root-to-leaf max sum path
		System.out.println("Root-to-leaf max sum path: ");
		System.out.println(maxSumPath(root));
	}
}
