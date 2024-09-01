package com.svetanis.datastructures.tree.binary.bt.view;

import static com.google.common.collect.Maps.newTreeMap;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given a BT, print the top view of it

// top view of a BT is the set of nodes visible
// when the tree is viewed from the top

// a node x is there in output if x is 
// the topmost node at its horizontal distance

// horizontal distance of a child of a node x
// is equal to horizontal distance of x minus 1
// and that of right child is horizontal distance
// of x plus 1

public final class TopViewIterative {

	public static ImmutableList<Integer> topView(Node root) {
		// Time Complexity: O(n)

		// base case
		if (root == null) {
			return newList();
		}

		Queue<Item> queue = new ArrayDeque<>();
		Map<Integer, Integer> map = newTreeMap();
		// horizontal distance of root is 0
		queue.offer(new Item(root, 0));

		// standard BFS or
		// level order traversal loop
		while (!queue.isEmpty()) {
			// remove the front item
			// and get its details
			Item item = queue.poll();
			Node node = item.node;
			int hd = item.hd;

			// if this is the first node
			// at its horizontal dist,
			// then this node is in top view
			if (!map.containsKey(hd)) {
				map.put(hd, node.data);
			}

			// enqueue left child of current node
			if (isNotNull(node.left)) {
				queue.offer(new Item(node.left, hd - 1));
			}
			// enqueue right child of current node
			if (isNotNull(node.right)) {
				queue.offer(new Item(node.right, hd + 1));
			}
		}
		return newList(map.values());
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.left = newNode(6);
		root.right.right = newNode(7);
		print(topView(root)); // 4 2 1 3 7

		Node root2 = newNode(1);
		root2.left = newNode(2);
		root2.right = newNode(3);
		root2.left.right = newNode(4);
		root2.left.right.right = newNode(5);
		root2.left.right.right.right = newNode(6);
		print(topView(root2)); // 2 1 3 6
	}
}
