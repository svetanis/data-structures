package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.LinkedList;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Level order successor of a node
//
// Input: root of a binary tree and a value key (not a LeetCode problem). The level order successor
// of a node is the node listed right after it in the level order traversal (level by level, left
// to right). Returns that node, or null when key is not in the tree, is the last node in level
// order, or the tree is empty. With a repeated value the first one in level order counts.
//
// The one idea: one queue entry is a node waiting to be listed. The queue always holds the nodes
// that come after the one just taken, in level order. So run the traversal; when the node just
// taken off has the value key, queue its children as usual and stop: the node at the front of
// the queue is the next one in level order. If the key never appears the queue runs dry and
// the answer is null.
//
// Time: O(n) -- at worst every node is queued and taken off once.
// Space: O(w) -- the queue holds about two levels, w = the widest level (up to n / 2).

public final class LevelOrderSuccessor {

	public static TreeNode lot(TreeNode root, int key) {
		if (isNull(root)) {
			return null;
		}
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			TreeNode node = queue.poll(); // TAKE: the next node in level order
			if (isNotNull(node.left)) {
				queue.offer(node.left); // QUEUE: its children, so they sit behind everything listed so far
			}
			if (isNotNull(node.right)) {
				queue.offer(node.right);
			}
			if (node.val == key) { // FOUND: stop with the successor at the front
				break;
			}
		}
		return queue.peek(); // RETURN: front of the queue; null if empty
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.right = newNode(6);

		System.out.println(lot(root, 4)); // 5
		System.out.println(lot(root, 6)); // null -- the last node in level order
	}
}
