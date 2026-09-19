package com.svetanis.datastructures.tree.binary.bt.vertical;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.java.base.collect.Multimaps.newMultimap;
import static com.svetanis.java.base.utils.Print.print;

import java.util.Queue;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.java.base.Pair;

// given a binary tree, perform vertical 
// traversal of it: print nodes of a binary tree
// in vertical order by assuming that the left and
// right child of a node makes 45 degree angle with 
// the parent

public final class VerticalOrderIterative {
	// Time Complexity: O(n)
	// Aux Space: O(n)

	public static ImmutableMultimap<Integer, Integer> verticalOrder(Node root) {
		Multimap<Integer, Integer> mm = LinkedHashMultimap.create();
		Queue<Pair<Node, Integer>> queue = newLinkedList();
		queue.offer(Pair.build(root, 0));
		while (!queue.isEmpty()) {
			Pair<Node, Integer> pair = queue.poll();
			int dist = pair.getRight();
			Node node = pair.getLeft();
			mm.put(dist, node.data);
			if (isNotNull(node.left)) {
				queue.offer(Pair.build(node.left, dist - 1));
			}
			if (isNotNull(node.right)) {
				queue.offer(Pair.build(node.right, dist + 1));
			}
		}
		return newMultimap(mm);
	}

	public static void main(String[] args) {
		// -2: [4] · -1: [2] · 0: [1, 5, 6] · 1: [3, 8, 10] · 2: [7, 11]
		// 3: [9, 12] -- and the recursive sibling prints that last column
		// [12, 9]. Within a column the queue hands nodes over shallowest
		// first, so this walk needs no sort and that one does
		Node root1 = tree1();
		print(verticalOrder(root1));
		System.out.println();
		// -1: [2, 7] · 0: [1, 5, 9] · 1: [3, 8] · 2: [6, 10]
		Node root2 = tree2();
		print(verticalOrder(root2));
	}

	private static Node tree2() {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.right.left = newNode(5);
		root.right.right = newNode(6);
		root.right.left.left = newNode(7);
		root.right.left.right = newNode(8);
		root.right.left.right.left = newNode(9);
		root.right.left.right.right = newNode(10);
		return root;
	}

	private static Node tree1() {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.left = newNode(6);
		root.right.right = newNode(7);
		root.right.left.right = newNode(8);
		root.right.right.right = newNode(9);
		root.right.right.left = newNode(10);
		root.right.right.left.right = newNode(11);
		root.right.right.left.right.right = newNode(12);
		return root;
	}

	// -2: [4]
	// -1: [2]
	// 0: [1, 5, 6]
	// 1: [3, 8, 10]
	// 2: [7, 11]
	// 3: [9, 12]
}
