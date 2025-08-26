package com.svetanis.datastructures.tree.binary.bt.view;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 545. Boundary of Binary Tree

// given a BT, return an array containing
// all the boundary nodes of the tree in  
// an anti-clockwise direction. 

// the boundary of a tree contains all nodes
// in the left view, all leaves, and all 
// nodes in the right view.
// there should not be any duplicate nodes

public final class BoundaryTraversalRecursive {

	public static ImmutableList<Node> boundary(Node root) {
		// Time Complexity: O(n)

		List<Node> list = newArrayList();
		if (isNotNull(root)) {
			list.add(root);
			// left boundary in top-down manner
			boundaryLeft(root.left, list);

			// all leaf nodes
			leaves(root.left, list);
			leaves(root.right, list);

			// right boundary in bottom-up manner
			boundaryRight(root.right, list);
		}
		return newList(list);
	}

	private static void boundaryLeft(Node node, List<Node> list) {
		if (isNotNull(node)) {
			if (isNotNull(node.left)) {
				// to ensure top down order, print the node
				// before calling itself for left subtree
				list.add(node);
				boundaryLeft(node.left, list);
			} else if (isNotNull(node.right)) {
				list.add(node);
				boundaryLeft(node.right, list);
			}
			// do nothing if it is a leaf node,
			// this way we avoid duplicates in output
		}
	}

	private static void boundaryRight(Node node, List<Node> list) {
		if (isNotNull(node)) {
			if (isNotNull(node.right)) {
				// to ensure bottom up order, first call for
				// right subtree, then print this node
				boundaryRight(node.right, list);
				list.add(node);
			} else if (isNotNull(node.left)) {
				boundaryRight(node.left, list);
				list.add(node);
			}
			// do nothing if it is a leaf node,
			// this way we avoid duplicates in output
		}
	}

	private static void leaves(Node node, List<Node> list) {
		if (isNull(node)) {
			return;
		}
		if (isLeaf(node)) {
			list.add(node);
		}
		leaves(node.left, list);
		leaves(node.right, list);
	}

	public static void main(String[] args) {
		Node root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.left = newNode(4);
		root1.left.right = newNode(5);
		root1.right.left = newNode(6);
		root1.right.right = newNode(7);
		root1.left.left.left = newNode(8);
		root1.left.left.right = newNode(9);
		root1.left.right.left = newNode(10);
		root1.right.left.left = newNode(11);
		root1.right.right.left = newNode(12);
		root1.right.right.right = newNode(13);

		print(boundary(root1)); // 1, 2, 4, 8, 9, 10, 11, 12, 13, 7, 3
		System.out.println();

		Node root2 = newNode(12);
		root2.left = newNode(7);
		root2.right = newNode(1);
		root2.left.left = newNode(4);
		root2.left.right = newNode(3);
		root2.left.left.left = newNode(9);
		root2.left.right.left = newNode(15);
		root2.right.left = newNode(10);
		root2.right.right = newNode(5);
		root2.right.right.left = newNode(6);

		print(boundary(root2)); // 12, 7, 4, 9, 15, 10, 6, 5, 1
		System.out.println();

		Node root3 = newNode(12);
		root3.right = newNode(1);
		root3.right.left = newNode(10);
		root3.right.right = newNode(5);
		print(boundary(root3)); // 12, 10, 5, 1
		System.out.println();
	}
}
