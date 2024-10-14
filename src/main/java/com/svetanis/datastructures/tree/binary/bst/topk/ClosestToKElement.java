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

public final class ClosestToKElement {
	// Time complexity: O(log n)

	public static int closestToK(Node root, int k) {
		AtomicInteger min = new AtomicInteger(MAX_VALUE);
		return closestToK(root, k, -1, min);
	}

	public static int closestToK(Node root, int k, int key, AtomicInteger min) {
		if (isNull(root)) {
			return key;
		}

		if (root.data == k) {
			return root.data;
		}
		int diff = abs(k - root.data);
		if (min.get() > diff) {
			min.set(diff);
			key = root.data;
		}

		if (k < root.data) {
			key = closestToK(root.left, k, key, min);
		} else {
			key = closestToK(root.right, k, key, min);
		}
		return key;
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
