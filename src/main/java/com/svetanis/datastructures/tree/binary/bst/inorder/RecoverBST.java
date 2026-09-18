package com.svetanis.datastructures.tree.binary.bst.inorder;

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
		// the three fields survive the traversal, so a second call would
		// carry the first tree's prev into the new one
		this.prev = null;
		this.first = null;
		this.second = null;
		inOrder(root);
		// a tree that is already a BST has no pair out of order, so there
		// is nothing to swap. reading first.data here threw instead
		if (first == null || second == null) {
			return;
		}
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

		// an already-valid tree, and then the same object again. the
		// first of these threw on first.data and the second saw the
		// previous tree's last node as its own predecessor
		Node valid = newNode(2);
		valid.left = newNode(1);
		valid.right = newNode(3);
		r2.recover(valid);
		System.out.println(LotQueue.traverse(valid)); // 2, 1, 3
		r2.recover(valid);
		System.out.println(LotQueue.traverse(valid)); // 2, 1, 3
	}
}