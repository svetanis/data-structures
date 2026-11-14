package com.svetanis.datastructures.tree.binary.bt.cousins;

import static com.svetanis.datastructures.tree.binary.bt.Level.level;
import static com.svetanis.datastructures.tree.binary.bt.cousins.Siblings.siblings;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 993. Cousins in Binary Tree
//
// Input: the root of a binary tree with unique values, and two nodes of it, given as values (x and
// y) or, in cousins(root, a, b), as the nodes themselves. Two nodes are cousins when they are on
// the same level but have different parents. Returns whether the two nodes are cousins. On
// LeetCode: 2 <= nodes <= 100, 1 <= Node.val <= 100, all values unique, x != y, both in the tree.
// (Outside that: an empty tree returns false in areCousins and true in cousinsHashing and
// cousinsInOrder; a value that is not in the tree makes depth.get(...) throw in cousinsHashing
// and keeps level 1 and no parent in cousinsInOrder. In cousins(root, a, b) both nodes must be in
// the tree, and the level of a node is looked up by its value.)
//
// The one idea of areCousins: go level by level. On each level, note whether x and whether y turn
// up. Both on the same level means the first condition holds; just one of them means they are on
// different levels, so the answer is false. The second condition is checked from above: while a
// node is being taken off the queue, if its two children are x and y it is their one parent, and
// the answer is false.
//
// The one idea of cousinsHashing: one walk down the tree writes down, for each value, two facts --
// its depth and its parent node. Then the answer is a comparison of two entries: the depths are
// equal and the parents are different nodes.
//
// The one idea of cousinsInOrder: walk the whole tree once, carrying the current node's level and
// its parent down the calls. When the walk reaches the node holding a, or the one holding b, it
// writes that node's level and parent into a small record for it (NodeInfo). After the walk, the
// nodes are cousins when the two levels are equal and the two parents are different nodes.
//
// The one idea of cousins: the two conditions are checked separately. 1. Level -- the same level.
// 2. Siblings -- not the same parent: the pair is siblings when some node has one of them as its
// left child and the other as its right child. Cousins are exactly the pairs on one level that are
// not siblings.
//
// Time: O(n) for all four -- areCousins queues and takes off each node once; cousinsHashing makes
// one call per node, then two lookups in each map; cousinsInOrder makes one call per node and does
// not stop early; cousins does two level lookups and one sibling search, each a walk over the tree.
// Space: areCousins is O(w) -- the queue holds one level or two; w is the widest level, up to
// n / 2. cousinsHashing is O(n) -- two maps with one entry per node, plus O(h) recursion stack.
// cousinsInOrder is O(h) -- the recursion stack; two records, no map. cousins is O(h) -- the
// recursion stack of its walks.
//
// Siblings: Siblings.java is the parent check used by cousins.

public final class CousinsQueue {

	public static boolean areCousins(TreeNode root, int x, int y) {
		if (root == null) {
			return false;
		}
		Queue<TreeNode> queue = new ArrayDeque<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			int size = queue.size(); // LEVEL: the nodes now in the queue are one level
			boolean foundX = false;
			boolean foundY = false;
			for (int i = 0; i < size; i++) {
				TreeNode node = queue.poll();
				if (node.val == x) {
					foundX = true; // SEEN: x is on this level
				}
				if (node.val == y) {
					foundY = true; // SEEN: y is on this level
				}
				if (node.left != null && node.right != null) {
					int left = node.left.val;
					int right = node.right.val;
					boolean one = left == x && right == y;
					boolean two = left == y && right == x;
					if (one || two) {
						return false; // SIBLINGS: x and y are the two children of this node
					}
				}
				if (node.left != null) {
					queue.add(node.left); // QUEUE: the next level
				}
				if (node.right != null) {
					queue.add(node.right);
				}
			}
			if (foundX && foundY) {
				return true; // RETURN: same level, and not siblings
			}
			if (foundX || foundY) {
				return false; // RETURN: only one found, so they are on different levels
			}
		}
		return false;
	}

	public static boolean cousinsHashing(TreeNode root, int a, int b) {
		if (isNull(root)) {
			return true;
		}
		Map<Integer, Integer> depth = new HashMap<>(); // DEPTH: value -> its depth, root 0
		Map<Integer, TreeNode> parent = new HashMap<>(); // PARENT: value -> its parent node, root null
		dfs(root, null, depth, parent); // WALK: fill both maps
		// equals for the depths: they are Integers, and a reference compare
		// only works inside the -128..127 cache
		boolean one = depth.get(a).equals(depth.get(b)); // LEVEL: the same depth
		// != is CORRECT for the parents: these are TreeNode references, and
		// different parents means different objects
		boolean two = parent.get(a) != parent.get(b); // PARENTS: different parent nodes
		return one && two; // RETURN: cousins are both
	}

	private static void dfs(TreeNode root, TreeNode parent,
			Map<Integer, Integer> depth, Map<Integer, TreeNode> map) {
		if (isNull(root)) {
			return;
		}
		int height = isNull(parent) ? 0 : 1 + depth.get(parent.val); // DEPTH: one below the parent's
		depth.put(root.val, height); // RECORD: depth of this value
		map.put(root.val, parent); // RECORD: parent of this value
		dfs(root.left, root, depth, map);
		dfs(root.right, root, depth, map);
	}

	public static boolean cousinsInOrder(TreeNode root, int a, int b) {
		if (isNull(root)) {
			return true;
		}
		NodeInfo x = new NodeInfo(a, 1); // INFO: level and parent of the node holding a
		NodeInfo y = new NodeInfo(b, 1); // INFO: level and parent of the node holding b
		inorder(root, null, 1, x, y); // WALK: fill both records
		return x.level == y.level && x.parent != y.parent; // RETURN: same level, different parents
	}

	private static void inorder(TreeNode root, TreeNode parent,
									int level, NodeInfo x, NodeInfo y) {
		if (isNull(root)) {
			return;
		}
		inorder(root.left, root, level + 1, x, y);
		if (root.val == x.key) {
			x.level = level; // RECORD: where a was found
			x.parent = parent;
		}
		if (root.val == y.key) {
			y.level = level; // RECORD: where b was found
			y.parent = parent;
		}
		inorder(root.right, root, level + 1, x, y);
	}

	public static boolean cousins(TreeNode root, TreeNode a, TreeNode b) {
		boolean sameLevel = level(root, a) == level(root, b); // LEVEL: both on one level
		return sameLevel && !siblings(root, a, b); // SIBLINGS: and not under one parent
	}

	public static void main(String[] args) {
		TreeNode root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.left.right.right = newNode(15);
		root.right.left = newNode(6);
		root.right.right = newNode(7);
		root.right.left.right = newNode(8);
		System.out.println(areCousins(root, 4, 7)); // true -- both on level 2, parents 2 and 3

		TreeNode root1 = newNode(1);
		root1.left = newNode(2);
		root1.right = newNode(3);
		root1.left.left = newNode(4);
		root1.left.right = newNode(5);
		root1.right.left = newNode(6);
		root1.right.right = newNode(7);
		System.out.println(areCousins(root1, 5, 6)); // true -- both on level 2, parents 2 and 3

		System.out.println(cousinsHashing(root, 4, 7)); // true
		System.out.println(cousinsHashing(root1, 5, 6)); // true

		System.out.println(cousinsInOrder(root, 4, 7)); // true
		System.out.println(cousinsInOrder(root1, 5, 6)); // true

		System.out.println(cousins(root, root.left.left, root.right.right)); // 4 and 7: true
		System.out.println(cousins(root1, root1.left.right, root1.right.left)); // 5 and 6: true
	}

	private static class NodeInfo {
		private int key;
		private int level;
		private TreeNode parent;

		public NodeInfo(int key, int level) {
			this(key, level, null);
		}

		public NodeInfo(int key, int level, TreeNode parent) {
			this.key = key;
			this.level = level;
			this.parent = parent;
		}
	}
}
