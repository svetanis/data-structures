package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 111. Minimum Depth of Binary Tree

// Find the minimum depth of a binary tree. 
// The min depth is the number of nodes along the shortest 
// path from the root node to the nearest leaf node.

// NODES, not edges -- so a single node is 1, not 0. The two differ by
// exactly one and every test small enough to check by hand still passes,
// which is why the count and the sentence above it drifted apart

public final class MinDepth {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int lot(Node root) {
		// an empty tree is 0 levels deep. Without this, the null goes
		// into the queue and isLeaf reads through it on the first poll
		if (isNull(root)) {
			return 0;
		}
		int minDepth = 0;
		Queue<Node> queue = newLinkedList();
		queue.offer(root);

		while (!queue.isEmpty()) {
			// count the level BEFORE draining it: the nodes about to be
			// polled are minDepth nodes from the root, root included
			minDepth++;
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				Node node = queue.poll();
				if (isLeaf(node)) {
					return minDepth;
				}
				if (isNotNull(node.left)) {
					queue.offer(node.left);
				}
				if (isNotNull(node.right)) {
					queue.offer(node.right);
				}
			}
		}
		return minDepth;
	}

	public static void main(String[] args) {
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(4);
		root.left.right = new Node(5);
		root.right.right = new Node(6);
		System.out.println(lot(root)); // 3 -- NODES: root, 3, 6
	}
}
