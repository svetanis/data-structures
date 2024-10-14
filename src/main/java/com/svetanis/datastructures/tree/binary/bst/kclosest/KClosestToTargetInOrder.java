package com.svetanis.datastructures.tree.binary.bst.kclosest;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;
import static java.lang.Math.abs;

import java.util.ArrayDeque;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given BST and a target value. 
// Find k nodes that have values closest to target.

public final class KClosestToTargetInOrder {

	public static ImmutableList<Integer> kClosest(Node root, int k, double target) {
		Queue<Integer> queue = new ArrayDeque<>();
		kClosest(root, k, target, queue);
		return newList(queue);
	}

	private static void kClosest(Node root, int k, double target, Queue<Integer> queue) {
		if (isNull(root)) {
			return;
		}
		kClosest(root.left, k, target, queue);
		if (queue.size() < k) {
			queue.offer(root.data);
		} else if (abs(queue.peek() - target) > abs(root.data - target)) {
			queue.poll();
			queue.offer(root.data);
		}
		kClosest(root.right, k, target, queue);
	}

	public static void main(String[] args) {
		Node root = newNode(20);
		root.left = newNode(8);
		root.right = newNode(22);
		root.left.left = newNode(4);
		root.left.right = newNode(12);
		root.left.right.left = newNode(10);
		root.left.right.right = newNode(14);
		print(kClosest(root, 3, 11.5)); // 10, 12, 14

		Node root2 = newNode(8);
		root2.left = newNode(5);
		root2.right = newNode(10);
		root2.left.left = newNode(2);
		root2.left.right = newNode(6);
		root2.left.left.right = newNode(3);
		root2.right.right = newNode(14);
		print(kClosest(root2, 4, 7)); // 5, 6, 8, 10
	}
}
