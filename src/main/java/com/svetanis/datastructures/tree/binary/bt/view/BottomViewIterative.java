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

// given a BT, print the bottom view 
// from left to right

// a node x is there in output 
// if x the bottom-most node at
// its horizontal distance

// horizontal distance of left child
// of a node x is equal to horizontal
// distance of x minus 1, and that of
// right child is horizontal distance 
// of x plus 1

public final class BottomViewIterative {

	public static ImmutableList<Integer> bottomView(Node root) {
		// Time Complexity: O(n)

		// base case
		if (root == null) {
			return newList();
		}

		Queue<Item> queue = new ArrayDeque<>();
		Map<Integer, Integer> map = newTreeMap();

		// horizontal distance of root is 0
		queue.offer(new Item(root, 0));

		// standard BFS or level order traversal loop
		while (!queue.isEmpty()) {
			// remove the front item and get its details
			Item item = queue.poll();
			Node node = item.node;
			int hd = item.hd;

			// put the dequeued node to TreeMap
			// having key at horizontal distance
			// every time we find a node having
			// same horizontal dist we need to
			// replace the data in the map
			map.put(hd, node.data);

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
		Node root = newNode(20);
		root.left = newNode(8);
		root.right = newNode(22);
		root.left.left = newNode(5);
		root.left.right = newNode(3);
		root.left.right.left = newNode(10);
		root.left.right.right = newNode(14);
		root.right.right = newNode(25);
		print(bottomView(root)); // 5 10 3 14 25

		Node root2 = newNode(20);
		root2.left = newNode(8);
		root2.right = newNode(22);
		root2.left.left = newNode(5);
		root2.left.right = newNode(3);
		root2.left.right.left = newNode(10);
		root2.left.right.right = newNode(14);
		root2.right.left = newNode(4);
		root2.right.right = newNode(25);
		print(bottomView(root2)); // 5 10 4 14 25
	}
}