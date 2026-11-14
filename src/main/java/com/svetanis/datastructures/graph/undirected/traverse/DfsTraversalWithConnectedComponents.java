package com.svetanis.datastructures.graph.undirected.traverse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.svetanis.datastructures.graph.Graph;
import com.svetanis.datastructures.graph.Node;

// Depth-first traversal of every component of a graph -- not a LeetCode problem.
//
// Input: g -- a Graph, a list of Nodes; each Node holds an int and the list of Nodes it points to.
// Returns one list per group of nodes the walk reaches from a start node, each list holding the
// ints in the order the depth-first walk first reached them; the groups come in the order of
// their first node in g. For an undirected graph (both ends list each other) a group is a
// connected component.
//
// The one idea: one call of explore stands for one node the walk has just arrived at: it writes
// the node's number down, puts the node in the set of nodes already reached, and follows each
// pointer to a node not in the set. One walk reaches only what is joined to its start, so the
// outer loop starts a new walk at each node of g that no earlier walk reached, and each such
// start opens a new list. For a directed graph a later start may point into an earlier group;
// the set keeps it from being written twice.
//
// Time: O(V + E) -- every node is explored once and every pointer is looked at once.
// Space: O(V) -- the set, the lists, and a recursion as deep as the longest unbroken route.
//
// Siblings: DfsTraversal is one walk from one node.

public final class DfsTraversalWithConnectedComponents {

	public static List<List<Integer>> dfs(Graph g) {
		int components = 0;
		Set<Node> set = new HashSet<>();
		List<Integer> list = new ArrayList<>();
		List<List<Integer>> lists = new ArrayList<>();
		for (Node node : g.getNodes()) {
			if (!set.contains(node)) { // WALK: a node no earlier walk reached starts a new group
				components++;
				explore(node, set, list);
				lists.add(new ArrayList<>(list)); // RECORD: this group
				list.clear();
			}
		}
		if (components != lists.size()) { // CHECK: one list per walk
			throw new IllegalStateException("components and lists disagree");
		}
		return lists; // RETURN: the groups
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
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		Node node6 = new Node(6);

		// add edges
		node1.add(node2);
		node1.add(node3);
		node2.add(node1);
		node2.add(node4);
		node3.add(node1);
		node3.add(node4);
		node4.add(node2);
		node4.add(node3);
		node5.add(node6);
		node6.add(node5);

		Graph g = new Graph();
		g.addNode(node1);
		g.addNode(node2);
		g.addNode(node3);
		g.addNode(node4);
		g.addNode(node5);
		g.addNode(node6);

		System.out.println(dfs(g)); // [[1, 2, 4, 3], [5, 6]]
	}
}
