package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

// All Root-to-Leaf Paths, without recursion -- not a LeetCode problem; the nearest is
// 257. Binary Tree Paths (which wants strings)
//
// Input: the root of a binary tree with at least one node (an empty tree throws a
// NullPointerException). Returns the values of every root-to-leaf path, root first, one list per
// leaf. The lists come out level by level: a leaf nearer the root is listed before a deeper one.
//
// The one idea: visit the nodes level by level with a queue, and remember for each node who its
// parent is, so a path can be read back from any leaf. One map entry holds "this node's parent".
// The map is keyed on the node object itself (identity), never on its value, because two
// different nodes can hold the same value and must keep their own parents. At each leaf, rebuild
// the path by following parents up to the root.
//
// Time: O(n + L * h^2) -- each leaf (L of them) rebuilds a path of up to h nodes by inserting
//   at the front of a list, which moves up to h entries each time (h = height).
// Space: O(n) -- the queue and the parent map; the answer is extra.
//
// Siblings: RootToLeafPathsRecursive lists the same paths depth first, left to right, with a
// deque and no parent map; RootToLeafPathsSimple and RootToLeafPathsSubmit build strings.
public final class RootToLeafPathsIterative {

	private static List<List<Integer>> paths(TreeNode root) {
		Deque<TreeNode> dq = new ArrayDeque<>();
		dq.add(root);
		Map<TreeNode, TreeNode> map = new IdentityHashMap<>(); // PARENT: node object -> its parent
		map.put(root, null);
		List<List<Integer>> paths = new ArrayList<>();
		while (!dq.isEmpty()) {
			TreeNode node = dq.poll();
			if (isLeaf(node)) { // RECORD: rebuild the path from this leaf up
				paths.add(path(node, map));
			}
			if (isNotNull(node.left)) { // QUEUE: left child
				dq.add(node.left);
				map.put(node.left, node);
			}
			if (isNotNull(node.right)) { // QUEUE: right child
				dq.add(node.right);
				map.put(node.right, node);
			}
		}
		return paths;
	}

	private static List<Integer> path(TreeNode leaf, Map<TreeNode, TreeNode> map) {
		TreeNode node = leaf;
		List<Integer> list = new ArrayList<>();
		while (isNotNull(map.get(node))) { // WALK: up until the root, whose parent is null
			list.add(0, node.val);
			node = map.get(node);
		}
		list.add(0, node.val); // ROOT: the last node, not covered by the loop
		return list;
	}

	public static void main(String[] args) {
		// 10->8->3
		// 10->8->5
		// 10->2->2
		TreeNode root = newNode(10);
		root.left = newNode(8);
		root.right = newNode(2);
		root.left.left = newNode(3);
		root.left.right = newNode(5);
		root.right.left = newNode(2);
		inOrder(root); // prints 3 8 5 10 2 2
		System.out.println();

		// print Root-to-leaf path
		System.out.println("Root-to-leaf paths: ");
		System.out.println(paths(root)); // [[10, 8, 3], [10, 8, 5], [10, 2, 2]]
		System.out.println();

		// two leaves holding the same value under different parents.
		// The parent map is keyed on node identity, so the two leaves are
		// two entries, each with its own parent, and each path ends its own way.
		// 1->2->5
		// 1->3->5
		TreeNode dup = newNode(1);
		dup.left = newNode(2);
		dup.right = newNode(3);
		dup.left.left = newNode(5);
		dup.right.left = newNode(5);
		System.out.println("Two leaves of equal value: ");
		System.out.println(paths(dup)); // [[1, 2, 5], [1, 3, 5]]
		System.out.println();
	}
}
