package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.bt.traversal.lot.LotQueue;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 99. Recover Binary Search Tree

public final class RecoverBST {
	// Time Complexity: O(n)

	private Node prev;
	private Node first;
	private Node second;

	public void recover(Node root) {
		inOrder(root);
		int temp = first.data;
		first.data = second.data;
		second.data = temp;
	}

	public void inOrder(Node root) {
		if (root == null) {
			return;
		}
		inOrder(root.left);
		if (prev != null && root.data < prev.data) {
			if (first == null) {
				first = prev;
			}
			second = root;
		}
		prev = root;
		inOrder(root.right);
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(3);
		root.left.right = newNode(2);
		RecoverBST r1 = new RecoverBST();
		r1.recover(root);
		System.out.println(LotQueue.traverse(root)); // 3, 1, 2

		Node root1 = new Node(3);
		root1.left = new Node(1);
		root1.right = new Node(4);
		root1.right.left = new Node(2);
		RecoverBST r2 = new RecoverBST();
		r2.recover(root1);
		System.out.println(LotQueue.traverse(root1)); // 2, 1, 4, 3
	}
}