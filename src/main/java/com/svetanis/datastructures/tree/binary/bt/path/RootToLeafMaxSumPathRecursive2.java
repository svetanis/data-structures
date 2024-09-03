package com.svetanis.datastructures.tree.binary.bt.path;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.max;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, find 
// the root-to-leaf path
// with the maximum sum.

public final class RootToLeafMaxSumPathRecursive2 {

	public static ImmutableList<Integer> maxSumPath(Node root) {
		// Time complexity: O(n)

		Deque<Integer> dq = new ArrayDeque<>();
		List<Integer> path = newArrayList();
		AtomicInteger max = new AtomicInteger(MIN_VALUE);
		paths(root, max, dq, path);
		return newList(path);
	}

	private static int paths(Node root, AtomicInteger max, Deque<Integer> dq, List<Integer> path) {
		if (isNull(root)) {
			return 0;
		}
		// add the current node to the path
		dq.addLast(root.data);
		// traverse left subtree
		int left = max(0, paths(root.left, max, dq, path));
		// traverse right subtree
		int right = max(0, paths(root.right, max, dq, path));
		// max sum path passing through current node
		int sum = root.data + left + right;

		// if the current node is a leaf
		// & the sum is greater than max
		// save the current path
		if (isLeaf(root)) {
			if (sum > max.get()) {
				max.set(sum);
				path.clear();
				path.addAll(dq);
			}
		}
		// backtrack: remove the current node from the path
		// while going up the recursive call stack
		int val = dq.removeLast();
		return sum - val;
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
