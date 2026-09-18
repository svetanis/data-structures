package com.svetanis.datastructures.tree.binary.bst.candidate;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 270. Closest Binary Search Tree Value

// Given BST and a target value. 
// Find the value that is closest to the target.

public final class ClosestToTarget {
	// Time Complexity: O(h)

	// LC 270 asks for the SMALLEST value when two are equally close, and
	// that is what the tie branch below is for. the three siblings named
	// ClosestToTargetIterative / Recursive / Recursive2 answer the looser
	// "a node with minimum absolute difference" and keep whichever one
	// the descent met first, so for 6 above 4 and target 5 they answer 6

	// the descent does not meet candidates in order of distance -- it can
	// pass the closest node and then record worse ones -- so every node
	// is compared against the best so far instead of overwriting it

	public static int closestToTarget(Node root, double target) {
		int closest = root.data;
		double minDiff = Double.MAX_VALUE;
		while (root != null) {
			double currDiff = Math.abs(target - root.data);
			// a tie needs the value comparison: `<=` alone keeps the last one met
			boolean equals = currDiff == minDiff && root.data < closest;
			if (currDiff < minDiff || equals) {
				minDiff = currDiff;
				closest = root.data;
			}
			if (root.data > target) {
				root = root.left;
			} else {
				root = root.right;
			}
		}
		return closest;
	}

	public static void main(String[] args) {
		Node root = newNode(4);
		root.left = newNode(2);
		root.right = newNode(5);
		root.left.left = newNode(1);
		root.left.right = newNode(3);
		System.out.println(closestToTarget(root, 3.714286)); // 4 -- the first node met, then passed
		System.out.println(closestToTarget(root, 4.5)); // 4 -- tie, smaller met first
		System.out.println(closestToTarget(root, 3.5)); // 3 -- tie, larger met first
		System.out.println(closestToTarget(root, 2.5)); // 2 -- tie, smaller met first
	}
}
