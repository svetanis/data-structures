package com.svetanis.datastructures.tree.binary.bst.topk;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 230. K-th Smallest BST

// given a BST and and integer k,
// find k'th smallest element in BST

// K-SMALLEST : INORDER TRAVERSAL
// in-order traversal of BST sorts
// all nodes in ascending order
// while doing traversal, count nodes
// visited so far. stop when the count
// becomes equal to k

public final class KthSmallestIterative {
	// Time complexity: O(h + k)

	public static int kthSmallest(Node root, int k) {
		Node curr = root;
		Deque<Node> dq = new ArrayDeque<>();
		while (curr != null || !dq.isEmpty()) {
			while (curr != null) {
				dq.push(curr);
				curr = curr.left;
			}
			curr = dq.pop();
			if (--k == 0) {
				return curr.data;
			}
			curr = curr.right;
		}
		return -1;
	}

	public static void main(String[] args) {
		Node root = newNode(20);
		root.left = newNode(8);
		root.right = newNode(22);
		root.left.left = newNode(4);
		root.left.right = newNode(12);
		root.left.right.left = newNode(10);
		root.left.right.right = newNode(14);
		System.out.println(kthSmallest(root, 3));
	}
}
