package com.svetanis.datastructures.tree.binary.bt.reverse;

import static com.svetanis.datastructures.tree.binary.bt.traversal.InOrderRecursive.inorder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a perfect binary tree,
// reverse the node values at 
// each odd level of the tree

public final class ReverseAlternateLevels {

	public static void reverseAlternate(Node root) {
		// Time Complexity: O(n)
		// Auxiliary Space: O(log n)

		if (isNull(root)) {
			return;
		}
		reverseAlternate(root.left, root.right, 0);
	}

	private static void reverseAlternate(Node root1, Node root2, int level) {
		if (isNull(root1) || isNull(root2)) {
			return;
		}

		if (level % 2 == 0) {
			int temp = root1.data;
			root1.data = root2.data;
			root2.data = temp;
		}
		reverseAlternate(root1.left, root2.right, level + 1);
		reverseAlternate(root1.right, root2.left, level + 1);
	}

	public static void main(String[] args) {
		Node root = newNode('a');
		root.left = newNode('b');
		root.right = newNode('c');
		root.left.left = newNode('d');
		root.left.right = newNode('e');
		root.right.left = newNode('f');
		root.right.right = newNode('g');
		root.left.left.left = newNode('h');
		root.left.left.right = newNode('i');
		root.left.right.left = newNode('j');
		root.left.right.right = newNode('k');
		root.right.left.left = newNode('l');
		root.right.left.right = newNode('m');
		root.right.right.left = newNode('n');
		root.right.right.right = newNode('o');

		inorder(root);
		System.out.println();
		reverseAlternate(root);
		inorder(root);
	}
}
