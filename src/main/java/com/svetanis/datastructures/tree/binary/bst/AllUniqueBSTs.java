package com.svetanis.datastructures.tree.binary.bst;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.preOrder;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a number n find all
// structurally unique BSTs
// that can store values 1 to n

public final class AllUniqueBSTs {
	// Time Complexity: O(n * 2^n)
	// Space Complexity: O(n * 2^n)

	public static List<Node> construct(int n) {
		if (n <= 0) {
			return newList();
		}
		return construct(1, n);
	}

	private static List<Node> construct(int start, int end) {
		List<Node> list = newArrayList();
		// if start > end then subtree is empty
		if (start > end) {
			list.add(null);
			return list;
		}

		for (int i = start; i <= end; i++) {
			List<Node> left = construct(start, i - 1);
			List<Node> right = construct(i + 1, end);
			// loop through all left and right subtrees
			// and connect them to ith root
			for (int j = 0; j < left.size(); j++) {
				for (int k = 0; k < right.size(); k++) {
					Node node = new Node(i); // make value i as root
					node.left = left.get(j); // connect left subtree
					node.right = right.get(k); // connect right subtree
					list.add(node);
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		// construct all possible BTs
		List<Node> trees = construct(3);
		for (int i = 0; i < trees.size(); i++) {
			preOrder(trees.get(i));
			System.out.println();
		}
	}
}
