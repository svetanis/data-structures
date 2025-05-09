package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 701. Insert into a Binary Search Tree

public final class InsertIntoBst {
	// Time Complexity: O(log n)

	public static Node insert(Node root, int val) {
		if (root == null) {
			return new Node(val);
		}
		if (root.data < val) {
			root.right = insert(root.right, val);
		} else {
			root.left = insert(root.left, val);
		}
		return root;
	}

	public static void main(String[] args) {
		Node root = newNode(4);
		root.left = newNode(2);
		root.right = newNode(7);
		root.left.left = newNode(1);
		root.left.right = newNode(3);

		Nodes.preOrder(insert(root, 5)); //
		System.out.println();

		Node root2 = newNode(5);
		root2.left = newNode(2);
		root2.right = newNode(7);
		root2.left.left = newNode(1);
		root2.left.right = newNode(3);
		root2.left.right.right = newNode(4);
		Nodes.preOrder(insert(root2, 25));
		System.out.println();
	}
}
