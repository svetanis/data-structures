package com.svetanis.datastructures.tree.binary.bt.lca;

// 1650. Lowest Common Ancestor of a Binary Tree III
//
// Input: root -- the root of the tree; u, v -- two nodes of the tree, each holding a pointer to its
// parent (null at the root). Returns their lowest common ancestor, a node counting as an ancestor
// of itself. On LeetCode: 2 <= nodes <= 10^5, -10^9 <= Node.val <= 10^9, all values distinct,
// u != v, and both exist in the tree.
//
// The one idea: two nodes at the same depth reach their LCA by climbing the same number of steps, so
// first lift the deeper one until both are at the same depth, then climb both together until they
// are the same node. Depth is found by searching for the node's value down from root, so the
// method does not need the nodes to know their depth.
//
// Time: O(n * h) -- every comparison in the lifting loops runs two searches from root, each O(n),
// and there are up to h comparisons; the final loop takes at most h steps. With 10^5 nodes on a
// skewed tree this is the slow one of the three siblings.
// Space: O(h) -- the recursion stack of the depth search; the climbing needs only the two pointers.
//
// Siblings: LcaBtParent does it in O(h) with two pointers and no depth search; LcaBtParentStack
// compares the two root paths from the top in O(h). The three share the package's Node.

public final class LcaBtParentLevel {

	public static Node lca(Node root, Node u, Node v) {
		while (level(root, u.val) > level(root, v.val)) { // LIFT: the deeper node up to the other's depth
			u = u.parent;
		}
		while (level(root, v.val) > level(root, u.val)) {
			v = v.parent;
		}
		while (u.val != v.val) { // CLIMB: both together until they are the same node
			u = u.parent;
			v = v.parent;
		}
		return u; // RETURN: the first shared ancestor
	}

	private static int level(Node root, int data) {
		return level(root, data, 1);
	}

	private static int level(Node node, int data, int level) { // DEPTH: search from the root by value
		if (node == null) {
			return 0;
		}
		if (node.val == data) {
			return level;
		}
		int left = level(node.left, data, level + 1);
		if (left != 0) {
			return left;
		}
		return level(node.right, data, level + 1);
	}

	public static void main(String[] args) {
		Node root = new Node(3);
		Node node1 = new Node(5);
		Node node2 = new Node(1);
		Node node3 = new Node(6);
		Node node4 = new Node(2);
		Node node5 = new Node(7);
		Node node6 = new Node(4);
		Node node7 = new Node(0);
		Node node8 = new Node(8);

		root.left = node1;
		root.left.parent = root;
		root.right = node2;
		root.right.parent = root;
		root.parent = null;
		root.left.left = node3;
		root.left.left.parent = node1;
		root.left.right = node4;
		root.left.right.parent = node1;
		root.left.right.left = node5;
		root.left.right.left.parent = node4;
		root.left.right.right = node6;
		root.left.right.right.parent = node4;
		root.right.left = node7;
		root.right.left.parent = node2;
		root.right.right = node8;
		root.right.right.parent = node2;

		System.out.println(lca(root, root.left, root.right)); // 3
		System.out.println(lca(root, root.left, root.left.right.right)); // 5
	}
}
