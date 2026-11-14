package com.svetanis.datastructures.tree.binary.bt.lca;

import java.util.ArrayDeque;
import java.util.Deque;

// 1650. Lowest Common Ancestor of a Binary Tree III
//
// Input: root -- the root of the tree; u, v -- two nodes of the tree, each holding a pointer to its
// parent (null at the root). Returns their lowest common ancestor, a node counting as an ancestor
// of itself. On LeetCode: 2 <= nodes <= 10^5, -10^9 <= Node.val <= 10^9, all values distinct,
// u != v, and both exist in the tree.
//
// The one idea: a node's path from the root is found by climbing its parent pointers up to the root
// and pushing each node on a stack as it is passed, so the root ends on top and the node at the
// bottom. Two such stacks start with the same node (the root) on top. Pop both while their top nodes
// are the same node; the last pair that was the same is the last node the two root paths share,
// which is the LCA.
//
// Time: O(h) -- each stack takes one climb, and the comparison pops at most h from each.
// Space: O(h) -- the two stacks.
//
// Siblings: LcaBtParent needs no stack (O(1) space) and LcaBtParentLevel lifts the deeper node
// level with the other. The three share the package's Node.

public final class LcaBtParentStack {

	public static Node lca(Node root, Node u, Node v) {
		Deque<Node> s1 = stack(root, u); // PATH: root on top, u at the bottom
		Deque<Node> s2 = stack(root, v);
		return lca(s1, s2);
	}

	private static Node lca(Deque<Node> s1, Deque<Node> s2) {
		Node lca = null;
		while (!s1.isEmpty() && !s2.isEmpty() && s1.peek() == s2.peek()) { // WALK: down while the paths agree
			lca = s1.peek(); // RECORD: the last shared node so far
			s1.pop();
			s2.pop();
		}
		return lca; // RETURN: the deepest node on both paths
	}

	private static Deque<Node> stack(Node root, Node node) {
		Deque<Node> stack = new ArrayDeque<>();
		while (node != root) { // CLIMB: from the node up to the root by parent pointers
			stack.push(node);
			node = node.parent;
		}
		stack.push(root); // the root goes in last, so it is on top
		return stack;
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
