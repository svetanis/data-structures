package com.svetanis.datastructures.tree.binary.bst.kclosest;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;
import static java.lang.Math.abs;

import java.util.ArrayDeque;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 272. Closest Binary Search Tree Value II 

// Given BST and a target value. 
// Find k nodes that have values closest to target.
// Sort the output by value.
// Don't convert the BST to a list

public final class KClosestToTargetIterator {

	public static ImmutableList<Integer> kClosest(Node root, int k, double target) {
		BstIterator iter = new BstIterator(root);
		Queue<Integer> queue = new ArrayDeque<>();
		while (iter.hasNext()) {
			int val = iter.next();
			if (queue.size() < k) {
				queue.offer(val);
			} else if (abs(queue.peek() - target) > abs(val - target)) {
				queue.poll();
				queue.offer(val);
			}
		}
		return newList(queue);
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
