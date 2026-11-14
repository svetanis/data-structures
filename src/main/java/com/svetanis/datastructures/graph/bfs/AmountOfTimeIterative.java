package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 2385. Amount of Time for Binary Tree to Be Infected
//
// Input: root -- the root of a binary tree whose node values are all different; start -- the value
// of one node. At minute 0 that node is infected, and every minute each infected node infects the
// nodes next to it: its parent and its children. Returns how many minutes pass until the whole tree
// is infected. On LeetCode: 1 <= number of nodes <= 10^5, 1 <= Node.val <= 10^5, all values unique,
// a node with value start exists.
//
// The one idea: the same two steps as AmountOfTime -- turn the tree into a graph that can be walked
// both ways (each value -> the values of its parent and children), then walk that graph in rounds,
// one round per minute. The only change is how the tree is read in the first step: a queue of tree
// nodes still to be read replaces the recursion, so a chain of 10^5 nodes needs no deep call stack.
// One queue entry is a tree node whose edges to its children are not recorded yet.
//
// Time: O(n) -- buildGraph takes each node once; the walk queues each node once.
// Space: O(n) -- the map, the visited set and the two queues.
//
// Siblings: AmountOfTime reads the tree with a recursion, one call per level, which is the shorter
// code but overflows a default Java stack on a very tall tree.

public final class AmountOfTimeIterative {

	private Map<Integer, List<Integer>> map = new HashMap<>(); // GRAPH: a node value -> the values of its parent and children

	public int amountOfTime(TreeNode root, int start) {
		buildGraph(root); // BUILD: the tree as a two-way graph
		Set<Integer> visited = new HashSet<>(); // INFECTED: values infected so far
		Deque<Integer> dq = new ArrayDeque<>();
		dq.offer(start); // START: minute 0
		int time = -1; // MINUTES: -1, so the round that holds only start leaves 0
		while (!dq.isEmpty()) {
			time++; // ROUND: one more minute
			for (int i = dq.size(); i > 0; i--) {
				int node = dq.pollFirst(); // TAKE: a node infected in this minute
				visited.add(node); // MARK
				if (map.containsKey(node)) {
					for (int neighbor : map.get(node)) {
						if (!visited.contains(neighbor)) { // SPREAD: to a neighbour not infected yet
							dq.offer(neighbor);
						}
					}
				}
			}
		}
		return time; // RETURN: the number of the last round that infected anything
	}

	private void buildGraph(TreeNode root) {
		Deque<TreeNode> pending = new ArrayDeque<>(); // TO READ: tree nodes whose child edges are not recorded yet
		if (root != null) {
			pending.offer(root);
		}
		while (!pending.isEmpty()) {
			TreeNode node = pending.poll(); // TAKE: the next node to read
			if (node.left != null) {
				map.computeIfAbsent(node.val, k -> new ArrayList<>()).add(node.left.val); // RECORD: the edge parent -> left child
				map.computeIfAbsent(node.left.val, k -> new ArrayList<>()).add(node.val); // RECORD: and child -> parent
				pending.offer(node.left); // WALK: read the child later
			}
			if (node.right != null) {
				map.computeIfAbsent(node.val, k -> new ArrayList<>()).add(node.right.val); // RECORD: the edge parent -> right child
				map.computeIfAbsent(node.right.val, k -> new ArrayList<>()).add(node.val); // RECORD: and child -> parent
				pending.offer(node.right); // WALK: read the child later
			}
		}
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(5);
		root.right = new TreeNode(3);
		root.left.right = new TreeNode(4);
		root.right.left = new TreeNode(10);
		root.right.right = new TreeNode(6);
		root.left.right.left = new TreeNode(9);
		root.left.right.right = new TreeNode(2);
		System.out.println(new AmountOfTimeIterative().amountOfTime(root, 3)); // 4
		System.out.println(new AmountOfTimeIterative().amountOfTime(new TreeNode(1), 1)); // 0
	}
}
