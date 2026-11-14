package com.svetanis.datastructures.tree.binary.bt.lca;

// 1650. Lowest Common Ancestor of a Binary Tree III
//
// Input: root -- the root of the tree (not needed by every method); u, v -- two nodes of the tree,
// each holding a pointer to its parent (null at the root). Returns their lowest common ancestor, a
// node counting as an ancestor of itself. On LeetCode: 2 <= nodes <= 10^5, -10^9 <= Node.val <= 10^9,
// all values distinct, u != v, and both exist in the tree.
//
// The one idea: let L be the lowest common ancestor. Walker one climbs from u to the root, then
// continues from v up toward L; walker two climbs from v to the root, then continues from u up
// toward L. Both walks have the same length -- (u to L) + (L to the root) + 1 for the jump + (v to L)
// -- so both reach L after that many steps at the latest, and L is the first node they share. If u
// is an ancestor of v the same count puts both on u.
//
// Time: O(h) -- each walker takes at most 2h + 1 steps, h being the height of the tree.
// Space: O(1) -- two pointers.
//
// Siblings: LcaBtParentLevel lines the two nodes up by their depths found from root, and
// LcaBtParentStack compares the two root paths from the top. The three share the package's Node.

public final class LcaBtParent {

	public static Node lca(Node root, Node u, Node v) {
		Node node1 = u; // WALKER one starts at u
		Node node2 = v; // WALKER two starts at v
		while (node1 != node2) { // MEET: both walkers stand on the same node
			node1 = node1.parent == null ? v : node1.parent; // CLIMB, or jump to v past the root
			node2 = node2.parent == null ? u : node2.parent; // CLIMB, or jump to u past the root
		}
		return node1; // RETURN: the first node both walkers reach
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
