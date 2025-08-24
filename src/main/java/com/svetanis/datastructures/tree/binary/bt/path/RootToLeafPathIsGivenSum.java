package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 112. Path Sum

// given a BT and a number k
// find if the tree has a path
// from root-to-leaf such that
// the sum of all the node values
// of that path equals k

public final class RootToLeafPathIsGivenSum {
	// Time Complexity: O(n)
	// Space Complexity: O(log n)

	public static boolean hasPath(Node root, int target) {
		if (root == null) {
			return false;
		}
		if (isLeaf(root)) {
			return target == root.data;
		}
		target -= root.data;
		boolean left = hasPath(root.left, target);
		boolean right = hasPath(root.right, target);
		return left || right;
	}

	public static boolean hasPath1(Node root, int target) {
		if (root == null) {
			return false;
		}
		int sum = target - root.data;
		// if we reach a leaf node and
		// sum becomes 0 then return true
		if (sum == 0 && isLeaf(root)) {
			return true;
		}
		boolean left = hasPath(root.left, sum);
		boolean right = hasPath(root.right, sum);
		return left || right;
	}

	public static boolean hasPath2(Node root, int sum) {
		if (root == null) {
			return false;
		}
		// if the current node is a leaf &
		// its value == sum, found a path
		if (root.data == sum && isLeaf(root)) {
			return true;
		}
		boolean left = hasPath(root.left, sum - root.data);
		boolean right = hasPath(root.right, sum - root.data);
		return left || right;
	}

	public static void main(String[] args) {
		// sum = 21 : 10->8->3
		// sum = 23 : 10->8->5
		// sum = 14 : 10->2->2

		Node root = new Node(10);
		root.left = new Node(8);
		root.right = new Node(2);
		root.left.left = new Node(3);
		root.left.right = new Node(5);
		root.right.left = new Node(2);

		System.out.println(hasPath(root, 21));
		System.out.println(hasPath2(root, 21));
	}
}
