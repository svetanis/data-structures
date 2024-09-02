package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BT and a number k
// find if the tree has a path
// from root-to-leaf such that
// the sum of all the node values
// of that path equals k

public final class RootToLeafPathIsGivenSum {

	public static boolean hasPath(Node node, int k) {
		// Time complexity: O(n)

		if (node == null) {
			return false;
		}

		int sum = k - node.data;

		// if we reach a leaf node and
		// sum becomes 0 then return true
		if (sum == 0 && isLeaf(node)) {
			return true;
		}
		// recursively call to traverse
		// the left and right sub-tree
		boolean isLeft = hasPath(node.left, sum);
		boolean isRight = hasPath(node.right, sum);
		// return true if any of the two
		// recursive call return true
		return isLeft || isRight;
	}

	public static boolean hasPath2(Node node, int sum) {
		// Time complexity: O(n)

		if (node == null) {
			return false;
		}

		// if the current node is a leaf &
		// its value == sum, found a path
		if (node.data == sum && isLeaf(node)) {
			return true;
		}

		// recursively call to traverse
		// the left and right sub-tree
		boolean isLeft = hasPath(node.left, sum - node.data);
		boolean isRight = hasPath(node.right, sum - node.data);
		// return true if any of the two
		// recursive call return true
		return isLeft || isRight;
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
