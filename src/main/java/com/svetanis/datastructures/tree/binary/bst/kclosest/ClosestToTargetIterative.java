package com.svetanis.datastructures.tree.binary.bst.kclosest;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.abs;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BST and a target node K
// find the node with minimum absolute 
// difference with given target K

public final class ClosestToTargetIterative {
	// Time complexity: O(h)

	public static int closestToK(Node root, int k) {
		if (isNull(root)) {
			return -1;
		}

		int key = -1;
		int min = MAX_VALUE;
		while (isNotNull(root)) {
			if (root.data == k) {
				return root.data;
			}
			int diff = abs(k - root.data);
			if (min > diff) {
				min = diff;
				key = root.data;
			}
			if (k < root.data) {
				root = root.left;
			} else {
				root = root.right;
			}
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
