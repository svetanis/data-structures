package com.svetanis.datastructures.tree.binary.bt.dfs;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 863. All Nodes Distance K in Binary Tree
//
// Input: root of a binary tree, a target node of that tree, and k. Returns the values of every
// node exactly k edges from the target, in any order. On LeetCode: 1 <= number of nodes <= 500,
// 0 <= node.val <= 500, all values are unique, the target is a node of the tree,
// 0 <= k <= 1000.
//
// The one idea: a tree only knows a node's children, but distance k can go up through the
// parent. So first record every node's parent in a map. Then walk out from the target in three
// directions -- left child, right child, parent -- carrying how many steps are left. A node
// reached with 0 steps left is exactly k edges away and goes in the answer.
//
// The map is keyed by node identity, so two nodes holding equal values are still two keys. The
// visited set holds values, which is safe only because LeetCode guarantees every value is
// unique; with equal values on different nodes it would skip real nodes. The root has no
// parent, so its map entry is null and dfs returns at once.
//
// Time: O(n) -- each node is visited once, because the visited set stops a second visit.
// Space: O(n) -- the parent map, the visited set, and the recursion stack.
//
// Siblings: AllNodesDistanceK finds the answer in one walk, with no map; AllNodesDistanceKBfs
// builds neighbour lists and searches breadth-first.

public final class AllNodesDistanceKDfs {

	public static List<Integer> nodesDistK(TreeNode root, TreeNode target, int k) {
		Map<TreeNode, TreeNode> map = new IdentityHashMap<>(); // node -> its parent
		parents(root, null, map); // BUILD: every node's parent
		Set<Integer> visited = new HashSet<>();
		List<Integer> list = new ArrayList<>();
		dfs(target, k, visited, list, map); // WALK: out from the target, k steps
		return list;
	}

	private static void dfs(TreeNode node, int k, Set<Integer> visited, List<Integer> list, Map<TreeNode, TreeNode> map) {
		if (node == null || visited.contains(node.val)) {
			return; // BASE: past a leaf or above the root, or already reached
		}
		visited.add(node.val);
		if (k == 0) {
			list.add(node.val); // RECORD: no steps left, so k edges from the target
			return;
		}
		dfs(node.left, k - 1, visited, list, map); // WALK: down left
		dfs(node.right, k - 1, visited, list, map); // WALK: down right
		dfs(map.get(node), k - 1, visited, list, map); // WALK: up to the parent
	}

	private static void parents(TreeNode root, TreeNode parent, Map<TreeNode, TreeNode> map) {
		if (root == null) {
			return;
		}
		map.put(root, parent); // RECORD: parent of this node
		parents(root.left, root, map);
		parents(root.right, root, map);
	}

	public static void main(String[] args) {
		TreeNode target = new TreeNode(5);
		TreeNode root = new TreeNode(3);
		root.left = target;
		root.right = new TreeNode(1);
		root.left.left = new TreeNode(6);
		root.left.right = new TreeNode(2);
		root.left.right.left = new TreeNode(7);
		root.left.right.right = new TreeNode(4);
		root.right.left = new TreeNode(0);
		root.right.right = new TreeNode(8);
		System.out.println(nodesDistK(root, target, 2)); // [7, 4, 1]

		TreeNode root2 = new TreeNode(1);
		System.out.println(nodesDistK(root2, root2, 3)); // []
	}
}
