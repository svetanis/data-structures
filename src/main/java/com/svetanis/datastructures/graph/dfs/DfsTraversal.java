package com.svetanis.datastructures.graph.dfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.svetanis.datastructures.graph.Node;

// Depth-first traversal of a graph from one node -- not a LeetCode problem.
//
// Input: src -- a Node holding an int and the list of Nodes it points to (a directed edge each;
// for an undirected graph both ends list each other). Returns the ints of every node reachable
// from src, each once, in the order a depth-first walk first reaches them.
//
// The one idea: one call of explore stands for one node the walk has just arrived at. It writes
// the node's number down and puts the node in the set of nodes already reached, then follows
// each pointer to a node that is not in the set. The set is what stops the walk going round a
// cycle, and what stops a node reached by two routes from being written twice. The first loop
// in dfs obeys the same test as the one inside explore.
//
// Time: O(V + E) -- every node is explored once and every pointer is looked at once.
// Space: O(V) -- the set, the list, and a recursion as deep as the longest unbroken route.
//
// Siblings: DfsTraversalWithConnectedComponents repeats this from every node not yet reached and
// returns one list per component.

public final class DfsTraversal {

	public static List<Integer> dfs(Node src) {
		Set<Node> set = new HashSet<>();
		List<Integer> list = new ArrayList<>();
		set.add(src); // MARK
		list.add(src.getData()); // RECORD
		for (Node node : src.getNeighbors()) {
			if (!set.contains(node)) { // SKIP: already reached by an earlier route
				explore(node, set, list);
			}
		}
		return list; // RETURN: numbers in first-reached order
	}

	private static void explore(Node node, Set<Node> set, List<Integer> list) {
		set.add(node); // MARK
		list.add(node.getData()); // RECORD
		for (Node neighbor : node.getNeighbors()) {
			if (!set.contains(neighbor)) { // SKIP: already reached
				explore(neighbor, set, list); // WALK
			}
		}
	}

	public static void main(String[] args) {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		node1.add(node2);
		node1.add(node3);
		System.out.println(dfs(node1)); // [1, 2, 3]

		// node 6 is reached through node 5 and again straight from node 4
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		Node node6 = new Node(6);
		node4.add(node5);
		node4.add(node6);
		node5.add(node6);
		System.out.println(dfs(node4)); // [4, 5, 6]
	}
}
