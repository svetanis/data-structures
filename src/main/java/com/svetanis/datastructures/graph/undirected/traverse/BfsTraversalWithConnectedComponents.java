package com.svetanis.datastructures.graph.undirected.traverse;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.svetanis.datastructures.graph.Graph;
import com.svetanis.datastructures.graph.Node;

// Breadth-first traversal of a whole graph, one connected component at a time.
//
// Input: g -- a graph given as a list of nodes; each node holds an integer (getData) and the list of
// nodes it has an edge to (getNeighbors). Returns one list per connected component, in the order the
// components are first met in g's node list; each list holds the integers of the component's nodes
// in breadth-first order from the first node of that component met. Assumes the integers held by
// the nodes are distinct, because a node is looked up in a set by its contents.
//
// The one idea: a single breadth-first walk only reaches the nodes joined to where it started. So
// keep one set of nodes already found, shared by all the walks, and go through the node list: every
// node not in the set starts a new component, and one walk from it finds everything joined to it.
// One queue entry in a walk is a node found but not yet visited, as in BfsTraversal.
//
// Time: O(V + E) -- each node is queued once over all the walks and each edge looked at once.
// Space: O(V) -- the found set, the queue, and the answer.
//
// Siblings: BfsTraversal is the single walk from one source node; this file repeats it.

public final class BfsTraversalWithConnectedComponents {

	public static List<List<Integer>> bfs(Graph g) {
		Set<Node> set = new HashSet<>(); // FOUND: shared by every walk
		List<List<Integer>> lists = new ArrayList<>();
		for (Node node : g.getNodes()) {
			if (!set.contains(node)) { // a node no earlier walk reached: a new component
				lists.add(bfs(node, set)); // WALK: one component
			}
		}
		return lists; // RETURN: one list per component
	}

	private static List<Integer> bfs(Node src, Set<Node> set) {
		List<Integer> list = new ArrayList<>();
		Queue<Node> queue = new ArrayDeque<>();
		queue.add(src);
		set.add(src); // MARK: when queued, so a node is never queued twice
		while (!queue.isEmpty()) {
			Node node = queue.poll(); // TAKE: the oldest found node
			list.add(node.getData()); // RECORD: visiting order
			for (Node neighbour : node.getNeighbors()) {
				if (!set.contains(neighbour)) { // SKIP: a node already found
					queue.add(neighbour);
					set.add(neighbour);
				}
			}
		}
		return list; // RETURN: this component in visiting order
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

		System.out.println(bfs(g)); // [[1, 2, 3, 4], [5, 6]]
	}
}
