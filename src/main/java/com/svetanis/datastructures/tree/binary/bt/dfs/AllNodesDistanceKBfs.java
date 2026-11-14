package com.svetanis.datastructures.tree.binary.bt.dfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
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
// parent. So first give every node a list of its neighbours -- its children and its parent. Then
// the tree is an ordinary graph, and the nodes k edges from the target are the ones reached in
// the k-th round of a breadth-first search from the target: round 0 is the target, round 1 its
// neighbours, and so on. When the queue holds exactly round k, it is the answer.
//
// The map is keyed by node identity, so two nodes holding equal values are still two keys. The
// visited set holds values, which is safe only because LeetCode guarantees every value is unique;
// with equal values on different nodes it would skip real nodes.
//
// Time: O(n) -- the graph is built in one walk and each node enters the queue once.
// Space: O(n) -- the neighbour lists, the visited set and the queue.
//
// Siblings: AllNodesDistanceK finds the answer in one recursive walk, with no graph;
// AllNodesDistanceKDfs builds only a parent map and searches depth-first.

public final class AllNodesDistanceKBfs {

	public static List<Integer> nodesDistK(TreeNode root, TreeNode target, int k) {
		Map<TreeNode, List<TreeNode>> map = new IdentityHashMap<>(); // node -> its children and parent
		graph(root, null, map); // BUILD: the tree as an undirected graph
		Set<Integer> visited = new HashSet<>();
		Deque<TreeNode> dq = new ArrayDeque<>();
		dq.offer(target); // START: round 0 is the target alone
		visited.add(target.val);
		int dist = 0;
		while (!dq.isEmpty()) {
			if (dist == k) {
				return kDown(dq); // RETURN: the queue holds exactly the nodes k edges away
			}
			int size = dq.size(); // SIZE: one round at a time
			for (int i = 0; i < size; i++) {
				TreeNode node = dq.poll();
				for (TreeNode adj : map.get(node)) {
					if (!visited.contains(adj.val)) { // SKIP: already reached in an earlier round
						visited.add(adj.val);
						dq.offer(adj); // TAKE: reached for the first time, one edge further
					}
				}
			}
			dist++;
		}
		return new ArrayList<>(); // RETURN: no node is k edges from the target
	}

	private static List<Integer> kDown(Deque<TreeNode> dq) {
		List<Integer> list = new ArrayList<>();
		while (!dq.isEmpty()) {
			list.add(dq.poll().val); // RECORD: every node left in the queue is an answer
		}
		return list;
	}

	private static void graph(TreeNode node, TreeNode parent, Map<TreeNode, List<TreeNode>> map) {
		if (node == null) {
			return;
		}
		map.putIfAbsent(node, new ArrayList<>());
		if (parent != null) {
			map.get(node).add(parent); // EDGE: child to parent
			map.get(parent).add(node); // EDGE: parent to child
		}
		graph(node.left, node, map); // WALK: left subtree
		graph(node.right, node, map);
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
		System.out.println(nodesDistK(root, target, 2)); // [1, 7, 4]

		TreeNode root2 = new TreeNode(1);
		System.out.println(nodesDistK(root2, root2, 3)); // []
	}
}
