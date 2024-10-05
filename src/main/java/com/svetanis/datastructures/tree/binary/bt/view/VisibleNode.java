package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.max;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// in a binary tree, a node is
// labeled as visible if, on the
// path from the root to that node,
// there isn't any node with a value
// higher than this node's value

// the root is always visible since
// there are no other nodes between 
// the root and itself. given a binary
// tree, count the number of visible nodes

public final class VisibleNode {
	// Time Complexity: O(n)
	// Space Complexity: O(h)

	public static int countVisible(Node root) {
		return dfs(root, MIN_VALUE);
	}

	private static int dfs(Node node, int max) {
		if (isNull(node)) {
			return 0;
		}
		int count = 0;
		if (node.data >= max) {
			count += 1;
		}
		count += dfs(node.left, max(max, node.data));
		count += dfs(node.right, max(max, node.data));
		return count;
	}

	public static void main(String[] args) {
		Node root = newNode(5);
		root.left = newNode(4);
		root.right = newNode(6);
		root.left.left = newNode(3);
		root.left.right = newNode(8);
		System.out.println(countVisible(root)); // 3

		Node root1 = newNode(-100);
		root1.left = newNode(-500);
		root1.left.left = newNode(-50);
		System.out.println(countVisible(root1)); // 2

		Node root3 = newNode(9);
		root3.left = newNode(8);
		root3.right = newNode(6);
		root3.left.left = newNode(11);
		root3.left.right = newNode(20);
		System.out.println(countVisible(root3)); // 3

		Node root4 = newNode(5);
		root4.left = newNode(8);
		root4.right = newNode(6);
		root4.left.left = newNode(3);
		root4.left.right = newNode(8);
		System.out.println(countVisible(root4)); // 4

		Node root5 = newNode(3);
		root5.left = newNode(1);
		root5.right = newNode(1);
		root5.left.left = newNode(3);
		root5.left.right = newNode(3);
		root5.right.left = newNode(2);
		root5.right.left.left = newNode(5);
		System.out.println(countVisible(root5)); // 4

		Node root6 = newNode(5);
		root6.left = newNode(8);
		root6.right = newNode(6);
		root6.left.left = newNode(3);
		root6.left.right = newNode(9);
		root6.left.right.left = newNode(7);
		root6.left.right.left.left = newNode(8);
		System.out.println(countVisible(root6)); // 4
	}
}