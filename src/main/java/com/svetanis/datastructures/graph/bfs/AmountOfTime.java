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
// The one idea: infection travels along edges in both directions, up to the parent as well as down
// to the children, but a tree node only knows its children. So first turn the tree into a graph
// that can be walked both ways: map each value to the values of its parent and children. Then walk
// that graph in rounds, one round per minute. One queue entry is a node infected in the current
// minute. Round 0 holds only start; each round infects the neighbours not infected before. The
// number of rounds after the first is the answer, which is why time starts at -1.
// buildGraph recurses one level per tree level, so a chain of 10^5 nodes needs a larger thread
// stack than the JVM default.
//
// Time: O(n) -- buildGraph visits each node once; the walk queues each node once.
// Space: O(n) -- the map, the visited set and the queue.
//
// Siblings: AmountOfTimeIterative reads the tree with a queue instead, so only a very tall tree
// tells them apart: there this one overflows the default stack and that one does not.

public final class AmountOfTime {

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
		if (root == null) { // BASE
			return;
		}
		if (root.left != null) {
			map.computeIfAbsent(root.val, k -> new ArrayList<>()).add(root.left.val); // RECORD: the edge parent -> left child
			map.computeIfAbsent(root.left.val, k -> new ArrayList<>()).add(root.val); // RECORD: and child -> parent
		}
		if (root.right != null) {
			map.computeIfAbsent(root.val, k -> new ArrayList<>()).add(root.right.val); // RECORD: the edge parent -> right child
			map.computeIfAbsent(root.right.val, k -> new ArrayList<>()).add(root.val); // RECORD: and child -> parent
		}
		buildGraph(root.left); // WALK
		buildGraph(root.right); // WALK
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
		AmountOfTime aot = new AmountOfTime();
		System.out.println(aot.amountOfTime(root, 3)); // 4
		System.out.println(new AmountOfTime().amountOfTime(new TreeNode(1), 1)); // 0
	}
}
