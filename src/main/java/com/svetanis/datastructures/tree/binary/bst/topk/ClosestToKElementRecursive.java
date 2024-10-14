package com.svetanis.datastructures.tree.binary.bst.topk;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;

import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and a target node K
// find the node with minimum absolute 
// difference with given target K

public final class ClosestToKElementRecursive {

	public static int closestToK(Node root, int k) {
		AtomicInteger min = new AtomicInteger(MAX_VALUE);
		AtomicInteger key = new AtomicInteger(-1);
		closestToK(root, k, min, key);
		return key.get();
	}

	public static void closestToK(Node root, int k, AtomicInteger min, AtomicInteger key) {
		// Time complexity: O(h)

		if (isNull(root)) {
			return;
		}

		if (root.data == k) {
			key.set(root.data);
			return;
		}

		int diff = abs(k - root.data);
		if (min.get() > diff) {
			min.set(diff);
			key.set(root.data);
		}

		if (k < root.data) {
			closestToK(root.left, k, min, key);
		} else {
			closestToK(root.right, k, min, key);
		}
	}

	public static void main(String[] args) {
		Node root = newNode(9);
		root.left = newNode(4);
		root.right = newNode(17);
		root.left.left = newNode(3);
		root.left.right = newNode(6);
		root.left.right.left = newNode(5);
		root.left.right.right = newNode(7);
		root.right.right = newNode(22);
		root.right.right.left = newNode(20);
		System.out.println(closestToK(root, 4)); // 4
		System.out.println(closestToK(root, 18)); // 17
		System.out.println(closestToK(root, 12)); // 9
		System.out.println(closestToK(root, 2)); // 3
	}
}
